package ru.practicum.event.mapper;


import ru.practicum.event.dto.EventDtoRequest;
import ru.practicum.event.dto.EventDtoResponse;
import ru.practicum.event.dto.EventDtoShortResponse;
import ru.practicum.event.dto.UpdateEventUserRequest;
import ru.practicum.event.model.Event;

public class EventMapper {
    public static EventDtoResponse toEventDto(Event event) {
        return EventDtoResponse.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .title(event.getTitle())
                .eventDate(event.getEventDate())
                .paid(event.getPaid())
                .views(event.getViews())
                .confirmedRequests(event.getConfirmedRequests())
                .initiator(event.getInitiator())
                .category(event.getCategory())
                .description(event.getDescription())
                .location(event.getLocation())
                .requestModeration(event.getRequestModeration())
                .participantLimit(event.getParticipantLimit())
                .state(event.getState())
                .createdOn(event.getCreatedOn())
                .publishedOn(event.getPublishedOn())
                .comments(event.getComments())
                .build();
    }

    public static EventDtoShortResponse toEventShortDto(Event event) {
        return EventDtoShortResponse.builder()
                .id(event.getId())
                .annotation(event.getAnnotation())
                .title(event.getTitle())
                .eventDate(event.getEventDate())
                .paid(event.getPaid())
                .views(event.getViews())
                .confirmedRequests(event.getConfirmedRequests())
                .initiator(event.getInitiator())
                .category(event.getCategory())
                .build();
    }

    public static Event toEvent(Event event, EventDtoRequest eventDto) {
        if (eventDto.getId() != null) event.setId(eventDto.getId());
        if (eventDto.getAnnotation() != null) event.setAnnotation(eventDto.getAnnotation());
        if (eventDto.getTitle() != null) event.setTitle(eventDto.getTitle());
        if (eventDto.getEventDate() != null) event.setEventDate(eventDto.getEventDate());
        if (eventDto.getPaid() != null) event.setPaid(eventDto.getPaid());
        if (eventDto.getDescription() != null) event.setDescription(eventDto.getDescription());
        if (eventDto.getLocation() != null) event.setLocation(eventDto.getLocation());
        if (eventDto.getRequestModeration() != null) event.setRequestModeration(eventDto.getRequestModeration());
        if (eventDto.getParticipantLimit() != null) event.setParticipantLimit(eventDto.getParticipantLimit());
        return event;

    }

    public static Event toEvent(Event event, UpdateEventUserRequest eventDto) {
        if (eventDto.getId() != null) event.setId(eventDto.getId());
        if (eventDto.getAnnotation() != null) event.setAnnotation(eventDto.getAnnotation());
        if (eventDto.getTitle() != null) event.setTitle(eventDto.getTitle());
        if (eventDto.getEventDate() != null) event.setEventDate(eventDto.getEventDate());
        if (eventDto.getPaid() != null) event.setPaid(eventDto.getPaid());
        if (eventDto.getDescription() != null) event.setDescription(eventDto.getDescription());
        if (eventDto.getLocation() != null) event.setLocation(eventDto.getLocation());
        if (eventDto.getRequestModeration() != null) event.setRequestModeration(eventDto.getRequestModeration());
        if (eventDto.getParticipantLimit() != null) event.setParticipantLimit(eventDto.getParticipantLimit());
        return event;
    }
}
