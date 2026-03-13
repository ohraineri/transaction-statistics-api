# Transaction Statistics API (Itaú Backend Challenge)

High-performance REST API built with **Java + Spring Boot** that
receives transactions and calculates **real-time statistics for the last
60 seconds**, storing all data **in memory**.

This project is an implementation of the **Itaú Unibanco Backend
Programming Challenge**, designed to evaluate software engineering
practices such as API design, validation, code organization, and
performance.

------------------------------------------------------------------------

## Overview

The API allows clients to:

-   Submit financial transactions
-   Clear stored transactions
-   Retrieve real-time statistics based on the transactions received in
    the last **60 seconds**

All data is stored **in memory**, meaning no external database or cache
is used.

------------------------------------------------------------------------

## Tech Stack

-   Java 21+
-   Spring Boot
-   Maven
-   REST API
-   JSON

------------------------------------------------------------------------

## Architecture

The application follows a simple layered architecture:

Controller\
↓\
Service\
↓\
In-Memory Storage

**Controller** - Handles HTTP requests - Validates request structure -
Returns proper HTTP responses

**Service** - Business logic - Transaction validation - Statistics
calculation

**Storage** - In-memory transaction storage - Maintains transactions
within the last 60 seconds

------------------------------------------------------------------------

# API Endpoints

## Create Transaction

**Endpoint**

POST /transacao

**Request Body**

``` json
{
  "valor": 123.45,
  "dataHora": "2020-08-07T12:34:56.789-03:00"
}
```

**Rules**

The API only accepts transactions that:

-   contain both `valor` and `dataHora`
-   have non-negative values
-   do not occur in the future

**Responses**

-   201 Created --- Transaction successfully recorded
-   422 Unprocessable Entity --- Invalid transaction
-   400 Bad Request --- Malformed JSON

------------------------------------------------------------------------

## Delete Transactions

**Endpoint**

DELETE /transacao

**Response**

-   200 OK --- All transactions deleted

------------------------------------------------------------------------

## Get Statistics

**Endpoint**

GET /estatistica

**Response Example**

``` json
{
  "count": 10,
  "sum": 1234.56,
  "avg": 123.456,
  "min": 12.34,
  "max": 123.56
}
```

If no transactions exist in the last 60 seconds, all values must return
**0**.

------------------------------------------------------------------------

# Running the Project

## Clone the repository

``` bash
git clone [https://github.com/ohraineri/transaction-statistics-api](https://github.com/ohraineri/transaction-statistics-api)
```

## Enter the project directory

``` bash
cd transaction-statistics-api
```

## Run the application

``` bash
./mvnw spring-boot:run
```

or

``` bash
mvn spring-boot:run
```

The API will start on:

http://localhost:8080

------------------------------------------------------------------------

# Example Requests

### Create Transaction

``` bash
curl -X POST http://localhost:8080/transacao -H "Content-Type: application/json" -d '{
"valor": 100.50,
"dataHora": "2024-01-01T10:00:00.000-03:00"
}'
```

### Get Statistics

``` bash
curl http://localhost:8080/estatistica
```

### Delete Transactions

``` bash
curl -X DELETE http://localhost:8080/transacao
```

------------------------------------------------------------------------

# Challenge Requirements

This implementation follows the official challenge requirements:

-   No database usage
-   All data stored in memory
-   JSON-only communication
-   Exact endpoint specification
-   Real-time statistics calculation

------------------------------------------------------------------------

# Possible Improvements

Some improvements that could be added:

-   Unit and integration tests
-   Docker containerization
-   OpenAPI / Swagger documentation
-   Custom error handling
-   Performance benchmarks
-   Configurable statistics window

Example:

statistics.window.seconds=60

------------------------------------------------------------------------

# Author

Developed as part of the Itaú Backend Programming Challenge.
