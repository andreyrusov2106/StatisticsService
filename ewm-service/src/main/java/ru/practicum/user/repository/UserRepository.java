package ru.practicum.user.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import ru.practicum.user.model.User;

import java.util.List;


public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    Page<User> findUsersByIdIn(List<Long> ids, Pageable pageable);
    List<User> findUsersByIdIn(List<Long> ids);
    Page<User> findAll(Pageable pageable);

}
