package ru.practicum.user.mapper;


import ru.practicum.user.dto.UserDto;
import ru.practicum.user.model.User;

public class UserMapper {
    public static UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public static User toUser(User user, UserDto userDto) {
        if (userDto.getId() != null) user.setId(userDto.getId());
        if (userDto.getName() != null) user.setName(userDto.getName());
        if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
        return user;
    }
}
