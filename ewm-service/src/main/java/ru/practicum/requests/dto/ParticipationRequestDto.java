package ru.practicum.requests.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.NonFinal;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Value
public class ParticipationRequestDto {
    @NonFinal
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS", iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    LocalDateTime created;
    @NonFinal
    Long event;
    @NonFinal
    Long id;
    @NonFinal
    Long requester;
    @NonFinal
    String status;
}
