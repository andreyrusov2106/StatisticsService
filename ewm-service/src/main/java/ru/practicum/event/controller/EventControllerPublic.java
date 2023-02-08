package ru.practicum.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventDtoResponse;
import ru.practicum.event.dto.EventDtoShortResponse;
import ru.practicum.event.service.EventService;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
public class EventControllerPublic {
    private final EventService eventService;

    @Autowired
    public EventControllerPublic(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping()
    public List<EventDtoShortResponse> findAll(@RequestParam(name = "text", required = false) String text,
                                               @RequestParam(name = "categories", required = false) List<String> categories,
                                               @RequestParam(name = "paid", required = false) Boolean paid,
                                               @RequestParam(name = "onlyAvailable", defaultValue = "false", required = false) Boolean onlyAvailable,
                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                               @RequestParam(name = "sort", required = false) String sort,
                                               @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                               @RequestParam(name = "size", required = false, defaultValue = "10") Integer size,
                                               HttpServletRequest request) {
        return eventService.getAllEventsPublic(text, categories, paid, onlyAvailable, rangeStart, rangeEnd, sort, from, size, request);
    }

    @GetMapping("/{id}")
    public EventDtoResponse getEvent(@PathVariable Long id,
                                     HttpServletRequest request) {
        return eventService.getEventPublic(id, request);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{userId}")
    public void removeEvent(@PathVariable Long userId) {
        eventService.removeEventPublic(userId);
    }


}
