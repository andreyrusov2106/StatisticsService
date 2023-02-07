package ru.practicum.requests.dto;

import lombok.*;
import lombok.experimental.NonFinal;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Value
public class EventRequestStatusUpdateRequest {
    @NonFinal
    List<Long> requestIds;
    @NonFinal
    RequestStatus status;
}
