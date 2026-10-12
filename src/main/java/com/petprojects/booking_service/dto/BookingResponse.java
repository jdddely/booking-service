package com.petprojects.booking_service.dto;

import java.time.Instant;
import java.util.UUID;

public record BookingResponse(
        UUID roomId,
        UUID userId,
        UUID eventId,
        String eventStatus,
        String roomName,
        String userName,
        Instant startTime,
        Instant endTime) {
}
