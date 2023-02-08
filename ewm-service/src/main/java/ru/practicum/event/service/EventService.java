package ru.practicum.event.service;


import ru.practicum.event.dto.EventDtoRequest;
import ru.practicum.event.dto.EventDtoResponse;
import ru.practicum.event.dto.EventDtoShortResponse;
import ru.practicum.event.dto.UpdateEventUserRequest;
import ru.practicum.requests.dto.EventRequestDto;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventDtoResponse createEventPrivate(Long userId, EventDtoRequest eventDto);

    EventDtoResponse updateEventPrivate(Long userId, Long eventId, UpdateEventUserRequest eventDto);

    EventDtoResponse getEventPublic(Long id, HttpServletRequest request);

    void removeEventPublic(Long id);

    List<EventDtoShortResponse> getAllEventsPublic(String text, List<String> categories, Boolean paid, Boolean onlyAvailable, LocalDateTime start, LocalDateTime end, String sort, Integer from, Integer size, HttpServletRequest request);

    List<EventDtoResponse> getAllEventsAdmin(List<Long> users, List<String> states, List<Long> categories, LocalDateTime start, LocalDateTime end, Integer from, Integer size);

    List<EventDtoShortResponse> getAllEventsPrivate(Long userId, Integer from, Integer size);

    EventDtoResponse getEventPrivate(Long userId, Long eventId);

    List<EventRequestDto> getEventRequestsPrivate(Long userId, Long eventId);

    EventDtoResponse updateEventAdmin(Long eventId, UpdateEventUserRequest eventDtoRequest);
}
