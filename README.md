# 🌤️ Weather App

A RESTful web application built with **Java Spring Boot** that fetches real-time weather data from the [Visual Crossing Weather API](https://www.visualcrossing.com/). Users can retrieve weather information for any city by providing the city name directly in the URL path.

---
## ✨ Features

- 🔍 Fetch current weather data by city name via a simple URL path
- 📦 Returns structured JSON responses with weather information
- ⚠️ Global exception handling with descriptive error messages
- ⚙️ Clean layered architecture (Controller → Service → Model)

---

## 🛠️ Tech Stack

| Technology | Description |
|------------|-------------|
| Java | Core programming language |
| Spring Boot | Application framework |
| Maven | Dependency management & build tool |
| Visual Crossing API | External weather data provider |

---

## 📁 Project Structure

```
weather.app/
├── src/
│   └── main/
│       ├── java/
│       │   └── com.api.corporationX.weather.app/
│       │       ├── controllers/
│       │       │   └── WeatherController.java      # Handles HTTP requests
│       │       ├── exceptions/
│       │       │   ├── ErrorDetails.java           # Error response model
│       │       │   └── GlobalExceptionHandler.java # Centralized exception handling
│       │       ├── models/
│       │       │   └── WeatherResponse.java        # Weather data model
│       │       ├── services/
│       │       │   └── WeatherService.java         # Business logic & API calls
│       │       └── Application.java               # Spring Boot entry point
│       └── resources/
│           ├── application.properties              # App configuration (private)
│           └── application.properties.example      # Configuration template
├── pom.xml
└── mvnw / mvnw.cmd
```

---

## 🚀 Getting Started

### Prerequisites

- **Java 17+** installed
- **Maven** (or use the included `mvnw` wrapper)
- A free **Visual Crossing API key** — get one at [visualcrossing.com](https://www.visualcrossing.com/)

### Configuration

1. Copy the example properties file:

```bash
cp src/main/resources/application.properties.example src/main/resources/application.properties
```

2. Open `application.properties` and fill in your API key and server configuration:

```properties
weather.api.key=YOUR_VISUAL_CROSSING_API_KEY
weather.api.base-url=https://weather.visualcrossing.com/VisualCrossingWebServices/rest/services/timeline/
server.servlet.context-path=/weather-app/api/v1
```

> ⚠️ `application.properties` is listed in `.gitignore` to keep your API key private. Never commit it to version control.

### Running the Application

Using the Maven wrapper:

```bash
# On Linux / macOS
./mvnw spring-boot:run

# On Windows
mvnw.cmd spring-boot:run
```

The application will start on `http://localhost:8080` by default.

---

## 📡 API Usage

The application context path is set to `/weather-app/api/v1` in `application.properties`:

```properties
server.servlet.context-path=/weather-app/api/v1
```

### Get weather by city

```
GET /weather-app/api/v1/weather/{city}
```

**Example:**

```bash
curl http://localhost:8080/weather-app/api/v1/weather/London
```

**Response:**

```json
{
  "resolvedAddress": "London, England, United Kingdom",
  "timezone": "Europe/London",
  "datetime": "13:00:00",
  "temp": 15.3,
  "conditions": "Partially cloudy"
}
```

---

## ⚠️ Error Handling

The application includes a `GlobalExceptionHandler` that returns structured error responses for invalid requests or API failures:

```json
{
  "timestamp": "2025-04-25T10:30:00",
  "status": 404,
  "error": "City not found",
  "message": "No weather data available for the specified city."
}
```

---
## 📄 License

This project is for educational purposes. Feel free to use it as a reference or starting point for your own Spring Boot projects.