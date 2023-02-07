package ru.practicum.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping(path = "/categories")
public class CategoryControllerPublic {
    private final CategoryService categoryService;

    @Autowired
    public CategoryControllerPublic(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public List<CategoryDto> findAll(@RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                     @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return categoryService.getAllCategoriesPublic(from, size);
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategory(@PathVariable Long catId) {
        return categoryService.getCategoryPublic(catId);
    }


}
