package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.NonFinal;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.event.model.Location;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Value
public class EventDtoRequest {
    @NonFinal
    Long id;
    @NonFinal
    @NotNull
    String annotation;
    @NonFinal
    String description;
    @NonFinal
    String title;
    @NonFinal
    Long category;
    @NonFinal
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    @NonFinal
    Boolean paid;
    @NonFinal
    Boolean requestModeration;
    @NonFinal
    Long participantLimit;
    @NonFinal
    Location location;
}
