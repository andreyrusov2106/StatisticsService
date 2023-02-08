package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import ru.practicum.exceptions.ResourceNotFoundException;
import ru.practicum.user.dto.UserDto;
import ru.practicum.user.mapper.UserMapper;
import ru.practicum.user.model.User;
import ru.practicum.user.repository.UserRepository;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository repository;

    @Override
    public UserDto createUser(UserDto userDto) {
        var user = UserMapper.toUser(new User(), userDto);
        User createdUser = repository.save(user);
        log.info("User created" + createdUser);
        return UserMapper.toUserDto(createdUser);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User updatedUser;
        var userOptional = repository.findById(id);
        if (userOptional.isPresent()) {
            var user = userOptional.get();
            UserMapper.toUser(user, userDto);
            user.setId(id);
            updatedUser = repository.save(user);
            log.info("User updated" + updatedUser);
            return UserMapper.toUserDto(updatedUser);
        } else {
            throw new ResourceNotFoundException(String.format("User with id=%d not found", id));
        }
    }

    @Override
    public List<UserDto> getAllUsers(Long[] ids, Integer from, Integer size) {
        Pageable pageable = sizeAndFromToPageable(from, size);
        if (ids == null) {
            return repository.findAll(pageable).stream()
                    .map(UserMapper::toUserDto)
                    .collect(Collectors.toList());
        } else {
            return repository.findUsersByIdIn(List.of(ids), pageable).stream()
                    .map(UserMapper::toUserDto)
                    .collect(Collectors.toList());
        }
    }

    @Override
    public UserDto getUser(Long id) {
        var user = repository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException(String.format("User with id=%d not found", id));
        }
        return UserMapper.toUserDto(user.get());
    }

    @Override
    public void removeUser(Long id) {
        repository.deleteAllById(Collections.singleton(id));
    }

    private Pageable sizeAndFromToPageable(Integer from, Integer size) {
        return PageRequest.of(from / size, size);
    }
}
