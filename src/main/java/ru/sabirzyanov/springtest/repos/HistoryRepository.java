package ru.sabirzyanov.springtest.repos;

import org.springframework.data.repository.CrudRepository;
import ru.sabirzyanov.springtest.domain.History;

import java.util.Date;


public interface HistoryRepository extends CrudRepository<History, Long> {

    Iterable<History> findByUserIdAndDateBetween(Long userId, Date from, Date to);
    Iterable<History> findByAdminIdAndDateBetween(Long adminId, Date from, Date to);
    Iterable<History> findByDateBetween(Date from, Date to);
    Iterable<History> findByUserIdAndAdminIdAndDateBetween(Long userId, Long adminId, Date from, Date to);
}
