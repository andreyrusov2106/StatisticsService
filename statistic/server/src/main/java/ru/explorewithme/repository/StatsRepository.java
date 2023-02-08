package ru.explorewithme.repository;

import org.springframework.context.annotation.Primary;
import ru.explorewithme.model.StatResultProjection;
import ru.explorewithme.model.Stats;

import java.time.LocalDateTime;
import java.util.List;

@Primary
public interface StatsRepository extends BaseRepository<Stats, Integer> {

    List<StatResultProjection> getAuthorStatistic(LocalDateTime start, LocalDateTime end, List<String> uri, boolean unique);
}
