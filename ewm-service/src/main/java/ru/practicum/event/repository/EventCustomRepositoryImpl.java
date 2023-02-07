package ru.practicum.event.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import ru.practicum.category.model.Category;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.State;
import ru.practicum.user.model.User;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public class EventCustomRepositoryImpl implements EventCustomRepository {


    private final EntityManager entityManager;

    public EventCustomRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<Event> findEventsByInitiatorIn(List<User> initiators,
                                               List<State> states,
                                               LocalDateTime start,
                                               LocalDateTime end,
                                               Collection<Category> category,
                                               Pageable pageable) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Event> cq = cb.createQuery(Event.class);
        Root<Event> root = cq.from(Event.class);
        Predicate usersPredicate = initiators.size() != 0 ? cb.equal(root.get("initiator"), initiators) : cb.conjunction();
        Predicate statesPredicate = states.size() != 0 ? cb.equal(root.get("state"), states) : cb.conjunction();
        Predicate categoryPredicate = category.size() != 0 ? cb.equal(root.get("category"), category) : cb.conjunction();
        Predicate eventDatePredicate;
        if (start != null && end != null) {
            eventDatePredicate = cb.between(root.get("eventDate"), start, end);
        } else {
            if (start != null) {
                eventDatePredicate = cb.greaterThan(root.get("eventDate"), start);
            } else {
                eventDatePredicate = cb.conjunction();
            }
        }

        cq.where(usersPredicate, statesPredicate, categoryPredicate, eventDatePredicate);
        TypedQuery<Event> query = entityManager.createQuery(cq);
        int totalRows = query.getResultList().size();

        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());
        return new PageImpl<>(query.getResultList(), pageable, totalRows);

    }
}
