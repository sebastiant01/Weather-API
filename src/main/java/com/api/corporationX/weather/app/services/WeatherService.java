package com.api.corporationX.weather.app.services;

import com.api.corporationX.weather.app.models.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Service layer responsible for communicating with the Visual Crossing Weather API.
 *
 * <p>This class handles the construction of the external API request URL and delegates
 * the HTTP call to {@link RestTemplate}. The API key and base URL are injected from
 * the application configuration, keeping credentials out of the source code.</p>
 *
 * <p>The response is automatically deserialized into a {@link WeatherResponse} object
 * by Jackson.</p>
 */
@Service
public class WeatherService implements IntWeatherService {

    /**
     * Spring-managed HTTP client used to perform requests to the external Weather API.
     * Injected automatically by Spring's dependency injection container.
     */
    @Autowired
    private RestTemplate restTemplate;

    /**
     * The Visual Crossing API key, injected from {@code weather.api.key}
     * in {@code application.properties}.
     */
    @Value("${weather.api.key}")
    private String API_KEY;

    /**
     * The base URL of the Visual Crossing Weather API, injected from
     * {@code weather.api.base-url} in {@code application.properties}.
     */
    @Value("${weather.api.base-url}")
    private String BASE_URL;

    /**
     * Fetches current weather data for the specified city from the Visual Crossing API.
     *
     * <p>Builds the full request URL using the base URL, city name, and query parameters,
     * then performs an HTTP GET request. The JSON response is deserialized directly
     * into a {@link WeatherResponse} object.</p>
     *
     * <p>The request uses metric units ({@code unitGroup=metric}) so temperature
     * is returned in Celsius.</p>
     *
     * @param city the name of the city to query (e.g., {@code "London"})
     * @return a {@link WeatherResponse} containing the city's current weather data
     * @throws org.springframework.web.client.HttpClientErrorException.NotFound if the city is not found (HTTP 404)
     * @throws org.springframework.web.client.HttpClientErrorException.Unauthorized if the API key is invalid (HTTP 401)
     */
    @Override
    public WeatherResponse getWeather(String city) {
        String url = String.format("%s%s?unitGroup=metric&key=%s&contentType=json",
                BASE_URL, city, API_KEY);
        return restTemplate.getForObject(url, WeatherResponse.class);
    }
}