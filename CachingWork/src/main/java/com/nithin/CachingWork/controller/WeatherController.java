package com.nithin.CachingWork.controller;

import com.nithin.CachingWork.dto.WeatherResult;
import com.nithin.CachingWork.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/weather")
public class WeatherController {

    @Value("${weather.api.key}")
    private String weatherApiKey;

    private final WeatherService weatherService;


    @GetMapping
    public ResponseEntity<WeatherResult> getWeather(@RequestParam String location){
        return ResponseEntity.ok(weatherService.getWeather(location,weatherApiKey));
    }

}
