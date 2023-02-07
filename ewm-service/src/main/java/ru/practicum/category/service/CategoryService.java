package ru.practicum.category.service;


import ru.practicum.category.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategoryAdmin(CategoryDto userDto);

    CategoryDto updateCategoryAdmin(Long id, CategoryDto userDto);

    List<CategoryDto> getAllCategoryAdmin(Integer from, Integer size);

    List<CategoryDto> getAllCategoriesPublic(Integer from, Integer size);

    CategoryDto getCategoryPublic(Long id);

    void removeCategoryAdmin(Long id);

}
