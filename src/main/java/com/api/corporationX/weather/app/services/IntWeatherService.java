package com.api.corporationX.weather.app.services;

import com.api.corporationX.weather.app.models.WeatherResponse;

/**
 * Service interface defining the contract for fetching weather data.
 *
 * <p>Declaring this interface decouples the controller layer from the concrete
 * service implementation, making it easier to swap implementations (e.g., a different
 * weather provider) or inject mocks during testing without modifying the controller.</p>
 *
 * <p>The current implementation is {@link WeatherService}, which communicates
 * with the Visual Crossing Weather API.</p>
 */
public interface IntWeatherService {

    /**
     * Fetches current weather data for the specified city.
     *
     * @param city the name of the city to query (e.g., {@code "London"})
     * @return a {@link WeatherResponse} containing the city's current weather data
     */
    WeatherResponse getWeather(String city);
}