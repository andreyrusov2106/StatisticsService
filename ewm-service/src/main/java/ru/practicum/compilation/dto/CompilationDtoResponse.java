package ru.practicum.compilation.dto;

import lombok.*;
import lombok.experimental.NonFinal;
import ru.practicum.event.dto.EventDtoShortResponse;

import javax.validation.constraints.NotNull;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Value
public class CompilationDtoResponse {
    @NonFinal
    Long id;
    @NonFinal
    List<EventDtoShortResponse> events;
    @NonFinal
    Boolean pinned;
    @NonFinal
    @NotNull
    String title;
}
