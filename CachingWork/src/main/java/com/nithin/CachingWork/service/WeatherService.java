package com.nithin.CachingWork.service;

import com.nithin.CachingWork.dto.WeatherResult;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final RestClient restClient;


    @Cacheable(cacheNames = "weather",key = "#location")
    public WeatherResult getWeather(String location, String weatherApiKey) {

        Map<String,Object> result = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/current.json")
                        .queryParam("key",weatherApiKey)
                        .queryParam("q",location)
                        .build()
                )
                .retrieve()
                .body(new ParameterizedTypeReference<>() {
                });
        System.out.println(result);
        System.out.println(result.get("current"));
        Map<String,Object> current = (Map<String, Object>) result.get("current");


        Double celsius = (Double) current.get("temp_c");
        Double fahrenheit = (Double) current.get("temp_f");

        return new WeatherResult(celsius,fahrenheit);
    }
}
