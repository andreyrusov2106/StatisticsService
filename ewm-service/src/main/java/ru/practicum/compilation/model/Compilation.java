package ru.practicum.compilation.model;


import lombok.*;
import ru.practicum.event.model.Event;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "compilations", schema = "public")

public class Compilation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany
    private List<Event> events;
    private Boolean pinned;
    private String title;
}
