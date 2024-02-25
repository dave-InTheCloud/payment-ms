# payment-ms
demo of a payement microservice for spring-boot 3 native application 

## Compilation

Docker native image : 
```bash
# Docker need to be running
mvn clean install -U
mvn clean package -Pnative
mvn -Pnative spring-boot:build-image
```

Local Native image :

```bash
# first time only 
sdk install java 22.3.r17-nik
sdk use java 22.3.r17-nik

# after any changes
mvn clean install -U
mvn clean package -Pnative
mvn -Pnative native:compile 
```