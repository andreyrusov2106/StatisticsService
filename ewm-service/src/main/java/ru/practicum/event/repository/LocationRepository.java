package ru.practicum.event.repository;


import org.springframework.data.repository.PagingAndSortingRepository;
import ru.practicum.event.model.Location;


public interface LocationRepository extends PagingAndSortingRepository<Location, Long> {

}
