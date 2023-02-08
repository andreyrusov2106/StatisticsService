package ru.explorewithme.service;


import ru.explorewithme.mapper.StatMapper;
import ru.explorewithme.model.StatResultProjection;
import ru.explorewithme.model.Stats;
import ru.explorewithme.repository.StatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.dtos.StatRequestDto;
import ru.practicum.dtos.StatResponseDto;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository repository;

    @Override
    public StatResponseDto createStat(StatRequestDto statRequestDto) {
        Stats stats = new Stats();
        stats.setTimeStamp(LocalDateTime.now().withNano(0));
        StatMapper.toStat(stats, statRequestDto);
        Stats createdStat = repository.save(stats);
        log.info("Stat created" + createdStat);
        return StatMapper.toStatDto(createdStat);
    }

    @Override
    public List<StatResponseDto> getStats(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        List<StatResultProjection> result;
        result = repository.getAuthorStatistic(start,
                end,
                Arrays.stream(uris).collect(Collectors.toList())
                , true);

        return result.stream()
                .map(s -> new StatResponseDto(s.getApp(), s.getUri(), s.getCount()))
                .sorted(Comparator.comparingLong(StatResponseDto::getHits).reversed())
                .collect(Collectors.toList());

    }
}
