package ru.practicum.errors;

import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.FieldError;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class ApiError {
    List<FieldError> errors;
    String message;
    String reason;
    String status;
    LocalDateTime timestamp;
}
