# URL Shortener

## About the Project
This project is a **URL Shortener** service that converts long URLs into short, user-friendly, and shareable links. It is built as a RESTful web service using Spring Boot and uses the H2 in-memory database for simplicity.

## Features
- Shorten long URLs.
- Allow users to set custom short IDs.
- Specify a time-to-live (TTL) for shortened URLs, after which they expire.
- Redirect to the long URL via the short URL.
- Delete short URLs by their IDs.

## Setup

### Requirements
- **Java 21** or newer
- **Maven**

### Installation Steps

1. Clone the repository:
   ```bash
   git clone <repository-url>
   cd url-shortener
   ```

2. Configure the application by updating `application.yml` if necessary:
   
3. Install dependencies:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

5. Access the service at `http://localhost:8080`. The H2 console is available at `http://localhost:8080/h2-console`.

## Usage

### Create a Shortened URL
#### Request:
```http
POST /create
Content-Type: application/json
{
  "longURL": "https://example.com",
  "ttlDay": 7
}
```

#### Response:
```json
{
  "shortUrl": "http://localhost:8080/abc12345"
}
```

### Redirect to the Long URL
#### Request:
```http
GET /abc12345
```

#### Response:
HTTP/1.1 301 Moved Permanently

### Delete a Shortened URL
#### Request:
```http
DELETE /abc12345
```

#### Response:
```json
{
  "message": "Short URL deleted successfully!"
}
```
## Testing
For this project, I focused on testing the business service methods using JUnit 5. 
These tests ensure that the core logic of the application behaves correctly under various scenarios. 
By isolating and validating the business service methods, potential issues can be detected early, maintaining the reliability of the service.

## Technologies
- **Spring Boot**: Backend framework.
- **Spring Data JPA**: Database interactions.
- **H2 Database**: In-memory database.
- **SLF4J**: Logging framework.

