package ru.practicum.event.model;


import lombok.*;
import ru.practicum.category.model.Category;
import ru.practicum.comments.model.Comment;
import ru.practicum.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "events", schema = "public")

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long confirmedRequests;
    private Long participantLimit;
    private Long views;
    @Column(nullable = false)
    private String annotation;
    private String description;
    @Column(nullable = false)
    private String title;
    private Boolean paid;
    private Boolean requestModeration;
    private LocalDateTime eventDate;
    private LocalDateTime publishedOn;
    private LocalDateTime createdOn;
    @Enumerated(EnumType.STRING)
    private State state;
    @OneToOne
    private Category category;
    @OneToOne
    private User initiator;
    @OneToOne
    private Location location;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Comment> comments;

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
