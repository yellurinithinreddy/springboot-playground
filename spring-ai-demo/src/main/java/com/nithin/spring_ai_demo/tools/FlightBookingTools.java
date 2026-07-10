package com.nithin.spring_ai_demo.tools;


import com.nithin.spring_ai_demo.dto.BookingResponse;
import com.nithin.spring_ai_demo.dto.BookingsListResponse;
import com.nithin.spring_ai_demo.entities.BookingStatus;
import com.nithin.spring_ai_demo.entities.FlightBooking;
import com.nithin.spring_ai_demo.service.FlightBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FlightBookingTools {

    private final FlightBookingService flightBookingService;


    @Tool(name = "flight_booking_tool",description = "Create a new flight booking for a user")
    public BookingResponse bookFlightTicket(
            @ToolParam(description = "The unique user id (e.g. userId is user123)")
            String userId,
            @ToolParam(description = "The destination for the flight booking (e.g. city like Delhi, London, etc.)")
            String destination,
            @ToolParam(description = "Departure date and time in ISO-8601 format (e.g., 2025-12-25T14:30:00Z)")
            Instant departureTime){

        return flightBookingService.createBooking(userId,destination,departureTime);

    }

    @Tool(name = "get_user_bookings",description =  "Retrieve all flight bookings for the current user, sorted by departure time (most recent first). " +
            "Returns an empty list message if none exist.")
    public BookingsListResponse getAllBookings(
            @ToolParam(description = "The unique user ID")
            String userId){

        return flightBookingService.getUserBookings(userId);

    }

    @Tool(name = "update_booking_status",description = "Update the status of an existing flight booking (e.g., cancel it). " +
            "Only the owner of the booking can modify it. " +
            "Common use: set status to CANCELLED.")
    public BookingResponse updateBookingStatus(
            @ToolParam(description = "The user ID who owns the booking")
            String userId,
            @ToolParam(description = "The booking ID returned from create or get bookings")
            Long bookingId,
            @ToolParam(description = "New status: CONFIRMED, CANCELLED, or PENDING")
            BookingStatus newStatus
            ){

        return flightBookingService.updateStatusOfBooking(bookingId,userId,newStatus);

    }




}
