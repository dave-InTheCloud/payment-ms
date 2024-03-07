# Payment Microservice

This project demonstrates a native application built with GraalVM, Spring Boot 3, and React for currency exchange and transfer functionalities.

## Try it
**Executables:**

* Download pre-built executables from [here](https://drive.google.com/drive/folders/1C2MaGHbeWRAo40la6neMqcKMGIk-WT9s?usp=drive_link).

**Deployed version:**

* Access the application from a web browser at the provided server [URL](https://payment.daveinthecloud.wiki/).

## Application URLs (when running locally):

* **Front-end:** http://localhost:8080/
* **Business Documentation (AsciiDoc):**
    * http://localhost:8080/docs/index.html (HTML)
    * http://localhost:8080/docs/index.pdf (PDF)
* **REST API Documentation (Swagger UI):** http://localhost:8080/swagger-ui
* **DB web console:** http://localhost:8080/h2-console (jdbc:h2:mem:testdb  user: spaceFan pwd: spaceFan)
* **Monitoring endpoints:** http://localhost:8080/actuator/health

## Technical Stack

**Back-end:**

* **Spring Boot 3 native (Java) web framework producing native application bytecode** with GraalVM support for potential AOT compilation and native image generation.
* **Flyway + Spring Data JPA + H2 for database migration and access**
* **Asciidoctor and Swagger for documentation**
* **MapStruct, Jackson, and Lombok for back-end data binding**
* **Actuator for monitoring**

**Front-end:**

* **React:** Popular JavaScript library for building dynamic and user-friendly interfaces.

**GraalVM:**

* **Optional:** GraalVM can be used for AOT compilation and native image generation of the Spring Boot application, potentially improving performance and startup time.

## Compilation Instructions

### Classic JAR Build (Frontend + Backend - for Development):

```bash
# Build back-end
mvn clean install -U
# Build front-end
mvn clean install -U -Pprod

# Build front-end and run both
./mvnw spring-boot:run -Pprod
```

### Local Native Image

**Requirements:**

* **Windows:** Install GraalVM JDK from https://www.graalvm.org/latest/docs/getting-started/.
* **Linux:** Install Java 22.3.r17-nik using SDKMAN: https://sdkman.io/

**Instructions:**

1. **After changes:**
    ```bash
    mvn clean package -Pprod -Pnative -Dmaven.test.skip=true
    ```

2. **Compile the native image:**
    ```bash
    mvn -Pprod -Pnative native:compile -Dmaven.test.skip=true
    ```

### Docker native image :
* requirements docker installed

```bash
mvn clean package -Pprod -Pnative -Dmaven.test.skip=true
mvn -Pprod -Pnative spring-boot:build-image -Dmaven.test.skip=true
```

**Required dependencies for raspberry 4 native compilation:**
* apt-get install libz-dev
* apt update && sudo apt upgrade
* apt-get install libfreetype-dev