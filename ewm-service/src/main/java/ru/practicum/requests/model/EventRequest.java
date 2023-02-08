package ru.practicum.requests.model;


import lombok.*;
import ru.practicum.event.model.Event;
import ru.practicum.requests.dto.RequestStatus;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "event_requests", schema = "public")

public class EventRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User requester;
    @OneToOne
    private Event event;
    @Enumerated(EnumType.STRING)
    private RequestStatus state;
    private LocalDateTime created;

}
