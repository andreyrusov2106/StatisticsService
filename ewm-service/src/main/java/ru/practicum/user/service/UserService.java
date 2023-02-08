package ru.practicum.user.service;


import ru.practicum.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto updateUser(Long id, UserDto userDto);

    List<UserDto> getAllUsers(Long[] ids, Integer from, Integer size);

    UserDto getUser(Long id);

    void removeUser(Long id);

}
