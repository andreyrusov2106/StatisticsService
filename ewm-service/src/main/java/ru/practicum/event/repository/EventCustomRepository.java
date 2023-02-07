package ru.practicum.event.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.practicum.category.model.Category;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface EventCustomRepository {

    Page<Event> findEventsByInitiatorIn(List<User> initiator,
                                        List<State> states,
                                        LocalDateTime eventDate,
                                        LocalDateTime eventDate2,
                                        Collection<Category> category,
                                        Pageable pageable);
}
