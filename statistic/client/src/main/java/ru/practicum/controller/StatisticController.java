package ru.practicum.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.StatisticClient;
import ru.practicum.dtos.StatRequestDto;


import javax.validation.Valid;
import java.util.Arrays;

@RestController
@RequestMapping()
@RequiredArgsConstructor
@Slf4j
@Validated
public class StatisticController {

    private final StatisticClient statisticClient;

    @PostMapping("/hit")
    public ResponseEntity<Object> createStatistic(@RequestBody @Valid StatRequestDto requestDto) {
        log.info("Creating statistic {}", requestDto);
        return statisticClient.createStatistic(requestDto);
    }

    @GetMapping("/stats")
    public ResponseEntity<Object> getStatistic(@RequestParam(name = "start") String start,
                                               @RequestParam(name = "end") String end,
                                               @RequestParam(name = "uris", required = false) String[] uris,
                                               @RequestParam(name = "unique", required = false, defaultValue = "false") boolean unique) {
        log.info("Get statistic with start {}, end={}, uris={}, unique={}", start, end, Arrays.toString(uris), unique);
        return statisticClient.getStatistic(start, end, uris, unique);
    }
}
