package com.api.corporationX.weather.app.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * Model representing the weather data returned by the Visual Crossing Weather API.
 *
 * <p>Jackson deserializes the API's JSON response directly into this class.
 * Unknown fields from the response are silently ignored via {@link JsonIgnoreProperties}.
 * Fields with {@code null} values are excluded from the serialized JSON output
 * via {@link JsonInclude}, which enables the simple/full response modes.</p>
 *
 * <p>Two nested objects from the API response are unpacked into flat fields:</p>
 * <ul>
 *   <li>{@code currentConditions} — provides {@code temperature}, {@code countryCondition},
 *       and {@code currentTime}</li>
 *   <li>{@code days[0]} — provides {@code date}, {@code humidity}, and {@code precipitation}
 *       for the current day</li>
 * </ul>
 *
 * <p>Example full API response mapped by this class:</p>
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
 * @see #convertToSimple()
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherResponse {

    /** The full resolved address of the queried city, mapped from {@code resolvedAddress}. */
    @JsonProperty("resolvedAddress")
    private String cityLocation;

    /** The timezone of the queried city, mapped from {@code timezone}. */
    @JsonProperty("timezone")
    private String cityContinent;

    /** The current date, extracted from {@code days[0].datetime}. */
    private String date;

    /** The local time of the current conditions, extracted from {@code currentConditions.datetime}. */
    private String currentTime;

    /** The current temperature in Celsius, extracted from {@code currentConditions.temp}. */
    private Double temperature;

    /** The relative humidity percentage for the current day, extracted from {@code days[0].humidity}. */
    private Double humidity;

    /** The precipitation amount for the current day, extracted from {@code days[0].precip}. */
    private Double precipitation;

    /** A short description of the current weather, extracted from {@code currentConditions.conditions}. */
    private String countryCondition;

    /** A human-readable summary of the day's weather, mapped from {@code description}. */
    @JsonProperty("description")
    private String descriptionOfDay;

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
        this.temperature = (Double) currentConditions.get("temp");
        this.countryCondition = (String) currentConditions.get("conditions");
        this.currentTime = (String) currentConditions.get("datetime");
    }

    /**
     * Unpacks the first element of the {@code days} JSON array into flat fields.
     *
     * <p>Jackson calls this method during deserialization when it encounters the
     * {@code days} key. Only the first entry ({@code days[0]}) is used, as it
     * represents today's forecast data.</p>
     *
     * @param days a list of maps where each entry represents a day's forecast;
     *             only the first element is processed ({@code datetime}, {@code humidity},
     *             {@code precip})
     */
    @JsonProperty("days")
    private void unpackDaysDict(List<Map<String, Object>> days) {
        Map<String, Object> today = days.getFirst();

        this.date = (String) today.get("datetime");
        this.humidity = (Double) today.get("humidity");
        this.precipitation = (Double) today.get("precip");
    }

    /**
     * Strips extended fields from this response, leaving only the core weather data.
     *
     * <p>Sets {@code humidity}, {@code precipitation}, and {@code descriptionOfDay} to
     * {@code null}. Since the class is annotated with
     * {@link JsonInclude}{@code (NON_NULL)}, these fields will be omitted from the
     * JSON output, producing a lighter "simple" response suitable for the
     * {@code ?detail=simple} query parameter.</p>
     *
     * <p><b>Simple response fields:</b> {@code cityLocation}, {@code cityContinent},
     * {@code date}, {@code currentTime}, {@code temperature}, {@code countryCondition}.</p>
     */
    public void convertToSimple() {
        this.humidity = null;
        this.precipitation = null;
        this.descriptionOfDay = null;
    }

    /**
     * Returns the full resolved address of the queried city.
     *
     * @return the resolved address (e.g., {@code "London, England, United Kingdom"})
     */
    public String getCityLocation() {
        return cityLocation;
    }

    /**
     * Sets the resolved address of the queried city.
     *
     * @param cityLocation the resolved address to set
     */
    public void setCityLocation(String cityLocation) {
        this.cityLocation = cityLocation;
    }

    /**
     * Returns the timezone identifier of the queried city.
     *
     * @return the timezone (e.g., {@code "Europe/London"})
     */
    public String getCityContinent() {
        return cityContinent;
    }

    /**
     * Sets the timezone of the queried city.
     *
     * @param cityContinent the timezone identifier to set
     */
    public void setCityContinent(String cityContinent) {
        this.cityContinent = cityContinent;
    }

    /**
     * Returns the current date extracted from today's forecast entry.
     *
     * @return the date string (e.g., {@code "2025-04-25"})
     */
    public String getDate() {
        return date;
    }

    /**
     * Sets the current date.
     *
     * @param date the date string to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Returns the local time associated with the current conditions.
     *
     * @return the time string (e.g., {@code "13:00:00"})
     */
    public String getCurrentTime() {
        return currentTime;
    }

    /**
     * Sets the local time of the current conditions.
     *
     * @param currentTime the time string to set
     */
    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    /**
     * Returns the current temperature in Celsius.
     *
     * @return the temperature in °C, or {@code null} if not available
     */
    public Double getTemperature() {
        return temperature;
    }

    /**
     * Sets the current temperature.
     *
     * @param temperature the temperature in °C to set
     */
    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    /**
     * Returns the relative humidity percentage for the current day.
     *
     * @return the humidity as a percentage (e.g., {@code 72.0}),
     *         or {@code null} in simple response mode
     */
    public Double getHumidity() {
        return humidity;
    }

    /**
     * Sets the relative humidity percentage.
     *
     * @param humidity the humidity value to set
     */
    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    /**
     * Returns the precipitation amount for the current day.
     *
     * @return the precipitation value, or {@code null} in simple response mode
     */
    public Double getPrecipitation() {
        return precipitation;
    }

    /**
     * Sets the precipitation amount.
     *
     * @param precipitation the precipitation value to set
     */
    public void setPrecipitation(Double precipitation) {
        this.precipitation = precipitation;
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
     * Returns a human-readable summary of the day's overall weather.
     *
     * @return the day description, or {@code null} in simple response mode
     */
    public String getDescriptionOfDay() {
        return descriptionOfDay;
    }

    /**
     * Sets the day's weather description.
     *
     * @param descriptionOfDay the description to set
     */
    public void setDescriptionOfDay(String descriptionOfDay) {
        this.descriptionOfDay = descriptionOfDay;
    }
}