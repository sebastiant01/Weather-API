package com.api.corporationX.weather.app.controllers;

import com.api.corporationX.weather.app.models.WeatherResponse;
import com.api.corporationX.weather.app.services.IntWeatherService;
import com.api.corporationX.weather.app.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller that exposes the weather endpoints for the Weather App API.
 *
 * <p>Incoming requests are delegated to {@link WeatherService}, which handles
 * the communication with the external Visual Crossing Weather API.</p>
 *
 * <p>The application context path is configured in {@code application.properties} as
 * {@code server.servlet.context-path=/weather-app/api/v1}, so the full base URL
 * for all endpoints in this controller is:</p>
 * <pre>{@code /weather-app/api/v1/weather}</pre>
 */
@RestController
@RequestMapping("/weather")
public class WeatherController {

    /**
     * Service responsible for fetching weather data from the external API.
     * Injected automatically by Spring's dependency injection container.
     */
    @Autowired
    private IntWeatherService weatherService;

    /**
     * Retrieves the current weather data for the specified city.
     *
     * <p>The city name is extracted directly from the URL path and forwarded
     * to the {@link WeatherService}. On success, returns a {@code 200 OK} response
     * with the weather data as JSON.</p>
     *
     * <p><b>Endpoint:</b> {@code GET /weather-app/api/v1/weather/{city}}</p>
     *
     * <p><b>Example request:</b></p>
     * <pre>{@code GET /weather-app/api/v1/weather/London}</pre>
     *
     * <p><b>Example response:</b></p>
     * <pre>{@code
     * {
     *   "resolvedAddress": "London, England, United Kingdom",
     *   "timezone": "Europe/London",
     *   "currentConditions": {
     *     "datetime": "13:00:00",
     *     "temp": 15.3,
     *     "conditions": "Partially cloudy"
     *   }
     * }
     * }</pre>
     *
     * @param city the name of the city to retrieve weather data for (path variable)
     * @return a {@link ResponseEntity} containing a {@link WeatherResponse} with HTTP 200,
     *         or an error response handled by {@link com.api.corporationX.weather.app.exceptions.GlobalExceptionHandler}
     */
    @GetMapping("/{city}")
    public ResponseEntity<WeatherResponse> getWeatherByCity(@PathVariable String city) {
        WeatherResponse weather = weatherService.getWeather(city);
        return ResponseEntity.ok(weather);
    }
}