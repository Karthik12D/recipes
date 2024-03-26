## Objective

Create a standalone java application which allows users to manage their favourite recipes. It should
allow adding, updating, removing and fetching recipes. Additionally users should be able to filter
available recipes based on one or more of the following criteria:
1. Whether or not the dish is vegetarian
2. The number of servings
3. Specific ingredients (either include or exclude)
4. Text search within the instructions.


For example, the API should be able to handle the following search requests:
1. All vegetarian recipes
2. Recipes that can serve 4 persons and have “potatoes” as an ingredient
3. Recipes without “salmon” as an ingredient that has “oven” in the instructions.

## Requirements
Please ensure that we have some documentation about the architectural choices and also how to
run the application. The project is expected to be delivered as a GitHub (or any other public git
hosting) repository URL.

All these requirements needs to be satisfied:

1. It must be a REST application implemented using Java (use a framework of your choice)
2. Your code should be production-ready.
3. REST API must be documented
4. Data must be persisted in a database
5. Unit tests must be present
6. Integration tests must be present

-----------------------------------------

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

-----------------------------------------
## My solution
I've tried to make it as much production ready as I could.
I exposed the REST API using Spring Boot, and I've used Spring Data JPA to persist the data in the H2 database.
I have used OpenAPI to document the REST API, and I've added the swagger-ui to make it easier to test the API.
I have added unit and integration tests.
I've decided to use simple relational db called H2 and made the relations between ingredients and recipes as many-to-many relationship, since many ingredients might be used in many recipes. 
Finally, I've added unit and integration test as much possible. Still can be added more but need to finish the assignment today