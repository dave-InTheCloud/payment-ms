# Payment Microservice

This project demonstrates a native application built with GraalVM, Spring Boot 3, and React for currency exchange and transfer functionalities.

**Executables:**

* Download pre-built executables from [here](https://drive.google.com/drive/folders/1C2MaGHbeWRAo40la6neMqcKMGIk-WT9s?usp=drive_link).
* If the executables are unavailable, follow the compilation instructions below.

**Running the Application from Server (if executables are unavailable):**

* Access the application from a web browser at the provided server [URL](https://payment.daveinthecloud.wiki/).

## Application URLs (when running locally):

* **Front-end:** http://localhost:8080/
* **Business Documentation (AsciiDoc):**
    * http://localhost:8080/docs/index.html (HTML)
    * http://localhost:8080/docs/index.pdf (PDF)
* **REST API Documentation (Swagger UI):** http://localhost:8080/swagger-ui

## Compilation Instructions

### Classic JAR Build (Frontend + Backend - for Development):

```bash
# Build back-end
mvn clean install -U

# Build front-end and run both
mvn ./mvnw spring-boot:run -Pprod
```

### Local Native Image

**Requirements:**

* **Windows:** Install GraalVM JDK from https://www.graalvm.org/latest/docs/getting-started/.
* **Linux:** Install Java 22.3.r17-nik using SDKMAN: https://sdkman.io/

**Instructions:**

1. **After any changes:**
    ```bash
    mvn clean package -Pprod -Pnative -Dmaven.test.skip=true
    ```

2. **Compile the native image:**
    ```bash
    mvn -Pnative native:compile -Dmaven.test.skip=true
    ```