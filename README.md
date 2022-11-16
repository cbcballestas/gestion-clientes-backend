# GestionClientesBACKEND
Backend para sistema de gestión de clientes, curso Angular & Spring Boot: Creando web app full stack, se usa como motor de base de datos PostgreSQL

## Requirements
- [JDK 8](https://www.oracle.com/co/java/technologies/javase/javase8-archive-downloads.html)
- [Maven 3](https://maven.apache.org/)
- PostgreSQL

You will need to setup your database (or create your own) with the following configuration
``` properties
spring.datasource.url=jdbc:postgresql://localhost:5433/db_name
spring.datasource.username=
spring.datasource.password=
```

## Run application

There are several ways to run a Spring Boot application in your local machine. The most common way is executing `main` method in `com.cballestas.springboot.backend.apirest.SpringBootBackendApirestApplication` class from your IDE

Alternative you can use [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle), just running:

```shell
mvn spring-boot:run
```

## Running port
- default spring boot config
  http://localhost:8088
  
## Download Frontend project
You can download front-end project at [gestion-clientes-frontend](https://github.com/cbcballestas/gestion-clientes-frontend)
