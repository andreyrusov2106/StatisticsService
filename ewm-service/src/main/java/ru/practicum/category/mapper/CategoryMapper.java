package ru.practicum.category.mapper;


import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.model.Category;

public class CategoryMapper {
    public static CategoryDto toCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static void toCategory(Category category, CategoryDto categoryDto) {
        if (categoryDto.getId() != null) category.setId(categoryDto.getId());
        if (categoryDto.getName() != null) category.setName(categoryDto.getName());
    }
}
