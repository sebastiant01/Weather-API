package com.api.corporationX.weather.app.controllers;

import com.api.corporationX.weather.app.models.WeatherResponse;
import com.api.corporationX.weather.app.services.IntWeatherService;
import com.api.corporationX.weather.app.services.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller that exposes the weather endpoints for the Weather App API.
 *
 * <p>Incoming requests are delegated to {@link IntWeatherService}, decoupling the
 * controller from the concrete {@link WeatherService} implementation and allowing
 * the service to be swapped or mocked without modifying this class.</p>
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
     * Service interface responsible for fetching weather data from the external API.
     * Injected automatically by Spring's dependency injection container.
     * The concrete implementation is {@link WeatherService}.
     */
    @Autowired
    private IntWeatherService weatherService;

    /**
     * Retrieves the current weather data for the specified city.
     *
     * <p>The city name is extracted from the URL path and forwarded to
     * {@link IntWeatherService#getWeather(String)}. The optional {@code detail}
     * query parameter controls how much data is included in the response:</p>
     * <ul>
     *   <li>{@code simple} (default) — returns core fields only: location, timezone,
     *       date, time, temperature, and conditions</li>
     *   <li>{@code full} — returns all fields, including humidity, precipitation,
     *       and a day description</li>
     * </ul>
     *
     * <p><b>Endpoint:</b> {@code GET /weather-app/api/v1/weather/{city}}</p>
     *
     * <p><b>Example requests:</b></p>
     * <pre>{@code
     * GET /weather-app/api/v1/weather/London
     * GET /weather-app/api/v1/weather/London?detail=full
     * }</pre>
     *
     * <p><b>Simple response (default):</b></p>
     * <pre>{@code
     * {
     *   "cityLocation": "London, England, United Kingdom",
     *   "cityContinent": "Europe/London",
     *   "date": "2025-04-25",
     *   "currentTime": "13:00:00",
     *   "temperature": 15.3,
     *   "countryCondition": "Partially cloudy"
     * }
     * }</pre>
     *
     * <p><b>Full response ({@code ?detail=full}):</b></p>
     * <pre>{@code
     * {
     *   "cityLocation": "London, England, United Kingdom",
     *   "cityContinent": "Europe/London",
     *   "date": "2025-04-25",
     *   "currentTime": "13:00:00",
     *   "temperature": 15.3,
     *   "humidity": 72.0,
     *   "precipitation": 0.0,
     *   "countryCondition": "Partially cloudy",
     *   "descriptionOfDay": "Partly cloudy throughout the day."
     * }
     * }</pre>
     *
     * @param city   the name of the city to retrieve weather data for (path variable)
     * @param detail controls the verbosity of the response; accepts {@code "simple"} (default)
     *               or {@code "full"} (case-insensitive)
     * @return a {@link ResponseEntity} containing a {@link WeatherResponse} with HTTP 200,
     *         or an error response handled by
     *         {@link com.api.corporationX.weather.app.exceptions.GlobalExceptionHandler}
     */
    @GetMapping("/{city}")
    public ResponseEntity<WeatherResponse> getWeatherByCity(
            @PathVariable String city,
            @RequestParam(value = "detail", defaultValue = "simple") String detail
    ) {
        WeatherResponse weather = weatherService.getWeather(city);

        if (!detail.equalsIgnoreCase("full")) {
            weather.convertToSimple();
        }
        return ResponseEntity.ok(weather);
    }
}