package ru.practicum.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.practicum.category.model.Category;
import ru.practicum.event.model.Event;

import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


public interface EventRepository extends PagingAndSortingRepository<Event, Long>, EventCustomRepository {

    Page<Event> findAll(Pageable pageable);

    List<Event> findAllByIdIn(List<Long> ids);

    Optional<Event> findByInitiatorAndId(User u, Long id);

    Page<Event> findAllByInitiator(User u, Pageable pageable);

    Event findEventByInitiatorAndId(User u, Long eventId);

}
