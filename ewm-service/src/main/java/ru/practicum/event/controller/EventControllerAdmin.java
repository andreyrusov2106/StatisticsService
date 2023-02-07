package ru.practicum.event.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.EventDtoResponse;
import ru.practicum.event.dto.UpdateEventUserRequest;
import ru.practicum.event.service.EventService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
public class EventControllerAdmin {
    private final EventService eventService;

    @Autowired
    public EventControllerAdmin(EventService eventService) {
        this.eventService = eventService;
    }
    @GetMapping()
    public List<EventDtoResponse> findAll(@RequestParam(name = "text", required = false) Long[] userIds,
                                               @RequestParam(name = "states", required = false) String[] states,
                                               @RequestParam(name = "categories", required = false) Long[] categories,
                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                               @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                               @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return eventService.getAllEventsAdmin(userIds, states, categories, rangeStart, rangeEnd, from,size);
    }
    @PatchMapping("/{eventId}")
    public EventDtoResponse updateEvent(@PathVariable Long eventId,
                            @Valid @RequestBody UpdateEventUserRequest eventDtoRequest) {

        return eventService.updateEventAdmin(eventId, eventDtoRequest);
    }


}
