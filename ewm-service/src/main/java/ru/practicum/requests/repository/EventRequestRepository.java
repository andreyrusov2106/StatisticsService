package ru.practicum.requests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.event.model.Event;
import ru.practicum.requests.model.EventRequest;
import ru.practicum.user.model.User;

import java.util.List;


public interface EventRequestRepository extends JpaRepository<EventRequest, Long> {
    List<EventRequest> findEventRequestsByRequester(User requester);

    List<EventRequest> findEventRequestsByIdInAndEventIs(List<Long> requestIds, Event event);

    List<EventRequest> findAllEventRequestsByEventIs(Event event);

    EventRequest findAEventRequestByIdIsAndRequesterIs(Long requestId, User requester);
}
