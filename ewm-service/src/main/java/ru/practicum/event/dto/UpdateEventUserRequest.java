package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.NonFinal;
import org.springframework.format.annotation.DateTimeFormat;
import ru.practicum.event.model.Location;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Value
public class UpdateEventUserRequest {
    @NonFinal
    Long id;
    @NonFinal
    String annotation;
    @NonFinal
    Long category;
    @NonFinal
    String description;
    @NonFinal
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    @NonFinal
    Location location;
    @NonFinal
    Boolean paid;
    @NonFinal
    Long participantLimit;
    @NonFinal
    Boolean requestModeration;
    @NonFinal
    String stateAction;
    @NonFinal
    String title;


}
