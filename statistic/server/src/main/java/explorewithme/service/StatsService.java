package explorewithme.service;


import ru.practicum.dtos.StatRequestDto;
import ru.practicum.dtos.StatResponseDto;


import java.time.LocalDateTime;
import java.util.List;

public interface StatsService {
    StatResponseDto createStat(StatRequestDto userDto);

    List<StatResponseDto> getStats(LocalDateTime start, LocalDateTime end, String[] uris, boolean unique);
}
