package ru.explorewithme.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import ru.explorewithme.model.QStats;

import javax.persistence.EntityManager;

public abstract class BaseRepositoryImpl<T, Id> extends SimpleJpaRepository<T, Id> implements BaseRepository<T, Id> {

    EntityManager em;
    JPAQueryFactory jpaQueryFactory;
    protected final QStats stats = QStats.stats;

    public BaseRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.em = em;
        this.jpaQueryFactory = new JPAQueryFactory(em);
    }

}
