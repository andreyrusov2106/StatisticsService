package ru.practicum.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.comments.dto.CommentDtoRequest;
import ru.practicum.comments.dto.CommentDtoResponse;
import ru.practicum.comments.service.CommentService;
import ru.practicum.event.dto.EventDtoRequest;
import ru.practicum.event.dto.EventDtoResponse;
import ru.practicum.event.dto.EventDtoShortResponse;
import ru.practicum.event.dto.UpdateEventUserRequest;
import ru.practicum.event.service.EventService;
import ru.practicum.requests.dto.EventRequestDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/users")
public class EventControllerPrivate {
    private final EventService eventService;
    private final CommentService commentService;

    @Autowired
    public EventControllerPrivate(EventService eventService, CommentService commentService) {
        this.eventService = eventService;
        this.commentService = commentService;
    }

    @GetMapping("/{userId}/events")
    public List<EventDtoShortResponse> findAllByUserIdPrivate(@PathVariable Long userId,
                                                              @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                                              @RequestParam(name = "size", required = false, defaultValue = "10") Integer size
    ) {
        return eventService.getAllEventsPrivate(userId, from, size);
    }

    @GetMapping("/{userId}/events/{eventId}")
    public EventDtoResponse findByUserIdAndEventIdPrivate(@PathVariable Long userId,
                                                          @PathVariable Long eventId) {
        return eventService.getEventPrivate(userId, eventId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}/events")
    public EventDtoResponse createEventPrivate(@Valid @RequestBody EventDtoRequest eventDtoRequest,
                                               @PathVariable Long userId) {
        return eventService.createEventPrivate(userId, eventDtoRequest);
    }

    @PatchMapping("/{userId}/events/{eventId}")
    public EventDtoResponse updateEventPrivate(@PathVariable Long userId,
                                               @PathVariable Long eventId,
                                               @Valid @RequestBody UpdateEventUserRequest eventDtoRequest) {
        return eventService.updateEventPrivate(userId, eventId, eventDtoRequest);
    }

    @GetMapping("/{userId}/events/{eventId}/requests")
    public List<EventRequestDto> findAllRequests(@PathVariable Long userId,
                                                 @PathVariable Long eventId) {
        return eventService.getEventRequestsPrivate(userId, eventId);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{userId}/events/{eventId}/comments")
    public CommentDtoResponse createCommentPrivate(@Valid @RequestBody CommentDtoRequest commentDtoRequest,
                                                   @PathVariable Long userId,
                                                   @PathVariable Long eventId) {
        return commentService.createComment(commentDtoRequest, userId, eventId);
    }
}
