# Transaction Scheduler API

A Spring Boot application for scheduling financial transactions between accounts with automatic fee calculation based on configurable business rules.

[![Java](https://img.shields.io/badge/Java-21-orange.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## Table of Contents

- [Overview](#overview)
- [Key Features](#key-features)
- [Prerequisites](#prerequisites)
- [Installation & Setup](#installation--setup)
- [Build the Project](#build-the-project)
- [Test Collections](#test-collections)

## Overview

The Transaction Scheduler API is a financial transaction management system that enables users to:

- **Schedule money transfers** between accounts for future execution
- **Automatically calculate fees** based on transfer amount and scheduling date
- **Query transactions** with flexible filtering (account, date range, pagination)
- **Update scheduled transactions** with automatic fee recalculation
- **Delete unwanted** scheduled transactions

### Key Features

**Automated Fee Calculation** - Fees are calculated based on configurable database rules  
**Clean Architecture** - Domain-driven design with clear separation of concerns  
**CQRS Pattern** - Command/Query separation for better maintainability  
**Strategy Pattern** - Pluggable fee calculation strategies loaded from database  
**OpenAPI Documentation** - Interactive Swagger UI for API exploration   
**Pagination Support** - Efficient data retrieval for large datasets

## Prerequisites

Before running this application, ensure you have:

- **Java 21** or higher
- **Maven 3.8+**
- **Git**
- **Your favorite IDE** (IntelliJ IDEA, Eclipse, VS Code)

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
```

### Test Collections

This project is fully documented using OpenAPI (Swagger) annotations, allowing you to explore and test the API interactively without any external tools.

#### SwaggerUI
After starting the application, navigate to the Swagger UI in your browser:
```
http://localhost:8080/swagger-ui/index.html
```

From there, you can:

- Browse all available endpoints (Commands & Queries)
- Inspect request/response schemas and validation constraints
- Try out requests directly from the browser
- View example payloads and error responses
- Test pagination, filtering, and validation scenarios interactively

This is the primary and recommended way to test the API, as it always reflects the latest contract defined in the code.


Pre-configured API test collections are provided in the `/collections` directory:

- **Postman**: `Transaction-Scheduler-API.postman_collection.json`
- **Insomnia**: `Transaction-Scheduler-API.insomnia.json`
- **Bruno**: `Transaction-Scheduler-API.bruno.json`