package ru.practicum.user.dto;

import lombok.*;
import lombok.experimental.NonFinal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Value
public class UserDto {
    @NonFinal
    Long id;
    @NonFinal
    @NotNull
    String name;
    @Email
    @NotEmpty
    @NonFinal
    String email;


}
