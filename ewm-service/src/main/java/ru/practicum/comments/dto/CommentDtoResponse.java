package ru.practicum.comments.dto;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Builder
@Value
public class CommentDtoResponse {
    Long id;
    String text;
    LocalDateTime created;
    String authorName;
}
