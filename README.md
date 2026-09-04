# URL Shortener - Spring Boot Maven

A small but practical Spring Boot application for creating short URLs and tracking click counts.

## Concept

Unlike a normal CRUD dashboard, this project demonstrates a URL-shortening workflow:

1. User submits a long URL.
2. API generates a unique 7-character code.
3. User gets a short URL such as `/r/spring01`.
4. Opening the short URL redirects to the original URL.
5. Every redirect increments the click counter.
6. Analytics endpoint returns click information.
7. A link can be deactivated.

## Technology

- Java 17+
- Spring Boot 4.1.1
- Maven only
- Spring Web MVC
- Spring Data JPA
- H2
- Bean Validation
- Actuator
- HTML/CSS/JavaScript frontend

Spring Boot's Maven plugin supports executable JAR packaging and Maven-based running of Spring Boot applications.

## Run

```bash
mvn clean test
mvn clean package
java -jar target/url-shortener-1.0.0.jar
```

Or:

```bash
mvn spring-boot:run
```

Open:

http://localhost:8080

## API Examples

Create a short URL:

```http
POST /api/urls
Content-Type: application/json

{
  "originalUrl": "https://spring.io/"
}
```

List URLs:

```http
GET /api/urls
```

Get one URL:

```http
GET /api/urls/{code}
```

Redirect:

```http
GET /r/{code}
```

Analytics:

```http
GET /api/urls/{code}/analytics
```

Deactivate:

```http
DELETE /api/urls/{code}
```

Health:

```http
GET /actuator/health
```

H2 Console:

http://localhost:8080/h2-console

JDBC URL:

```text
jdbc:h2:mem:urlshortener
```

Username:

```text
sa
```

Password:

```text
(empty)
```

## Git

After extracting:

```bash
git init
git add .
git commit -m "Initial URL shortener service"
```

## Suggested extensions

- Custom aliases
- Expiration dates
- QR code generation
- Per-day click analytics
- User authentication
- Redis cache
- PostgreSQL
- Docker
- Rate limiting
- OpenAPI/Swagger
