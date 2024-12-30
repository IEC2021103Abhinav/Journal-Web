package com.rjAbhi.journalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

// JSON to POJO --> Deserialization
// POJO to JSON --> Serialization

@Getter
@Setter
public class WeatherApiResponse {
    private List<Weather> weather;
    private Sys sys;
    private Main main;

    @Getter
    @Setter
    public static class Main { // Made static
        @JsonProperty("feels_like")
        private double feelsLike;
    }

    @Getter
    @Setter
    public static class Sys { // Made static
        private String country;
    }

    @Getter
    @Setter
    public static class Weather { // Made static
        private String description;
    }
}
