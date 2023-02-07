package ru.practicum.compilation.mapper;


import ru.practicum.compilation.dto.CompilationDtoResponse;
import ru.practicum.compilation.dto.CompilationDtoRequest;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.event.mapper.EventMapper;

import java.util.stream.Collectors;

public class CompilationMapper {
    public static CompilationDtoResponse toCompilationDto(Compilation compilation) {
        return CompilationDtoResponse.builder()
                .id(compilation.getId())
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .events(compilation.getEvents().stream().map(EventMapper::toEventShortDto).collect(Collectors.toList()))
                .build();
    }

    public static void toCompilation(Compilation compilation, CompilationDtoRequest compilationDto) {
        if (compilationDto.getId() != null) compilation.setId(compilationDto.getId());
        if (compilationDto.getPinned() != null) compilation.setPinned(compilationDto.getPinned());
        if (compilationDto.getTitle() != null) compilation.setTitle(compilationDto.getTitle());
    }
}
