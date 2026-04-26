package com.api.corporationX.weather.app.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

/**
 * Model representing the weather data returned by the Visual Crossing Weather API.
 *
 * <p>Jackson deserializes the API's JSON response directly into this class.
 * Unknown fields from the response are silently ignored via {@link JsonIgnoreProperties}.
 * The nested {@code currentConditions} object is unpacked into flat fields through
 * a dedicated private method annotated with {@link JsonProperty}.</p>
 *
 * <p>Example API response mapped by this class:</p>
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
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    /** The full resolved address of the queried city, mapped from {@code resolvedAddress}. */
    @JsonProperty("resolvedAddress")
    private String countryAddress;

    /** The timezone of the queried city, mapped from {@code timezone}. */
    @JsonProperty("timezone")
    private String countryTimezone;

    /** The current temperature in Celsius, extracted from {@code currentConditions.temp}. */
    private double temperature;

    /** A short description of the current weather, extracted from {@code currentConditions.conditions}. */
    private String countryCondition;

    /** The local time of the current conditions, extracted from {@code currentConditions.datetime}. */
    private String currentTime;

    /**
     * Unpacks the nested {@code currentConditions} JSON object into flat fields.
     *
     * <p>Jackson calls this method during deserialization when it encounters the
     * {@code currentConditions} key, passing its contents as a {@link Map}.
     * The relevant fields are then extracted and assigned individually.</p>
     *
     * @param currentConditions a map containing the fields inside {@code currentConditions}
     *                          ({@code temp}, {@code conditions}, {@code datetime})
     */
    @JsonProperty("currentConditions")
    private void unpackWeatherDict(Map<String, Object> currentConditions) {
        temperature = (double) currentConditions.get("temp");
        countryCondition = (String) currentConditions.get("conditions");
        currentTime = (String) currentConditions.get("datetime");
    }

    /**
     * Returns the full resolved address of the queried city.
     *
     * @return the resolved address (e.g., {@code "London, England, United Kingdom"})
     */
    public String getCountryAddress() {
        return countryAddress;
    }

    /**
     * Sets the resolved address of the queried city.
     *
     * @param countryAddress the resolved address to set
     */
    public void setCountryAddress(String countryAddress) {
        this.countryAddress = countryAddress;
    }

    /**
     * Returns the timezone identifier of the queried city.
     *
     * @return the timezone (e.g., {@code "Europe/London"})
     */
    public String getCountryTimezone() {
        return countryTimezone;
    }

    /**
     * Sets the timezone of the queried city.
     *
     * @param countryTimezone the timezone identifier to set
     */
    public void setCountryTimezone(String countryTimezone) {
        this.countryTimezone = countryTimezone;
    }

    /**
     * Returns the current temperature in Celsius.
     *
     * @return the temperature in °C
     */
    public double getTemperature() {
        return temperature;
    }

    /**
     * Sets the current temperature.
     *
     * @param temperature the temperature in °C to set
     */
    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    /**
     * Returns a short description of the current weather conditions.
     *
     * @return the weather condition (e.g., {@code "Partially cloudy"})
     */
    public String getCountryCondition() {
        return countryCondition;
    }

    /**
     * Sets the current weather condition description.
     *
     * @param countryCondition the condition string to set
     */
    public void setCountryCondition(String countryCondition) {
        this.countryCondition = countryCondition;
    }

    /**
     * Returns the local time associated with the current conditions.
     *
     * @return the datetime string (e.g., {@code "13:00:00"})
     */
    public String getCurrentTime() {
        return currentTime;
    }

    /**
     * Sets the local time of the current conditions.
     *
     * @param currentTime the datetime string to set
     */
    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}