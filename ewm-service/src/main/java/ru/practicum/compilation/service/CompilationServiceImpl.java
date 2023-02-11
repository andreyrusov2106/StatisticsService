package ru.practicum.compilation.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.compilation.dto.CompilationDtoResponse;
import ru.practicum.compilation.dto.CompilationDtoRequest;
import ru.practicum.compilation.mapper.CompilationMapper;
import ru.practicum.compilation.model.Compilation;
import ru.practicum.compilation.repository.CompilationRepository;
import ru.practicum.event.repository.EventRepository;
import ru.practicum.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompilationServiceImpl implements CompilationService {
    private final CompilationRepository repository;
    private final EventRepository eventRepository;

    @Override
    public CompilationDtoResponse createCompilationAdmin(CompilationDtoRequest categoryDto) {
        Compilation compilation = CompilationMapper.toCompilation(new Compilation(), categoryDto);
        if (categoryDto.getEvents() != null) {
            compilation.setEvents(eventRepository.findAllByIdIn(categoryDto.getEvents()));
        }
        Compilation createdCategory = repository.save(compilation);
        log.info("Category created" + createdCategory);
        return CompilationMapper.toCompilationDto(createdCategory);
    }

    @Override
    public CompilationDtoResponse updateCompilationAdmin(Long id, CompilationDtoRequest categoryDto) {
        var compilation = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("compilation with id=%d not found", id)));
        CompilationMapper.toCompilation(compilation, categoryDto);
        compilation.setId(id);
        if (categoryDto.getEvents() != null) {
            compilation.setEvents(eventRepository.findAllByIdIn(categoryDto.getEvents()));
        }
        Compilation updatedCompilation = repository.save(compilation);
        log.info("Category updated" + updatedCompilation);
        return CompilationMapper.toCompilationDto(updatedCompilation);
    }

    @Override
    public List<CompilationDtoResponse> getAllCompilationsAdmin(Integer from, Integer size) {
        Pageable pageable = sizeAndFromToPageable(from, size);
        return repository.findAll(pageable).stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CompilationDtoResponse> getCompilationsPublic(boolean pinned, Integer from, Integer size) {
        Pageable pageable = sizeAndFromToPageable(from, size);
        return repository.findAllByPinnedIs(pinned, pageable).stream()
                .map(CompilationMapper::toCompilationDto)
                .collect(Collectors.toList());
    }

    @Override
    public CompilationDtoResponse getCompilationAdmin(Long compId) {
        return CompilationMapper.toCompilationDto(repository.findCompilationById(compId));
    }


    @Override
    public void removeCompilation(Long id) {
        repository.deleteById(id);
    }

    private Pageable sizeAndFromToPageable(Integer from, Integer size) {
        return PageRequest.of(from / size, size);
    }
}
