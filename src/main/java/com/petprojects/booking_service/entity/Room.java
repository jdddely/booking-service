package com.petprojects.booking_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.Objects;
import java.util.UUID;

@Entity
public class Room {

    @Id
    UUID roomId;

    String roomName;

    int roomCapacity;

    private Room(UUID roomId, String roomName, int roomCapacity) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomCapacity = roomCapacity;
    }

    protected Room() {
    }

    public static Room create(String roomName, int roomCapacity) {
        UUID id = UUID.randomUUID();
        return new Room(id, roomName, roomCapacity);
    }

    public UUID getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public int getRoomCapacity() {
        return roomCapacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Room room = (Room) o;
        return Objects.equals(roomId, room.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(roomId);
    }
}

