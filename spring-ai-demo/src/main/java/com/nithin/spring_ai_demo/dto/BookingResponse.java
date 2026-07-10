package com.nithin.spring_ai_demo.dto;

import com.nithin.spring_ai_demo.entities.BookingStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

public record BookingResponse(
        Long id,
        String destination,

        Instant departureTime,

        BookingStatus bookingStatus,

        Instant bookedAt
) {
}
