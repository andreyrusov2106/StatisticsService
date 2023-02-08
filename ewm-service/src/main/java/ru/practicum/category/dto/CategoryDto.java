package ru.practicum.category.dto;

import lombok.*;
import lombok.experimental.NonFinal;

import javax.validation.constraints.NotNull;


@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Value
public class CategoryDto {
    @NonFinal
    Long id;
    @NonFinal
    @NotNull
    String name;
}
