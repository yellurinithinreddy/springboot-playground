package com.nithin.CachingWork.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

    @Value("${weather.api.url}")
    private String weatherApiUrl;

    @Bean
    public RestClient restClient(){
        return RestClient.builder()
                .baseUrl(weatherApiUrl)
                .build();
    }
}
