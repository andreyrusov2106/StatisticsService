package explorewithme.controller;


import explorewithme.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.dtos.StatRequestDto;
import ru.practicum.dtos.StatResponseDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/statistic")
public class StatsController {

    private final StatsService statsService;

    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @PostMapping("/hit")
    public StatResponseDto createStat(@RequestBody StatRequestDto statRequestDto) {
        return statsService.createStat(statRequestDto);
    }

    @GetMapping("/stats")
    public List<StatResponseDto> getStat(@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime start,
                                         @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime end,
                                         @RequestParam(name = "uris") String[] uris,
                                         @RequestParam(name = "unique", defaultValue = "false") boolean unique) {
        return statsService.getStats(start, end, uris, unique);
    }

}
