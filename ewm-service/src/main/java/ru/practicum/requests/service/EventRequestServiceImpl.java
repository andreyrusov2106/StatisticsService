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

        var event = eventRepository.findById(eventId);
        if (event.isEmpty()) {
            throw new ResourceNotFoundException(String.format("event with id=%d not found", eventId));
        }
        if (Objects.equals(event.get().getInitiator().getId(), userId)) {
            throw new ConflictException("Incorrect StateAction");
        }
        if (event.get().getState() != State.PUBLISHED) {
            throw new ConflictException("Incorrect StateAction");
        }
        if (event.get().getParticipantLimit() == 0) {
            throw new ConflictException("ParticipantLimit is 0");
        } else {
            if (!event.get().getRequestModeration()) {
                event.get().setParticipantLimit(event.get().getParticipantLimit() - 1);
                eventRepository.save(event.get());
            }
        }
        eventRequest.setRequester(requester.get());
        eventRequest.setEvent(event.get());
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

        var event = eventRepository.findByInitiatorAndId(requester.get(), eventId);
        if (event.isEmpty()) {
            throw new ResourceNotFoundException(String.format("event with id=%d not found", eventId));
        }
        if (event.get().getParticipantLimit() == 0) {
            throw new ConflictException("ParticipantLimit is 0");
        } else {
            if (event.get().getRequestModeration()) {
                event.get().setParticipantLimit(event.get().getParticipantLimit() - (long) eventRequestStatusUpdateRequest.getRequestIds().size());
                eventRepository.save(event.get());
            }
        }
        var res = repository.findEventRequestsByIdInAndEventIs(eventRequestStatusUpdateRequest.getRequestIds(), event.get());
        if (res.size() == 0) {
            return eventRequestListDto;
        }
        res.forEach(eventRequest -> {
            eventRequest.setState(eventRequestStatusUpdateRequest.getStatus());
            repository.save(eventRequest);
        });

        if (eventRequestStatusUpdateRequest.getStatus() == RequestStatus.REJECTED) {
            eventRequestListDto.setRejectedRequests(repository.findEventRequestsByIdInAndEventIs(eventRequestStatusUpdateRequest.getRequestIds(), event.get())
                    .stream()
                    .map(EventRequestMapper::toEventRequestDto)
                    .collect(Collectors.toList()));
        } else {
            eventRequestListDto.setConfirmedRequests(repository.findEventRequestsByIdInAndEventIs(eventRequestStatusUpdateRequest.getRequestIds(), event.get())
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
