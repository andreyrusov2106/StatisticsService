package explorewithme.repository;

import explorewithme.model.StatResultProjection;
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

    @Query(value = "Select sel1.uri, sel1.app, count(*) From (SELECT Distinct(s1.ip), s1.uri,s1.app " +
            "FROM  statistic s1 where s1.uri in(:uri) and s1.created between :timeStamp and :timeStamp2) sel1 \n" +
            "GROUP BY sel1.uri, sel1.app", nativeQuery = true)
    List<StatResultProjection> findDistinctByIpAndTimeStampBetweenAndUriInNative(LocalDateTime timeStamp, LocalDateTime timeStamp2, List<String> uri);

    @Query(value = "Select sel1.uri, sel1.app, count(*) From (SELECT Distinct(s1.ip), s1.uri,s1.app " +
            "FROM  statistic s1 where s1.created between :timeStamp and :timeStamp2) sel1 \n" +
            "GROUP BY sel1.uri, sel1.app", nativeQuery = true)
    List<StatResultProjection> findDistinctByIpAndTimeStampBetweenAndNative(LocalDateTime timeStamp, LocalDateTime timeStamp2);

    @Query(value = "Select sel1.uri, sel1.app, count(*) From (SELECT s1.uri,s1.app " +
            "FROM  statistic s1 where s1.created between :timeStamp and :timeStamp2) sel1 \n" +
            "GROUP BY sel1.uri, sel1.app", nativeQuery = true)
    List<StatResultProjection> findTimeStampBetweenAndNative(LocalDateTime timeStamp, LocalDateTime timeStamp2);

    @Query(value = "Select sel1.uri, sel1.app, count(*) From (SELECT s1.uri,s1.app " +
            "FROM  statistic s1 where s1.uri in(:uri) and s1.created between :timeStamp and :timeStamp2) sel1 \n" +
            "GROUP BY sel1.uri, sel1.app", nativeQuery = true)
    List<StatResultProjection> findTimeStampBetweenAndUriInNative(LocalDateTime timeStamp, LocalDateTime timeStamp2, List<String> uri);
}
