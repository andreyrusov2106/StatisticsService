DROP table IF EXISTS PUBLIC.events cascade;
DROP table IF EXISTS PUBLIC.categories cascade;
DROP table IF EXISTS PUBLIC.users cascade;
DROP table IF EXISTS PUBLIC.locations cascade;
DROP table IF EXISTS PUBLIC.compilations cascade;
DROP table IF EXISTS PUBLIC.event_requests cascade;
DROP table IF EXISTS PUBLIC.compilations_events cascade;

CREATE TABLE IF NOT EXISTS PUBLIC.users (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(500) UNIQUE,
    email varchar(500)
    );

CREATE TABLE IF NOT EXISTS PUBLIC.categories (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(2000) UNIQUE
);
CREATE TABLE IF NOT EXISTS PUBLIC.locations (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    lat float,
    lon float
    );
CREATE TABLE IF NOT EXISTS PUBLIC.events (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    category_id BIGINT,
    annotation varchar(64000),
    description varchar(64000),
    confirmed_requests BIGINT,
    initiator_id BIGINT,
    paid boolean,
    request_moderation boolean,
    title varchar(500),
    views BIGINT,
    location_id BIGINT,
    participant_limit BIGINT,
    state varchar(30),
    event_date TIMESTAMP WITHOUT TIME ZONE,
    published_on TIMESTAMP WITHOUT TIME ZONE,
    created_on TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT fk_events_to_users FOREIGN KEY(initiator_id) REFERENCES users(id),
    CONSTRAINT fk_events_to_categories FOREIGN KEY(category_id) REFERENCES categories(id),
    CONSTRAINT fk_events_to_locations FOREIGN KEY(location_id) REFERENCES locations(id)
    );

CREATE TABLE IF NOT EXISTS PUBLIC.compilations (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    pinned boolean,
    title varchar(3000)
);

CREATE TABLE IF NOT EXISTS compilations_events(
    compilation_id BIGINT not null,
    events_id BIGINT not null,
    CONSTRAINT fk_compilation_events_to_compilation FOREIGN KEY(compilation_id) REFERENCES compilations(id),
    CONSTRAINT fk_compilation_events_to_events FOREIGN KEY(events_id) REFERENCES events(id)
    );

CREATE TABLE IF NOT EXISTS PUBLIC.event_requests (
    id BIGINT GENERATED ALWAYS AS IDENTITY,
    event_id BIGINT,
    requester_id BIGINT,
    created TIMESTAMP WITHOUT TIME ZONE,
    state varchar(30),
    CONSTRAINT fk_event_requests_to_users FOREIGN KEY(requester_id) REFERENCES users(id),
    CONSTRAINT fk_event_requests_to_events FOREIGN KEY(event_id) REFERENCES events(id),
    constraint event_request_pk primary key (event_id, requester_id)
    );





