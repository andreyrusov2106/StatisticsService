package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.exceptions.ResourceNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository repository;

    @Override
    public CategoryDto createCategoryAdmin(CategoryDto categoryDto) {
        Category category = new Category();
        CategoryMapper.toCategory(category, categoryDto);
        Category createdCategory = repository.save(category);
        log.info("Category created" + createdCategory);
        return CategoryMapper.toCategoryDto(createdCategory);
    }

    @Override
    public CategoryDto updateCategoryAdmin(Long id, CategoryDto categoryDto) {
        Category updatedCategory;
        var category = repository.findById(id);
        if (category.isPresent()) {
            CategoryMapper.toCategory(category.get(), categoryDto);
            category.get().setId(id);
            updatedCategory = repository.save(category.get());
            log.info("Category updated" + updatedCategory);
            return CategoryMapper.toCategoryDto(updatedCategory);
        } else {
            throw new ResourceNotFoundException("Category not found");
        }
    }

    @Override
    public List<CategoryDto> getAllCategoryAdmin(Integer from, Integer size) {
        Pageable pageable = sizeAndFromToPageable(from, size);
        return repository
                .findAll(pageable)
                .stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CategoryDto> getAllCategoriesPublic(Integer from, Integer size) {
        Pageable pageable = sizeAndFromToPageable(from, size);
        return repository.findAll(pageable)
                .stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryPublic(Long id) {
        var category = repository.findById(id);
        if (category.isEmpty()) {
            throw new ResourceNotFoundException("Category not found");
        }
        return CategoryMapper.toCategoryDto(category.get());
    }

    @Override
    public void removeCategoryAdmin(Long id) {
        repository.deleteAllById(Collections.singleton(id));
    }

    private Pageable sizeAndFromToPageable(Integer from, Integer size) {
        return PageRequest.of(from / size, size);
    }
}
