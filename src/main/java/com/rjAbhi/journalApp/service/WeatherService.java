package com.rjAbhi.journalApp.service;
import com.rjAbhi.journalApp.api.response.WeatherApiResponse;
import com.rjAbhi.journalApp.cache.AppCache;
import com.rjAbhi.journalApp.constants.PlaceHolders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
@Service
public class WeatherService {
    // It's recommended to use an environment variable or configuration file for sensitive keys
    @Value("${weather.api.key}")
    private String apiKey;

    private static final String API_TEMPLATE = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={apiKey}";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WeatherApiResponse getWeather(String latitude, String longitude) {
        try {
            // Replace placeholders dynamically
            String finalAPI= appCache.appCacheMap.get(AppCache.keys.WEATHER_API.toString())
                    .replace(PlaceHolders.LATITUDE,latitude)
                    .replace(PlaceHolders.LONGITUDE,longitude)
                    .replace(PlaceHolders.API_KEY,apiKey);
            ResponseEntity<WeatherApiResponse> weatherResponse = restTemplate.exchange(
                    finalAPI,
                    HttpMethod.POST,
                    null,
                    WeatherApiResponse.class
            );
            return weatherResponse.getBody();
        } catch (Exception e) {
            // Log the error or throw a custom exception
            throw new RuntimeException("Failed to fetch weather data: " + e.getMessage(), e);
        }
    }
}