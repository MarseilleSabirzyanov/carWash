package ru.sabirzyanov.springtest.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import ru.sabirzyanov.springtest.domain.History;

import java.util.Date;


public interface HistoryRepository extends CrudRepository<History, Long> {

    Page<History> findAll(Pageable pageable);
    Page<History> findByUserIdAndDateBetween(Long userId, Date from, Date to, Pageable pageable);
    Page<History> findByAdminIdAndDateBetween(Long adminId, Date from, Date to, Pageable pageable);
    Page<History> findByDateBetween(Date from, Date to, Pageable pageable);
    Page<History> findByUserIdAndAdminIdAndDateBetween(Long userId, Long adminId, Date from, Date to, Pageable pageable);
}
