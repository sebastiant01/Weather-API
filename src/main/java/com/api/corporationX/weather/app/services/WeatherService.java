package com.api.corporationX.weather.app.services;

import com.api.corporationX.weather.app.models.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Concrete implementation of {@link IntWeatherService} responsible for communicating
 * with the Visual Crossing Weather API.
 *
 * <p>This class handles the construction of the external API request URL and delegates
 * the HTTP call to {@link RestTemplate}. The API key and base URL are injected from
 * the application configuration, keeping credentials out of the source code.</p>
 *
 * <p>The response JSON is automatically deserialized into a {@link WeatherResponse}
 * object by Jackson.</p>
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
     * {@inheritDoc}
     *
     * <p>Builds the full request URL by combining the base URL, the city name, and
     * the following query parameters:</p>
     * <ul>
     *   <li>{@code unitGroup=metric} — returns temperature in Celsius</li>
     *   <li>{@code key} — the Visual Crossing API key</li>
     *   <li>{@code contentType=json} — requests a JSON response</li>
     * </ul>
     *
     * <p>Results are cached in Redis under the {@code weather} cache using the city name as key.
     * Subsequent calls with the same city are served directly from Redis for up to 10 minutes,
     * bypassing the external API call entirely. Cache configuration is defined in
     * {@link com.api.corporationX.weather.app.Application#redisCacheManagerBuilderCustomizer()}.</p>
     *
     * @param city the name of the city to retrieve weather data for
     * @return a {@link WeatherResponse} with the current weather data for the given city,
     *         either from cache or freshly fetched from the Visual Crossing API
     * @throws org.springframework.web.client.HttpClientErrorException.NotFound
     *         if the city is not found by the external API (HTTP 404)
     * @throws org.springframework.web.client.HttpClientErrorException.Unauthorized
     *         if the configured API key is invalid (HTTP 401)
     * @throws java.net.UnknownHostException
     *         if the Visual Crossing hostname cannot be resolved (connectivity or DNS failure)
     */
    @Override
    @Cacheable(value = "weather", key = "#city")
    public WeatherResponse getWeather(String city) {
        String url = String.format("%s%s?unitGroup=metric&key=%s&contentType=json",
                BASE_URL, city, API_KEY);
        return restTemplate.getForObject(url, WeatherResponse.class);
    }
}