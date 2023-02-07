package ru.practicum.requests.service;


import ru.practicum.requests.dto.*;

import java.util.List;

public interface EventRequestService {
    EventRequestDto createEventRequest(Long userId, Long eventId);

    EventRequestDto updateCategory(Long userId, Long request);

    EventRequestListDto updateEventRequestStatus(Long userId, Long eventId, EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest);

    List<EventRequestDto> getAllEventRequest(Long userId);


}
