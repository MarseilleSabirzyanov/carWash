package ru.sabirzyanov.springtest.repos;

import org.springframework.data.repository.CrudRepository;
import ru.sabirzyanov.springtest.domain.History;

import java.util.List;

public interface HistoryRepository extends CrudRepository<History, Long> {
    List<History> findByTotal(Long total);
}
