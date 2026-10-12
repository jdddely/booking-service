package com.petprojects.booking_service.dto;

import java.time.Instant;
import java.util.UUID;

public record BookingRequest(UUID roomId, UUID userId, Instant startTime, Instant endTime) {
}
