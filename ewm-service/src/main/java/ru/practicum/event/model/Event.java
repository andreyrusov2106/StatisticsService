package ru.practicum.event.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.category.model.Category;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events", schema = "public")

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "confirmed_requests")
    private Long confirmedRequests;
    @Column(name = "participant_limit")
    private Long participantLimit;
    @Column(name = "views")
    private Long views;
    @Column(name = "annotation", nullable = false)
    private String annotation;
    @Column(name = "description")
    private String description;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "paid")
    private Boolean paid;
    @Column(name = "request_moderation")
    private Boolean requestModeration;
    @Column(name = "event_date")
    private LocalDateTime eventDate;
    @Column(name = "published_on")
    private LocalDateTime publishedOn;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private State state;
    @OneToOne
    private Category category;
    @OneToOne
    private User initiator;
    @OneToOne
    private Location location;
}
