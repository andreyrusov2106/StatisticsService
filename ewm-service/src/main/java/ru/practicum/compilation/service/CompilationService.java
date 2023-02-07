package ru.practicum.compilation.service;


import ru.practicum.compilation.dto.CompilationDtoResponse;
import ru.practicum.compilation.dto.CompilationDtoRequest;

import java.util.List;

public interface CompilationService {
    CompilationDtoResponse createCompilationAdmin(CompilationDtoRequest userDto);

    CompilationDtoResponse updateCompilationAdmin(Long id, CompilationDtoRequest userDto);

    List<CompilationDtoResponse> getAllCompilationsAdmin(Integer from, Integer size);

    List<CompilationDtoResponse> getCompilationsPublic(boolean pinned, Integer from, Integer size);

    CompilationDtoResponse getCompilationAdmin(Long compId);

    void removeCompilation(Long id);

}
