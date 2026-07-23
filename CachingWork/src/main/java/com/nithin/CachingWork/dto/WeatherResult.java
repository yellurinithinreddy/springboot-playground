package com.nithin.CachingWork.dto;

import java.io.Serializable;

public record WeatherResult (
        double celsius,
        double Fahrenheit
) implements Serializable {
}
