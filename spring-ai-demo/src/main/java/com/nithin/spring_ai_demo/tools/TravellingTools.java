package com.nithin.spring_ai_demo.tools;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TravellingTools {


    @Tool(name = "weather_prediction_tool",description = "Use this Tool to find weather in the given city")
    public String getWeatherBasedOnCity(@ToolParam(description = "Based on this city only the weather can be found") String city){
        return switch(city){
            case "Delhi" -> "Weather in Delhi is sunny , and 26 degree Celsius";
            case "London" -> "Weather in London is cloudy, and 2 degree Celsius";
            default -> "Sorry I cant provide the weather for provided city";
        };
    }
}
