package com.api.corporationX.weather.app.services;

import com.api.corporationX.weather.app.models.WeatherResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${weather.api.key}")
    private String API_KEY;

    @Value("${weather.api.base-url}")
    private String BASE_URL;

    public WeatherResponse getWeather(String city) {
        String url = String.format("%s%s?unitGroup=metric&key=%s&contentType=json",
                BASE_URL, city, API_KEY);
        return restTemplate.getForObject(url, WeatherResponse.class);
    }
}
