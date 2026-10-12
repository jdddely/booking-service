package com.petprojects.booking_service.service;

import com.petprojects.booking_service.dto.BookingRequest;
import com.petprojects.booking_service.dto.BookingResponse;

import java.util.List;
import java.util.UUID;

public interface EventService {

    BookingResponse createBooking(BookingRequest bookingRequest);
    BookingResponse cancelBooking(UUID eventId);
    List<BookingResponse> getAllBookings();
}
