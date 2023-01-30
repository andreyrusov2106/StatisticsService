package explorewithme.repository;

import explorewithme.model.StatResultProjection;
import explorewithme.model.Stats;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface StatsRepository extends PagingAndSortingRepository<Stats, Long> {
    @Query(value = "Select sel1.uri, sel1.app, count(*) From (SELECT Distinct(s1.ip), s1.uri,s1.app " +
            "FROM  statistic s1 where s1.uri in(:uri) and s1.created between :start and :end) sel1 \n" +
            "GROUP BY sel1.uri, sel1.app", nativeQuery = true)
    List<StatResultProjection> findDistinctByIpAndTimeStampBetweenAndUriInNative(LocalDateTime start, LocalDateTime end, List<String> uri);

    @Query(value = "Select sel1.uri, sel1.app, count(*) From (SELECT Distinct(s1.ip), s1.uri,s1.app " +
            "FROM  statistic s1 where s1.created between :start and :end) sel1 \n" +
            "GROUP BY sel1.uri, sel1.app", nativeQuery = true)
    List<StatResultProjection> findDistinctByIpAndTimeStampBetweenAndNative(LocalDateTime start, LocalDateTime end);

    @Query(value = "Select uri, app, count(*) " +
            "FROM  statistic  where created between :start and :end \n" +
            "GROUP BY uri, app", nativeQuery = true)
    List<StatResultProjection> findTimeStampBetweenAndNative(LocalDateTime start, LocalDateTime end);

    @Query(value = "Select uri, app, count(*)  " +
            "FROM  statistic where uri in(:uri) and created between :start and :end\n" +
            "GROUP BY uri, app", nativeQuery = true)
    List<StatResultProjection> findTimeStampBetweenAndUriInNative(LocalDateTime start, LocalDateTime end, List<String> uri);
}
