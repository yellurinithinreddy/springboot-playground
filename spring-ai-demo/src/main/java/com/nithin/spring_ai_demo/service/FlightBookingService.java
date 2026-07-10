package com.nithin.spring_ai_demo.service;

import com.nithin.spring_ai_demo.dto.BookingResponse;
import com.nithin.spring_ai_demo.dto.BookingsListResponse;
import com.nithin.spring_ai_demo.entities.BookingStatus;
import com.nithin.spring_ai_demo.entities.FlightBooking;
import com.nithin.spring_ai_demo.respository.FlightBookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FlightBookingService {

    private final FlightBookingRepository flightBookingRepository;

    public BookingResponse createBooking(String userId, String destination, Instant departureTime){
        boolean exists = flightBookingRepository.existsByUserIdAndDestinationAndDepartureTime(userId,destination,departureTime);

        if(exists){
            throw new IllegalArgumentException(
                    "you already have booking to "+destination+" on that date"
            );
        }

        FlightBooking flightBooking =flightBookingRepository.save(FlightBooking.builder()
                .userId(userId)
                .destination(destination)
                .departureTime(departureTime)
                .bookingStatus(BookingStatus.CONFIRMED)
                .build());

        return new BookingResponse(flightBooking.getId(), destination, departureTime,BookingStatus.CONFIRMED, flightBooking.getBookedAt());
    }

    public BookingsListResponse getUserBookings(String userId){
        List<BookingResponse> bookings = flightBookingRepository.findByUserIdOrderByDepartureTimeDesc(userId)
                .stream()
                .map((b) -> new BookingResponse(b.getId(),b.getDestination(),b.getDepartureTime(),b.getBookingStatus(),b.getBookedAt()))
                .toList();

        return new BookingsListResponse(bookings);
    }

    public BookingResponse updateStatusOfBooking(Long bookingId, String userId, BookingStatus bookingStatus){
        FlightBooking flightBooking = flightBookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found"));

        if(!userId.equals(flightBooking.getUserId())){
            throw new IllegalArgumentException("you can only modify you own bookings");
        }

        flightBooking.setBookingStatus(bookingStatus);
        flightBooking = flightBookingRepository.save(flightBooking);
        return new BookingResponse(flightBooking.getId(), flightBooking.getDestination(), flightBooking.getDepartureTime(),flightBooking.getBookingStatus(), flightBooking.getBookedAt());
    }
}
