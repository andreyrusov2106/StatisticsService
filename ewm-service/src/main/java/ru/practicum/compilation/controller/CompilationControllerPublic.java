package ru.practicum.compilation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.practicum.compilation.dto.CompilationDtoResponse;
import ru.practicum.compilation.service.CompilationService;

import java.util.List;

@RestController
@RequestMapping(path = "/compilations")
public class CompilationControllerPublic {
    private final CompilationService compilationService;

    @Autowired
    public CompilationControllerPublic(CompilationService compilationService) {
        this.compilationService = compilationService;
    }

    @GetMapping("/{compId}")
    public CompilationDtoResponse getCompilation(@PathVariable Long compId) {
        return compilationService.getCompilationAdmin(compId);
    }

    @GetMapping()
    public List<CompilationDtoResponse> getCategory(@RequestParam(name = "pinned", required = false, defaultValue = "false") Boolean pinned,
                                                    @RequestParam(name = "from", required = false, defaultValue = "0") Integer from,
                                                    @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        return compilationService.getCompilationsPublic(pinned, from, size);
    }


}
