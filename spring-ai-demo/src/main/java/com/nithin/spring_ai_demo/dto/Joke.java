package com.nithin.spring_ai_demo.dto;

public record Joke(
        String text,
        Double laughPercentage,
        Integer laughScore,
        boolean isNSFW
) {
}
