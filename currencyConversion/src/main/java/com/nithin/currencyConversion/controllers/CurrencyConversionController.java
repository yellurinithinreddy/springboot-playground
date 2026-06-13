package com.nithin.currencyConversion.controllers;

import com.nithin.currencyConversion.clients.CurrencyClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/convertCurrency")
public class CurrencyConversionController {

    private final CurrencyClient currencyClient;

    @GetMapping
    public Double getConvertedCurrencyUnits(@RequestParam(defaultValue = "INR") String fromCurrency,@RequestParam(defaultValue = "USD") String toCurrency,@RequestParam(defaultValue = "1") Double units){
        return currencyClient.getConvertedCurrencyUnits(fromCurrency,toCurrency,units);
    }
}
