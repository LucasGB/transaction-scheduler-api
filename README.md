# Transaction Scheduler API

A Spring Boot application for scheduling financial transactions between accounts with automatic fee calculation based on configurable business rules.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## Table of Contents

- [Overview](#overview)
- [Business Rules](#business-rules)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Running the Application](#running-the-application)
- [API Documentation](#api-documentation)
- [Database Schema](#database-schema)
- [Testing](#testing)
- [Configuration](#configuration)

## Overview

The Transaction Scheduler API is a financial transaction management system that enables users to:

- **Schedule money transfers** between accounts for future execution
- **Automatically calculate fees** based on transfer amount and scheduling date
- **Query transactions** with flexible filtering (account, date range, pagination)
- **Update scheduled transactions** with automatic fee recalculation
- **Delete unwanted** scheduled transactions

### Key Features

✅ **Automated Fee Calculation** - Fees are calculated based on configurable database rules  
✅ **Clean Architecture** - Domain-driven design with clear separation of concerns  
✅ **CQRS Pattern** - Command/Query separation for better maintainability  
✅ **Strategy Pattern** - Pluggable fee calculation strategies loaded from database  
✅ **OpenAPI Documentation** - Interactive Swagger UI for API exploration   
✅ **Pagination Support** - Efficient data retrieval for large datasets

## 📋 Prerequisites

Before running this application, ensure you have:

- ☕ **Java 21** or higher ([Download OpenJDK](https://adoptium.net/))
- 📦 **Maven 3.8+** ([Download Maven](https://maven.apache.org/download.cgi))
- 🔧 **Git** (optional, for cloning)
- 🖥️ **Your favorite IDE** (IntelliJ IDEA, Eclipse, VS Code)

### Package Structure

```
io.github.lucasgb.transaction_scheduler_api/
│
├── domain/                          # Core business logic
│   ├── entity/
│   │   ├── TransactionSchedule      # Main aggregate root
│   │   └── TransactionFeeRule       # Fee configuration entity
│   ├── valueObjects/
│   │   └── Money                    # Financial amount value object
│   ├── enums/
│   │   └── CurrencyEnum             # Supported currencies
│   ├── interfaces/                  # Domain contracts
│   │   ├── TransactionScheduleRepository
│   │   ├── TransactionFeeRuleRepository
│   │   └── TransactionFeeStrategy
│   └── service/
│       ├── TransactionFeeCalculationService
│       └── strategy/
│           └── ConfigurableTransactionFeeStrategy
│
├── application/                     # Use cases orchestration
│   ├── command/                     # Write operations
│   ├── query/                       # Read operations (if separated)
│   ├── dto/
│   │   ├── request/                 # API request DTOs
│   │   └── response/                # API response DTOs
│   ├── handler/                     # Command/Query handlers
│   └── service/
│       └── TransactionFeeStrategyFactory
│
├── infrastructure/                  # External concerns
│   ├── repository/
│   │   └── jpa/                     # JPA implementations
│   ├── presentation/
│   │   └── controller/              # REST controllers
│   └── exception/
│       └── GlobalExceptionHandler   # Global error handling
│
└── config/                          # Spring configuration
    ├── OpenApiConfig                # Swagger/OpenAPI setup
    └── CacheConfig                  # Caching configuration
```



## Installation & Setup

### 1. Clone the Repository

```bash
git clone https://github.com/lucasgb/transaction-scheduler-api.git
cd transaction-scheduler-api
```

### 2. Build the Project

```bash
# Clean and build
mvn clean install

# Skip tests (faster build)
mvn clean install -DskipTests

## 🧪 Testing

### Running Tests

```bash
# Run all tests
mvn test

# Run with coverage report
mvn clean test jacoco:report

# Run specific test class
mvn test -Dtest=TransactionScheduleControllerTest
```

### Test Collections

Pre-configured API test collections are provided in the `/collections` directory:

- **Postman**: `Transaction-Scheduler-API.postman_collection.json`
- **Insomnia**: `Transaction-Scheduler-API.insomnia.json`
- **Bruno**: `Transaction-Scheduler-API.bruno.json`