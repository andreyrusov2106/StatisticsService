package ru.practicum.comments.dto;

import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Builder
@Value
public class CommentDtoRequest {
    @NotEmpty
    String text;
    Long author;
    Long event;
}
