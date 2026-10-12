package com.petprojects.booking_service.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Event {

    @Id
    UUID eventId;

    @JoinColumn(name = "room_id")
    @ManyToOne(fetch = FetchType.LAZY)
    Room room;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    User user;

    @Enumerated(EnumType.STRING)
    EventStatus eventStatus;

    Instant startTime;
    Instant endTime;

    private Event(UUID eventId, Room room, User user, EventStatus eventStatus, Instant startTime, Instant endTime) {
        this.eventId = eventId;
        this.room = room;
        this.user = user;
        this.eventStatus = eventStatus;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    protected Event() {
    }

    public static Event create(Room room, User user, Instant startTime, Instant endTime) {
        UUID id = UUID.randomUUID();
        return new Event(id, room, user, EventStatus.ACTIVE, startTime, endTime);
    }

    public UUID getEventId() {
        return eventId;
    }

    public Room getRoom() {
        return room;
    }

    public User getUser() {
        return user;
    }

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(eventId, event.eventId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(eventId);
    }

    /**
     * This is a "rich domain model" on purpose (not an "anemic" one).
     * <p>
     * There is no public setter for {@link #eventStatus}. This method is the
     * ONLY way to cancel a booking, and it checks the rule itself: you cannot
     * cancel a booking that is already cancelled.
     * <p>
     * Why here and not in the service layer? If the check lived in a service
     * method instead, every future caller (an admin feature, a scheduled job,
     * another developer) would have to remember to repeat it. Here, the rule
     * cannot be skipped — there is no other way to change the status.
     */
    public void cancel() {
        if (this.eventStatus == EventStatus.CANCELLED) {
            throw new IllegalStateException("Event " + eventId + " is already cancelled");
        }
        this.eventStatus = EventStatus.CANCELLED;
    }
}

