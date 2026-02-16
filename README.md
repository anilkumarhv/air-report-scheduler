# air-report-scheduler

[![Build and deploy JAR app to Azure Web App - air-report-scheduler](https://github.com/anilkumarhv/air-report-scheduler/actions/workflows/main_air-report-scheduler.yml/badge.svg?branch=main)](https://github.com/anilkumarhv/air-report-scheduler/actions/workflows/main_air-report-scheduler.yml)

A Spring Boot application that schedules and retrieves aviation weather reports (PIREP and METAR) from the NOAA Aviation Digital Data Service (ADDS).

## Features

- Scheduled retrieval of PIREP (Pilot Reports) and METAR weather data
- REST API for querying aviation weather reports
- Duplicate detection to prevent redundant data storage
- Rate limiting to protect API endpoints
- Spring Security with Basic Authentication
- Retry logic for transient API failures
- Input validation for station codes and date ranges

## Prerequisites

- Java 17+
- Maven 3.6+
- PostgreSQL (production) or H2 (development/testing)

## Configuration

### Environment Variables

Copy `.env.example` to `.env` and configure:

```bash
DB_URL=jdbc:postgresql://localhost:5432/aviation_air_report
DB_USERNAME=your_username
DB_PASSWORD=your_password
API_USERNAME=admin
API_PASSWORD=changeme
ADMIN_USERNAME=admin
ADMIN_PASSWORD=admin123
```

### Application Profiles

- `local` (default): Uses environment variables for database connection
- `mysql`: MySQL database configuration

## Building and Running

```bash
# Build
mvn clean install

# Run
mvn spring-boot:run

# Run with specific profile
mvn spring-boot:run -Dspring-boot.run.profiles=local
```

## API Endpoints

### Health Check
```
GET /healthCheck          (no auth required)
GET /actuator/health      (no auth required)
```

### Aviation Data (requires authentication)
```
GET  /api/dataserver/aviation/metars?station=KJFK&startTime=2024-01-01T00:00:00Z&endTime=2024-01-02T00:00:00Z
POST /api/dataserver/aviation/metars?station=KJFK&startTime=2024-01-01T00:00:00Z&endTime=2024-01-02T00:00:00Z
```

### Authentication
```bash
curl -u admin:changeme http://localhost:8080/api/dataserver/aviation/metars?station=KJFK&startTime=2024-01-01T00:00:00Z&endTime=2024-01-02T00:00:00Z
```

### Validation Rules
- Station code: 3-4 uppercase letters (e.g., KJFK, LAX)
- Start time / end time: ISO 8601 format
- End time must be after start time

## Security

- Actuator endpoints restricted (health/info public, others require auth)
- API endpoints protected with Basic Authentication
- No hardcoded credentials (environment variable based)
- Rate limiting: 20 requests/minute per application instance
- Input validation on all API parameters

## Testing

```bash
# Run tests
mvn clean test

# Run tests with coverage report
mvn clean verify
# Coverage report: target/site/jacoco/index.html
```

## Project Structure

```
src/main/java/com/anil/airreportscheduler/
├── config/          # Security, retry, and rate limit configuration
├── controller/      # REST API controllers
├── exception/       # Global exception handling
├── filter/          # Rate limiting filter
├── model/           # JPA entities and data models
├── repository/      # Spring Data JPA repositories
├── scheduler/       # Scheduled tasks (PIREP, METAR)
├── service/         # Business logic and NOAA API integration
└── validation/      # Custom validation annotations
```
