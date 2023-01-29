package explorewithme.service;


import explorewithme.mapper.StatMapper;
import explorewithme.model.Stats;
import explorewithme.repository.StatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.dtos.StatRequestDto;
import ru.practicum.dtos.StatResponseDto;

import java.time.LocalDateTime;
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
        stats.setTimeStamp(LocalDateTime.now());
        StatMapper.toStat(stats, statRequestDto);
        Stats createdStat = repository.save(stats);
        log.info("Stat created" + createdStat);
        return StatMapper.toStatDto(createdStat);
    }

    @Override
    public List<StatResponseDto> getStats(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique) {
        List<Stats> result;
        if (unique) {
            if (uris == null) {
                result = repository.findDistinctByIpAndTimeStampBetween(start, end);
            } else {
                result = repository.findDistinctByIpAndTimeStampBetweenAndUriIn(start, end, uris);
            }
        } else {
            if (uris == null) {
                result = repository.findStatsByTimeStampBetween(start, end);
            } else {
                result = repository.findStatsByTimeStampBetweenAndUriIn(start, end, uris);
            }
        }
        var responses = result.stream().map(StatMapper::toStatDto)
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()));
        responses.forEach(StatResponseDto::setHits);

        return responses.keySet().stream()
                .sorted(Comparator.comparingLong(StatResponseDto::getHits).reversed())
                .collect(Collectors.toList());
    }
}
