package ru.practicum.compilation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDtoResponse;
import ru.practicum.compilation.dto.CompilationDtoRequest;
import ru.practicum.compilation.service.CompilationService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/compilations")
public class CompilationControllerAdmin {
    private final CompilationService compilationService;

    @Autowired
    public CompilationControllerAdmin(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @GetMapping()
    public List<CompilationDtoResponse> findAll(@RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                                @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return compilationService.getAllCompilationsAdmin(from, size);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public CompilationDtoResponse createCategory(@Valid @RequestBody CompilationDtoRequest categoryDto) {
        return compilationService.createCompilationAdmin(categoryDto);
    }

    @PatchMapping("/{catId}")
    public CompilationDtoResponse updateCompilation(@PathVariable Long catId, @RequestBody CompilationDtoRequest categoryDto) {
        return compilationService.updateCompilationAdmin(catId, categoryDto);
    }


    @GetMapping("/{id}")
    public CompilationDtoResponse getCompilation(@PathVariable Long id) {
        return compilationService.getCompilationAdmin(id);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{catId}")
    public void removeCategory(@PathVariable Long catId) {
        compilationService.removeCompilation(catId);
    }


}
