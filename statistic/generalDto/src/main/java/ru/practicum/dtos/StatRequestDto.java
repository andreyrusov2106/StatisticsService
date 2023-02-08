package ru.practicum.dtos;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class StatRequestDto {
    protected Long id;
    protected String app;
    protected String uri;
    protected String ip;
    protected LocalDateTime timeStamp;

}
