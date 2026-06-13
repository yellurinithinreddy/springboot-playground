package com.nithin.currencyConversion.clients;

import com.nithin.currencyConversion.dto.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class CurrencyClient {

    private final RestClient restClient;

    @Value("${Currency.api.key}")
    private String API_KEY;

    public Double getConvertedCurrencyUnits(String fromCurrency,String toCurrency,Double units){
        ApiResponse apiResponse = restClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("v1/latest")
                        .queryParam("apikey",API_KEY)
                        .queryParam("base_currency",fromCurrency)
                        .queryParam("currencies",toCurrency)
                        .build())
                .retrieve()
                .body(ApiResponse.class);

        return (Double) apiResponse.getData().get(toCurrency)*units;

    }
}
