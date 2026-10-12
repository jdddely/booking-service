package com.petprojects.booking_service.mapper;

import com.petprojects.booking_service.dto.BookingResponse;
import com.petprojects.booking_service.entity.Event;
import com.petprojects.booking_service.entity.Room;
import com.petprojects.booking_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.Instant;

@Mapper(componentModel = "spring")
public interface EventMapper {

    @Mapping(source = "room.roomId", target = "roomId")
    @Mapping(source = "user.userId", target = "userId")
    @Mapping(source = "room.roomName", target = "roomName")
    @Mapping(source = "user.userName", target = "userName")
    BookingResponse toResponse(Event event);

    default Event toEntity(
            Room room,
            User user,
            Instant startTime,
            Instant endTime) {
        return Event.create(room, user, startTime, endTime);
    }
}
