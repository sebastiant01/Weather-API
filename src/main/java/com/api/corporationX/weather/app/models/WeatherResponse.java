package com.api.corporationX.weather.app.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {
    @JsonProperty("resolvedAddress")
    private String countryAddress;

    @JsonProperty("timezone")
    private String countryTimezone;

    private double temperature;
    private String countryCondition;
    private String currentTime;

    @JsonProperty("currentConditions")
    private void unpackWeatherDict(Map<String, Object> currentConditions) {
        temperature = (double) currentConditions.get("temp");
        countryCondition = (String) currentConditions.get("conditions");
        currentTime = (String) currentConditions.get("datetime");
    }

    public String getCountryAddress() {
        return countryAddress;
    }

    public void setCountryAddress(String countryAddress) {
        this.countryAddress = countryAddress;
    }

    public String getCountryTimezone() {
        return countryTimezone;
    }

    public void setCountryTimezone(String countryTimezone) {
        this.countryTimezone = countryTimezone;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public String getCountryCondition() {
        return countryCondition;
    }

    public void setCountryCondition(String countryCondition) {
        this.countryCondition = countryCondition;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
