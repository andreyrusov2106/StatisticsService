package explorewithme.repository;

import explorewithme.model.Stats;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends PagingAndSortingRepository<Stats, Long> {
    @Query("SELECT DISTINCT s FROM Stats s WHERE (s.timeStamp between :timeStamp AND :timeStamp2) and s.uri in :uri")
    List<Stats> findDistinctByIpAndTimeStampBetweenAndUriIn(LocalDateTime timeStamp, LocalDateTime timeStamp2, String[] uri);

    List<Stats> findStatsByTimeStampBetweenAndUriIn(LocalDateTime start, LocalDateTime end, String[] uris);

    List<Stats> findStatsByTimeStampBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT DISTINCT s FROM Stats s WHERE s.timeStamp between :timeStamp AND :timeStamp2")
    List<Stats> findDistinctByIpAndTimeStampBetween(LocalDateTime timeStamp, LocalDateTime timeStamp2);
}
