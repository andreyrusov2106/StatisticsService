package ru.explorewithme.repository;

import com.querydsl.core.types.Projections;
import ru.explorewithme.model.StatResultProjection;
import ru.explorewithme.model.Stats;

import javax.persistence.EntityManager;
import java.time.LocalDateTime;
import java.util.List;


public class StatsRepositoryImpl extends BaseRepositoryImpl<Stats, Integer> implements StatsRepository {
    public StatsRepositoryImpl(EntityManager em) {
        super(Stats.class, em);
    }

    @Override
    public List<StatResultProjection> getAuthorStatistic(LocalDateTime start, LocalDateTime end, List<String> uri, boolean unique) {
        if (unique) {
            if (!uri.isEmpty()) {
                return jpaQueryFactory
                        .selectDistinct(stats.ip, stats.uri, stats.app)
                        .from(stats)
                        .where(stats.uri.in(uri).and(stats.timeStamp.between(start, end)))
                        .groupBy(stats.uri, stats.app)
                        .select(Projections.constructor(StatResultProjection.class, stats.uri, stats.app, stats.count()))
                        .fetch();

            } else {
                return jpaQueryFactory
                        .selectDistinct(stats.ip, stats.uri, stats.app)
                        .from(stats)
                        .where(stats.timeStamp.between(start, end))
                        .groupBy(stats.uri, stats.app)
                        .select(Projections.constructor(StatResultProjection.class, stats.uri, stats.app, stats.count()))
                        .fetch();
            }
        } else {
            if (!uri.isEmpty()) {
                return jpaQueryFactory
                        .from(stats)
                        .where(stats.uri.in(uri).and(stats.timeStamp.between(start, end)))
                        .groupBy(stats.uri, stats.app)
                        .select(Projections.constructor(StatResultProjection.class, stats.uri, stats.app, stats.count()))
                        .fetch();
            } else {
                return jpaQueryFactory
                        .from(stats)
                        .where(stats.timeStamp.between(start, end))
                        .groupBy(stats.uri, stats.app)
                        .select(Projections.constructor(StatResultProjection.class, stats.uri, stats.app, stats.count()))
                        .fetch();
            }
        }
    }
}
