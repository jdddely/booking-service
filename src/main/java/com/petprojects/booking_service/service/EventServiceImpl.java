package com.petprojects.booking_service.service;

import com.petprojects.booking_service.dto.BookingRequest;
import com.petprojects.booking_service.dto.BookingResponse;
import com.petprojects.booking_service.entity.Event;
import com.petprojects.booking_service.entity.Room;
import com.petprojects.booking_service.entity.User;
import com.petprojects.booking_service.mapper.EventMapper;
import com.petprojects.booking_service.repository.EventRepository;
import com.petprojects.booking_service.repository.RoomRepository;
import com.petprojects.booking_service.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final EventMapper mapper;

    public EventServiceImpl(
            EventRepository eventRepository,
            RoomRepository roomRepository,
            UserRepository userRepository,
            EventMapper mapper) {
        this.eventRepository = eventRepository;
        this.roomRepository = roomRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @Transactional
    @Override
    public BookingResponse createBooking(BookingRequest bookingRequest) {
        Room room = roomRepository.findById(bookingRequest.roomId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Room with id = " + bookingRequest.roomId() + " - not found"));
        User user = userRepository.findById(bookingRequest.userId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User with id = " + bookingRequest.userId() + " - not found"));
        Event event = mapper.toEntity(room, user, bookingRequest.startTime(), bookingRequest.endTime());
        eventRepository.save(event);
        return mapper.toResponse(event);
    }

    @Transactional
    @Override
    public BookingResponse cancelBooking(UUID eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Entity with id = " + eventId + " - not found"));
        try {
            event.cancel();
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
        return mapper.toResponse(event);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BookingResponse> getAllBookings() {
        return eventRepository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }
}
