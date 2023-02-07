package ru.practicum.requests.dto;

import lombok.*;
import lombok.experimental.NonFinal;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Value
public class EventRequestDto {
    @NonFinal
    Long id;
    @NonFinal
    Long requester;
    @NonFinal
    Long event;
    @NonFinal
    RequestStatus status;
    @NonFinal
    LocalDateTime created;
}
