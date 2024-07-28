# microservice

This project is an application for NISUM using Java, Spring Boot.

Service that has an endpoint for creating users.

## Getting Started

### Prerequisites

* Git
* JDK 17 or later
* Maven 3.0 or later

### Clone

To get started you can simply clone this repository using git:

``` cmd
git clone https://github.com/mastererickdiaz/nisum.git
cd nisum
```

### Configuration

In order to get your chatbot working you have to provide the following settings:

The configuration is located in `src/resources/application.yml`.

### Build an executable JAR

You can build a single executable JAR file that contains all the necessary dependencies, classes, and resources with:

```cmd
mvn clean package
```

You can run the application from the command line using:

```cmd
mvn spring-boot:run
```

Then you can run the JAR file with:

```cmd
java -jar target/*.jar
```

*Instead of `mvn` you can also use the maven-wrapper `./mvnw` to ensure you have everything necessary to run the Maven build.*

## License

This project is licensed under the terms of the [MIT license](LICENSE).
