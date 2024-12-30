//package com.rjAbhi.journalApp.service;
//
//import com.rjAbhi.journalApp.api.response.WeatherApiResponse;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.web.client.RestTemplate;
//
//@Service
//public class WeatherService {
//
//    private static final String apiKey="5c2b66013088cdf4be36dcf553ca2bae";
//
//    private static final String API="https://api.openweathermap.org/data/2.5/weather?lat={LAT}&lon={LONG}&appid={API_KEY}";
//
//    @Autowired
//    private RestTemplate restTemplate;
//
//    public WeatherApiResponse getWeather(String latitude, String longitude)
//    {
//        String finalAPI= API
//                .replace("LAT",latitude)
//                .replace("LONG",longitude)
//                .replace("API_KEY",apiKey);
//
//        ResponseEntity<WeatherApiResponse> weatherResponse=restTemplate.exchange(
//                finalAPI,
//                HttpMethod.GET,
//                null,
//                WeatherApiResponse.class);
//        WeatherApiResponse responseBody= weatherResponse.getBody();
//        return responseBody;
//
//
//    }
//
//
//}

package com.rjAbhi.journalApp.service;

import com.rjAbhi.journalApp.api.response.WeatherApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {

    // It's recommended to use an environment variable or configuration file for sensitive keys
    private static final String API_KEY ="5c2b66013088cdf4be36dcf553ca2bae";
    private static final String API_TEMPLATE = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={apiKey}";

    @Autowired
    private RestTemplate restTemplate;

    public WeatherApiResponse getWeather(String latitude, String longitude) {
        try {
            // Replace placeholders dynamically
            ResponseEntity<WeatherApiResponse> weatherResponse = restTemplate.exchange(
                    API_TEMPLATE,
                    HttpMethod.GET,
                    null,
                    WeatherApiResponse.class,
                    latitude, longitude, API_KEY
            );

            return weatherResponse.getBody();
        } catch (Exception e) {
            // Log the error or throw a custom exception
            throw new RuntimeException("Failed to fetch weather data: " + e.getMessage(), e);
        }
    }
}

