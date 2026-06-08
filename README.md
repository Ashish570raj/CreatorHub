# 🛍️ CreatorHub — Creator Store REST API

A production-ready RESTful backend for an e-commerce creator store, built with **Spring Boot**, **PostgreSQL**, and **JPA/Hibernate**. The API enables creators to manage their product catalogue and process customer orders with real-time inventory management.

---

## 🚨 Problem Statement

Independent creators selling digital or physical products online face a common set of backend challenges:

- **No centralised product management** — updating prices, descriptions, or stock levels is manual and error-prone.
- **No order processing logic** — there's no system to validate stock availability before confirming an order.
- **Inventory drift** — stock quantities go out of sync when multiple orders are placed simultaneously.
- **No audit trail** — order history with timestamps and totals is missing, making it hard to track sales.

Existing off-the-shelf solutions are either too heavy (full storefronts) or too basic (simple CRUD). Creators need a **clean, lightweight API** they can plug into any frontend or mobile app.

---

## ✅ How CreatorHub Solves It

| Problem | Solution |
|---|---|
| No product management | Full CRUD API for products with validation (price > 0, stock ≥ 0, name required) |
| No order processing | Dedicated order endpoint that accepts multiple items in one request |
| Inventory drift | `@Transactional` order creation — stock is validated and deducted atomically; if any item fails, the whole order rolls back |
| No audit trail | Every order stores customer info, status, total price, and a `createdAt` timestamp auto-set on creation |
| Circular JSON responses | `@JsonManagedReference` / `@JsonBackReference` / `@JsonIgnore` annotations prevent infinite loops in serialized responses |

---

## 🏗️ Architecture

```
┌─────────────────────────────────────────────┐
│                  REST Client                │
└───────────────────┬─────────────────────────┘
                    │ HTTP
        ┌───────────▼───────────┐
        │      Controllers      │  ← @RestController
        │  ProductControllers   │
        │  OrderController      │
        └───────────┬───────────┘
                    │
        ┌───────────▼───────────┐
        │       Services        │  ← @Service / @Transactional
        │  ProductService       │
        │  OrderService         │
        └───────────┬───────────┘
                    │
        ┌───────────▼───────────┐
        │     Repositories      │  ← Spring Data JPA
        │  ProductRepository    │
        │  OrderRepository      │
        │  OrderItemsRepository │
        └───────────┬───────────┘
                    │
        ┌───────────▼───────────┐
        │      PostgreSQL       │
        │  products             │
        │  orders               │
        │  order_items          │
        └───────────────────────┘
```

---

## 🗄️ Database Schema

```
products
├── id            BIGSERIAL PRIMARY KEY
├── name          VARCHAR NOT NULL
├── description   VARCHAR
├── category      VARCHAR
├── price         DECIMAL NOT NULL
└── stock_quantity INT NOT NULL

orders
├── id             BIGSERIAL PRIMARY KEY
├── customer_name  VARCHAR NOT NULL
├── customer_email VARCHAR NOT NULL
├── status         VARCHAR NOT NULL  (e.g. "CONFIRMED")
├── total_price    DECIMAL NOT NULL
└── created_at     TIMESTAMP

order_items
├── id          BIGSERIAL PRIMARY KEY
├── quantity    INT NOT NULL
├── price       DECIMAL NOT NULL
├── order_id    FK → orders(id)
└── product_id  FK → products(id)
```

> Schema is auto-managed by Hibernate (`ddl-auto: update`).

---

## 🔌 API Endpoints

### Products — `/api/products`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/products` | Get all products |
| `GET` | `/api/products/{id}` | Get product by ID |
| `POST` | `/api/products` | Create a new product |
| `PUT` | `/api/products/{id}` | Update product by ID |
| `DELETE` | `/api/products/{id}` | Delete product by ID |

#### Create / Update Product — Request Body
```json
{
  "name": "Handmade Notebook",
  "description": "A5 size, 200 pages",
  "category": "Stationery",
  "price": 12.99,
  "stockQuantity": 50
}
```

---

### Orders — `/api/orders`

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/orders` | Get all orders |
| `GET` | `/api/orders/{id}` | Get order by ID |
| `POST` | `/api/orders` | Place a new order |

#### Place Order — Request Body
```json
{
  "customerName": "Jane Doe",
  "customerEmail": "jane@example.com",
  "items": [
    { "productId": 1, "quantity": 2 },
    { "productId": 3, "quantity": 1 }
  ]
}
```

#### Order Response
```json
{
  "id": 101,
  "customerName": "Jane Doe",
  "customerEmail": "jane@example.com",
  "status": "CONFIRMED",
  "totalPrice": 38.97,
  "createdAt": "2025-06-08T14:30:00",
  "orderItems": [
    { "id": 1, "quantity": 2, "price": 12.99, "product": { ... } },
    { "id": 2, "quantity": 1, "price": 12.99, "product": { ... } }
  ]
}
```

---

## ⚙️ Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 17 | Core language |
| Spring Boot | 3.x | Application framework |
| Spring Data JPA | — | ORM / data access |
| Spring Web MVC | — | REST API layer |
| Hibernate | — | JPA implementation |
| PostgreSQL | — | Relational database |
| Lombok | — | Boilerplate reduction (`@Getter`, `@Builder`, etc.) |
| Bean Validation | — | Input validation (`@NotBlank`, `@Email`, `@Min`) |
| springdoc-openapi | 2.x | Swagger UI / API documentation |
| dotenv-java | 3.2.0 | Environment variable management via `.env` |
| Maven | — | Build & dependency management |

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- Maven 3.8+
- PostgreSQL running locally

### 1. Clone the repository
```bash
git clone https://github.com/Ashish570raj/CreatorHub.git
cd CreatorHub
```

### 2. Configure environment variables

Create a `.env` file in the project root:
```env
Database_url=jdbc:postgresql://localhost:5432/creatorhub
Database_username=your_db_username
Database_password=your_db_password
```

### 3. Create the database
```sql
CREATE DATABASE creatorhub;
```

### 4. Run the application
```bash
./mvnw spring-boot:run
```

The API will start at **`http://localhost:8080`**

### 5. Explore the API
Open **Swagger UI** in your browser:
```
http://localhost:8080/swagger-ui.html
```

---

## 📦 Project Structure

```
src/main/java/com/ashishraj/creatorstore/
│
├── Controllers/
│   ├── ProductControllers.java   # Product CRUD endpoints
│   └── OrderController.java      # Order management endpoints
│
├── Services/
│   ├── ProductService.java       # Product business logic
│   └── OrderService.java         # Order processing + stock management
│
├── entities/
│   ├── Product.java              # Product JPA entity
│   ├── Order.java                # Order JPA entity
│   └── OrderItem.java            # Order line-item JPA entity
│
├── repositories/
│   ├── ProductRepository.java    # Product data access
│   ├── OrderRepository.java      # Order data access
│   └── OrderItemsRepository.java # OrderItem data access
│
├── dto/
│   ├── OrderRequest.java         # Validated order creation payload
│   └── OrderItemRequest.java     # Validated order item payload
│
└── CreatorstoreApplication.java  # Main entry point
```

---

## 🔑 Key Implementation Highlights

### Transactional Order Processing
The `OrderService.CreateOrder()` method is wrapped in `@Transactional`, ensuring that if **any product is out of stock**, the entire order — including all stock deductions — is rolled back atomically. No partial orders are ever saved.

```java
@Transactional
public Order CreateOrder(OrderRequest orderRequest) {
    // validates stock, calculates total, deducts inventory
    // all-or-nothing — rolls back fully on any failure
}
```

### Input Validation with DTOs
All incoming requests are validated before hitting the service layer using Bean Validation:
```java
@NotBlank(message = "Customer name is required")
@Email(message = "Invalid email format")
@Min(value = 1, message = "Quantity must be at least 1")
```

### Secure Configuration
Database credentials are **never hardcoded**. They are loaded from a `.env` file at startup using `dotenv-java` and injected via Spring's `${...}` placeholder syntax — keeping secrets out of version control.

---

## 👤 Author

**Ashish Raj**  
[GitHub](https://github.com/Ashish570raj)
