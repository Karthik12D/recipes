
# Recipe Management System

A system built to manage, store, and retrieve recipes with ease. Designed using the Layered Architectural Design pattern, maintainability, and efficient data access.

## Table of Contents

- [Features](#features)
- [Architecture](#architecture)
- [Setup guide](#setup-guide)
- [Minimum Requirements](#minimum-requirements)
- [Technologies](#technologies)
- [My solution](#my-solution)

## Architecture

**Layered Architectural Design Pattern**:  
The system is organized into distinct layers to separate concerns:

1. **View/Controller Layer**: Manages the GUI and user interactions.
2. **Service Layer**: Handles the business logic of the application, making use of DTOs (Data Transfer Objects) for efficient data operations.
3. **DAO Layer**: Interfaces with the database for CRUD operations.

This separation ensures that changes in one layer don't necessarily impact others, making the system more maintainable and scalable.

## Features

- **Search Recipes**: Quick search using the first letter to retrieve all relevant recipes.
- **CRUD Operations**: Create, Read, and Update operations for recipes. (Delete operation is under development).
- **Ingredient Management**: Separate management system for ingredients linked to recipes (currently under development).
- **Error Handling**: Common handling of exceptions with error messages.
- 
## Setup guide

#### Minimum Requirements

- Java 17
- Maven 3.x

#### Install the application

1. Make sure you have [Java](https://www.oracle.com/java/technologies/downloads/#jdk17)
2. Open the command line in the source code folder

3. Build project

  ```
  $ mvn clean install
  ```

Run the tests
  ```
  $ mvn test
  ```


Run the project

  ```
  $ java -jar app.jar
  ```

4. Open the swagger-ui with the link below

```text
http://localhost:8090/openapi/swagger-ui/index.html
```

## Technologies

Java 17, Spring Boot 3.2, Spring Data JPA, H2 Database, OpenAPI, Swagger-UI, Maven, JUnit, Mockito


-----------------------------------------
## My solution
I've tried to make it as much production ready as I could.
I exposed the REST API using Spring Boot, and I've used Spring Data JPA to persist the data in the H2 database.
I have used OpenAPI to document the REST API, and I've added the swagger-ui to make it easier to test the API.
I have added unit and integration tests.
I've decided to use simple relational db called H2 and made the relations between ingredients and recipes as many-to-many relationship, since many ingredients might be used in many recipes. 
Finally, I've added unit and integration test as much possible. Still can be added more but need to finish the assignment today