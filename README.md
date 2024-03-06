# payment-ms
demo of a payement microservice for spring-boot 3 native application 

## Documentation

http://localhost:8080/docs/index.html

http://localhost:8080/swagger-ui

## Compilation

Local Native image :

```bash
# first time only 
sdk install java 22.3.r17-nik
sdk use java 22.3.r17-nik

# after any changes
mvn clean install -U  -Dmaven.test.skip=true
mvn clean package -Pnative -Dmaven.test.skip=true
mvn -Pnative native:compile -Dmaven.test.skip=true
```


Docker native image :
```bash
# Docker need to be running
mvn clean install -U
mvn clean package -Pnative -Dmaven.test.skip=true
mvn -Pnative spring-boot:build-image -Dmaven.test.skip=true
```

build front and run front + back-end: 
```bash
mvn -Pnative spring-boot:build-image -Dmaven.test.skip=true
```


rapsberry 4:
apt-get install libz-dev
apt update && sudo apt upgrade
apt-get install libfreetype-dev