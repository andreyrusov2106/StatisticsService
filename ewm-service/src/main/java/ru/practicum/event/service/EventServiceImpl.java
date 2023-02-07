package ru.practicum.event.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.client.StatisticClientEvm;
import ru.practicum.dtos.StatRequestDto;
import ru.practicum.event.dto.EventDtoRequest;
import ru.practicum.event.dto.EventDtoResponse;
import ru.practicum.event.dto.EventDtoShortResponse;
import ru.practicum.event.dto.UpdateEventUserRequest;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.Location;
import ru.practicum.event.model.State;
import ru.practicum.event.model.StateAction;
import ru.practicum.event.repository.LocationRepository;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exceptions.ConflictException;
import ru.practicum.exceptions.ResourceNotFoundException;
import ru.practicum.requests.dto.EventRequestDto;
import ru.practicum.requests.mapper.EventRequestMapper;
import ru.practicum.requests.repository.EventRequestRepository;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;

    private final EventRequestRepository eventRequestRepository;
    private final UserRepository userRepository;
    private final LocationRepository locationRepository;
    private final CategoryRepository categoryRepository;
    private final StatisticClientEvm client;


    @Override
    public EventDtoResponse createEventPrivate(Long userId, EventDtoRequest eventDto) {
        Event event = new Event();
        if (eventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ConflictException("Incorrect event date");
        }
        event.setCreatedOn(LocalDateTime.now().withNano(0));
        event.setState(State.PENDING);
        EventMapper.toEvent(event, eventDto);
        var initiator = userRepository.findById(userId);
        if (initiator.isEmpty()) {
            throw new ResourceNotFoundException(String.format("User with id=%d not found", userId));
        } else {
            event.setInitiator(initiator.get());
        }
        if (event.getLocation() != null) {
            Location createdLocation = locationRepository.save(eventDto.getLocation());
            event.setLocation(createdLocation);
        }
        var category = categoryRepository.findById(eventDto.getCategory());
        category.ifPresent(event::setCategory);

        Event createdUser = eventRepository.save(event);
        log.info("User created" + createdUser);
        return EventMapper.toEventDto(createdUser);
    }

    @Override
    public EventDtoResponse updateEventPrivate(Long userId, Long eventId, UpdateEventUserRequest eventDto) {
        Event updatedEvent;
        if (eventDto.getEventDate() != null && eventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            throw new ConflictException("Incorrect event date");
        }
        var event = eventRepository.findById(eventId);
        if (event.isPresent()) {
            if (event.get().getInitiator().getId().equals(userId)) {
                if (event.get().getState() == State.PUBLISHED) {
                    throw new ConflictException("Can not be changed");
                }
                EventMapper.toEvent(event.get(), eventDto);
                if (eventDto.getStateAction() != null) {
                    event.get().setState(StateAction.stringToState(eventDto.getStateAction()));
                }
                event.get().setId(eventId);
                updatedEvent = eventRepository.save(event.get());
                log.info("Event updated" + updatedEvent);
                return EventMapper.toEventDto(updatedEvent);
            } else {
                throw new ResourceNotFoundException("Initiator is not correct");
            }
        } else {
            throw new ResourceNotFoundException("Event not found");
        }
    }

    @Override
    public List<EventDtoShortResponse> getAllEventsPublic(String text,
                                                          String[] categories,
                                                          Boolean paid,
                                                          Boolean onlyAvailable,
                                                          LocalDateTime start,
                                                          LocalDateTime end,
                                                          String sort,
                                                          Integer from,
                                                          Integer size,
                                                          HttpServletRequest request) {
        Pageable pageable = sizeAndFromToPageable(from, size);
        StatRequestDto statRequestDto = StatRequestDto.builder().app("ExploreWithMe")
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .timeStamp(LocalDateTime.now().withNano(0)).build();
        client.createStatistic(statRequestDto);
        List<Category> categoryList = new ArrayList<>();
        if (categories != null) {
            categoryList = categoryRepository.findAllByNameIn(Arrays.stream(categories).collect(Collectors.toList()));
        }

        return eventRepository.findEventsByAnnotationContainingIgnoreCaseOrDescriptionIgnoreCaseAndEventDateBetweenAndPaidAndCategoryIn(text, text, start, end, paid, categoryList, pageable)
                .stream()
                .map(EventMapper::toEventShortDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<EventDtoResponse> getAllEventsAdmin(Long[] users,
                                                    String[] states,
                                                    Long[] categories,
                                                    LocalDateTime start,
                                                    LocalDateTime end,
                                                    Integer from,
                                                    Integer size) {
        Pageable pageable = sizeAndFromToPageable(from, size);
        List<Category> categoryList = Collections.emptyList();
        List<State> stateList = Collections.emptyList();
        List<User> userList = Collections.emptyList();
        if (categories != null) {
            categoryList = categoryRepository.findAllByIdIn(Arrays.stream(categories).collect(Collectors.toList()));
            if (categoryList.size() == 0) {
                categoryList = Collections.emptyList();
            }
        }
        if (states != null) {
            stateList = Arrays.stream(states).map(State::stringToState).collect(Collectors.toList());
        }
        if (users != null) {
            userList = userRepository.findUsersByIdIn(Arrays.stream(users).collect(Collectors.toList()));
        }
        return eventRepository.findEventsByInitiatorIn(userList, stateList, start, end, categoryList, pageable)
                .stream()
                .map(EventMapper::toEventDto)
                .collect(Collectors.toList());
    }


    @Override
    public List<EventDtoShortResponse> getAllEventsPrivate(Long userId, Integer from, Integer size) {
        Pageable pageable = sizeAndFromToPageable(from, size);
        var initiator = userRepository.findById(userId);
        if (initiator.isEmpty()) {
            throw new ResourceNotFoundException(String.format("User with id=%d not found", userId));
        } else {
            return eventRepository.findAllByInitiator(initiator.get(), pageable)
                    .stream()
                    .map(EventMapper::toEventShortDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public EventDtoResponse getEventPrivate(Long userId, Long eventId) {
        var initiator = userRepository.findById(userId);
        if (initiator.isEmpty()) {
            throw new ResourceNotFoundException(String.format("User with id=%d not found", userId));
        } else {
            return EventMapper.toEventDto(eventRepository.findEventByInitiatorAndId(initiator.get(), eventId));
        }
    }

    @Override
    public List<EventRequestDto> getEventRequestsPrivate(Long userId, Long eventId) {
        var initiator = userRepository.findById(userId);
        if (initiator.isEmpty()) {
            throw new ResourceNotFoundException(String.format("User with id=%d not found", userId));
        }
        var event = eventRepository.findByInitiatorAndId(initiator.get(), eventId);
        if (event.isEmpty()) {
            throw new ResourceNotFoundException(String.format("Event with id=%d not found", userId));
        }
        return eventRequestRepository.findAllEventRequestsByEventIs(event.get())
                .stream().map(EventRequestMapper::toEventRequestDto)
                .collect(Collectors.toList());
    }

    @Override
    public EventDtoResponse updateEventAdmin(Long eventId, UpdateEventUserRequest eventDtoRequest) {
        Event updatedEvent;
        if (eventDtoRequest.getEventDate() != null && eventDtoRequest.getEventDate().isBefore(LocalDateTime.now().plusHours(1))) {
            throw new ConflictException("EventDate has to be after now +1Hour");
        }

        var event = eventRepository.findById(eventId);
        if (event.isPresent()) {
            if (eventDtoRequest.getStateAction() != null) {
                if (StateAction.stringToState(eventDtoRequest.getStateAction()) == event.get().getState()) {
                    throw new ConflictException("StateAction is the same as State");
                }
                if (StateAction.stringToStateAction(eventDtoRequest.getStateAction()) == StateAction.PUBLISH_EVENT
                        && event.get().getState() == State.CANCELED) {
                    throw new ConflictException("Incorrect StateAction");
                }
                if (StateAction.stringToStateAction(eventDtoRequest.getStateAction()) == StateAction.REJECT_EVENT
                        && event.get().getState() == State.PUBLISHED) {
                    throw new ConflictException("Incorrect StateAction");
                }
            }
            EventMapper.toEvent(event.get(), eventDtoRequest);
            if (StateAction.stringToStateAction(eventDtoRequest.getStateAction()) == StateAction.PUBLISH_EVENT) {
                event.get().setPublishedOn(LocalDateTime.now().withNano(0));
            }
            event.get().setState(StateAction.stringToState(eventDtoRequest.getStateAction()));
            event.get().setId(eventId);
            if (eventDtoRequest.getLocation() != null) {
                Location createdLocation = locationRepository.save(eventDtoRequest.getLocation());
                event.get().setLocation(createdLocation);
            }
            updatedEvent = eventRepository.save(event.get());
            log.info("Event updated" + updatedEvent);
            return EventMapper.toEventDto(updatedEvent);
        } else {
            throw new ResourceNotFoundException("Event not found");
        }
    }

    @Override
    public EventDtoResponse getEventPublic(Long id,
                                           HttpServletRequest request) {
        StatRequestDto statRequestDto = StatRequestDto.builder().app("ExploreWithMe")
                .ip(request.getRemoteAddr())
                .uri(request.getRequestURI())
                .timeStamp(LocalDateTime.now().withNano(0)).build();
        client.createStatistic(statRequestDto);
        var user = eventRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("User not found");
        }
        return EventMapper.toEventDto(user.get());
    }

    @Override
    public void removeEventPublic(Long id) {
        eventRepository.deleteAllById(Collections.singleton(id));
    }

    private Pageable sizeAndFromToPageable(Integer from, Integer size) {
        return PageRequest.of(from / size, size);
    }
}
