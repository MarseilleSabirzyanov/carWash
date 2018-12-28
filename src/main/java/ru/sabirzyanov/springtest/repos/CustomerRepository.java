package ru.sabirzyanov.springtest.repos;

import org.springframework.data.repository.CrudRepository;
import ru.sabirzyanov.springtest.domain.Customer;

import java.util.List;

/**
 * Created by Marselius on 11.12.2018.
 */
public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findByFirstName(String firstName);
}
