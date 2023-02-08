package ru.practicum.requests.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.event.model.State;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exceptions.ConflictException;
import ru.practicum.exceptions.ResourceNotFoundException;
import ru.practicum.requests.dto.*;
import ru.practicum.requests.mapper.EventRequestMapper;
import ru.practicum.requests.model.EventRequest;
import ru.practicum.requests.repository.EventRequestRepository;
import ru.practicum.user.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventRequestServiceImpl implements EventRequestService {
    private final EventRequestRepository repository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public EventRequestDto createEventRequest(Long userId, Long eventId) {
        EventRequest eventRequest = new EventRequest();
        eventRequest.setCreated(LocalDateTime.now().withNano(0));
        var requester = userRepository.findById(userId);
        if (requester.isEmpty()) {
            throw new ResourceNotFoundException(String.format("User with id=%d not found", userId));
        }

        var eventOptional = eventRepository.findById(eventId);
        if (eventOptional.isEmpty()) {
            throw new ResourceNotFoundException(String.format("event with id=%d not found", eventId));
        }
        var event = eventOptional.get();
        if (Objects.equals(event.getInitiator().getId(), userId)) {
            throw new ConflictException("Incorrect StateAction");
        }
        if (event.getState() != State.PUBLISHED) {
            throw new ConflictException("Incorrect StateAction");
        }
        if (event.getParticipantLimit() == 0) {
            throw new ConflictException("ParticipantLimit is 0");
        } else {
            if (!event.getRequestModeration()) {
                event.setParticipantLimit(event.getParticipantLimit() - 1);
                eventRepository.save(event);
            }
        }
        eventRequest.setRequester(requester.get());
        eventRequest.setEvent(event);
        eventRequest.setState(RequestStatus.PENDING);
        EventRequest createdCategory = repository.save(eventRequest);
        log.info("Category created" + createdCategory);
        return EventRequestMapper.toEventRequestDto(createdCategory);
    }

    @Override
    public EventRequestDto updateCategory(Long userId, Long requestId) {
        var requester = userRepository.findById(userId);
        if (requester.isEmpty()) {
            throw new ResourceNotFoundException(String.format("User with id=%d not found", userId));
        }
        var res = repository.findAEventRequestByIdIsAndRequesterIs(requestId, requester.get());
        res.setState(RequestStatus.CANCELED);
        var updated = repository.save(res);
        return EventRequestMapper.toEventRequestDto(updated);

    }

    @Override
    public EventRequestListDto updateEventRequestStatus(Long userId,
                                                        Long eventId,
                                                        EventRequestStatusUpdateRequest eventRequestStatusUpdateRequest) {
        EventRequestListDto eventRequestListDto = new EventRequestListDto();
        var requester = userRepository.findById(userId);
        if (requester.isEmpty()) {
            throw new ResourceNotFoundException(String.format("User with id=%d not found", userId));
        }

        var eventOptional = eventRepository.findByInitiatorAndId(requester.get(), eventId);
        if (eventOptional.isEmpty()) {
            throw new ResourceNotFoundException(String.format("event with id=%d not found", eventId));
        }
        var event = eventOptional.get();
        if (event.getParticipantLimit() == 0) {
            throw new ConflictException("ParticipantLimit is 0");
        } else {
            if (event.getRequestModeration()) {
                event.setParticipantLimit(event.getParticipantLimit() - (long) eventRequestStatusUpdateRequest.getRequestIds().size());
                eventRepository.save(event);
            }
        }
        var res = repository.findEventRequestsByIdInAndEventIs(eventRequestStatusUpdateRequest.getRequestIds(), event);
        if (res.size() == 0) {
            return eventRequestListDto;
        }
        res.forEach(eventRequest -> {
            eventRequest.setState(eventRequestStatusUpdateRequest.getStatus());
            repository.save(eventRequest);
        });

        if (eventRequestStatusUpdateRequest.getStatus() == RequestStatus.REJECTED) {
            eventRequestListDto.setRejectedRequests(repository.findEventRequestsByIdInAndEventIs(eventRequestStatusUpdateRequest.getRequestIds(), event)
                    .stream()
                    .map(EventRequestMapper::toEventRequestDto)
                    .collect(Collectors.toList()));
        } else {
            eventRequestListDto.setConfirmedRequests(repository.findEventRequestsByIdInAndEventIs(eventRequestStatusUpdateRequest.getRequestIds(), event)
                    .stream()
                    .map(EventRequestMapper::toEventRequestDto)
                    .collect(Collectors.toList()));
        }
        return eventRequestListDto;

    }

    @Override
    public List<EventRequestDto> getAllEventRequest(Long userId) {
        var requester = userRepository.findById(userId);
        if (requester.isEmpty()) {
            throw new ResourceNotFoundException(String.format("User with id=%d not found", userId));
        } else {
            return repository.findEventRequestsByRequester(requester.get())
                    .stream()
                    .map(EventRequestMapper::toEventRequestDto)
                    .collect(Collectors.toList());
        }

    }

}
