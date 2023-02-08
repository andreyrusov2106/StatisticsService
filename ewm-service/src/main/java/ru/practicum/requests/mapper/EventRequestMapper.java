package ru.practicum.requests.mapper;


import ru.practicum.requests.dto.EventRequestDto;
import ru.practicum.requests.model.EventRequest;

public class EventRequestMapper {
    public static EventRequestDto toEventRequestDto(EventRequest eventRequest) {
        return EventRequestDto.builder()
                .id(eventRequest.getId())
                .event(eventRequest.getEvent().getId())
                .requester(eventRequest.getRequester().getId())
                .status(eventRequest.getState())
                .created(eventRequest.getCreated())
                .build();
    }
}
