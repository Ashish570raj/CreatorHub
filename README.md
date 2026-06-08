# CreatorStore — Spring Boot REST API

A lightweight backend for a creator-focused online store with product management and order processing built on Spring Boot.

## What this project does

- Manages product items with create, read, update, and delete APIs
- Processes orders containing multiple products in a single request
- Validates inventory availability before confirming orders
- Updates product stock quantity automatically during order creation
- Uses Spring Data JPA for database access and Hibernate for schema management

## Why it is useful

This API is designed for creators who need a simple backend service to power a storefront or mobile app without building a full commerce platform.

- Keeps inventory in sync with orders
- Ensures clean validation for products and order payloads
- Separates REST controllers, business logic, and persistence cleanly
- Supports easy extension for shipping, payment, or customer accounts later

## API Endpoints

### Products

- `GET /api/products` — Retrieve all products
- `GET /api/products/{id}` — Retrieve one product by ID
- `POST /api/products` — Create a new product
- `PUT /api/products/{id}` — Update an existing product
- `DELETE /api/products/{id}` — Delete a product

#### Product payload example
```json
{
  "name": "TShirt",
  "description": "Cotton T-shirt",
  "category": "Apparel",
  "price": 199,
  "stockQuantity": 100
}
```

### Orders

- `GET /api/orders` — Retrieve all orders
- `GET /api/orders/{id}` — Retrieve one order by ID
- `POST /api/orders` — Place a new order

#### Order payload example
```json
{
  "customerName": "Ashish Raj",
  "customerEmail": "ashish@gmail.com",
  "items": [
    {
      "productId": 1,
      "quantity": 5
    }
  ]
}
```

## Project structure

```text
src/main/java/com/ashishraj/creatorstore/
  Controllers/
    ProductControllers.java
    OrderController.java
  Services/
    ProductService.java
    OrderService.java
  entities/
    Product.java
    Order.java
    OrderItem.java
  repositories/
    ProductRepository.java
    OrderRepository.java
    OrderItemsRepository.java
  dto/
    OrderRequest.java
    OrderItemRequest.java
CreatorstoreApplication.java
```

## Key features

- **Validation**: Uses Bean Validation annotations like `@NotBlank`, `@Email`, `@Min`, and `@NotEmpty`
- **Transactional order processing**: The order creation flow is atomic, so stock updates and order save happen together
- **RESTful design**: Clean controller endpoints for products and orders
- **Entity relations**: `Order`, `OrderItem`, and `Product` are connected through JPA relationships

## Setup

### Prerequisites

- Java 17 or later
- Maven 3.8+
- PostgreSQL or another supported database

### Run locally

1. Clone the repository
   ```bash
   git clone https://github.com/Ashish570raj/creatorstore.git
   cd creatorstore
   ```
2. Create a PostgreSQL database
   ```sql
   CREATE DATABASE creatorstore;
   ```
3. Create a `.env` file in the project root with your database settings
   ```env
   Database_url=jdbc:postgresql://localhost:5432/creatorstore
   Database_username=your_db_username
   Database_password=your_db_password
   ```
4. Start the app
   ```bash
   ./mvnw spring-boot:run
   ```
5. Visit
   ```text
   http://localhost:8080
   ```

## Notes

- The project currently manages orders and inventory, but does not include payment or checkout integration.
- Product stock is deducted only when an order is created successfully.
- If validation fails for any order item, the whole order is rejected.

## Author

Ashish Raj

