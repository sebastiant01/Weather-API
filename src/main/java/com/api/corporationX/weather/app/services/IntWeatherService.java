package com.api.corporationX.weather.app.services;

import com.api.corporationX.weather.app.models.WeatherResponse;

public interface IntWeatherService {

    public WeatherResponse getWeather(String city);
}
