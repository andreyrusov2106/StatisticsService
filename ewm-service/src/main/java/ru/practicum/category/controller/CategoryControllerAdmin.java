package ru.practicum.category.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.service.CategoryService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/categories")
public class CategoryControllerAdmin {
    private final CategoryService categoryService;

    @Autowired
    public CategoryControllerAdmin(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    public List<CategoryDto> findAll(@RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                     @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return categoryService.getAllCategoryAdmin(from, size);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public CategoryDto createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.createCategoryAdmin(categoryDto);
    }

    @PatchMapping("/{catId}")
    public CategoryDto updateCategory(@PathVariable Long catId, @RequestBody @Valid CategoryDto categoryDto) {
        return categoryService.updateCategoryAdmin(catId, categoryDto);
    }


    @GetMapping("/{id}")
    public CategoryDto getCategory(@PathVariable Long id) {
        return categoryService.getCategoryPublic(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{catId}")
    public void removeCategory(@PathVariable Long catId) {
        categoryService.removeCategoryAdmin(catId);
    }


}
