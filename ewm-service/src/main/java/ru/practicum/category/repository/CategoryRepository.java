package ru.practicum.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.category.model.Category;

import java.util.List;
import java.util.Optional;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAllByNameIn(List<String> names);

    List<Category> findAllByIdIn(List<Long> ids);

    Optional<Category> findById(Long Id);

}
