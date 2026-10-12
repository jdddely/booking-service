package com.petprojects.booking_service.repository;

import com.petprojects.booking_service.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
}
