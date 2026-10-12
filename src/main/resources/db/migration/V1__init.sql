CREATE TABLE room
(
    room_id       UUID,
    room_name     VARCHAR(255) NOT NULL,
    room_capacity INT NOT NULL,

    CONSTRAINT room_pkey PRIMARY KEY (room_id),
    CONSTRAINT room_name_key UNIQUE (room_name),
    CONSTRAINT room_capacity_check CHECK ( room_capacity > 0 )
);

CREATE TABLE users (
    user_id UUID,
    user_name VARCHAR(255) NOT NULL,
    user_email VARCHAR(255) NOT NULL,

    CONSTRAINT users_pkey PRIMARY KEY (user_id),
    CONSTRAINT users_email_key UNIQUE (user_email)
);

CREATE TABLE event (
    event_id UUID,
    room_id UUID NOT NULL,
    user_id UUID NOT NULL,
    event_status VARCHAR(255) NOT NULL,
    start_time TIMESTAMP WITH TIME ZONE NOT NULL,
    end_time TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT event_pkey PRIMARY KEY (event_id),
    CONSTRAINT event_room_fkey FOREIGN KEY (room_id) REFERENCES room(room_id),
    CONSTRAINT event_user_fkey FOREIGN KEY (user_id) REFERENCES users(user_id),
    CONSTRAINT event_time_check CHECK ( start_time < end_time )
);