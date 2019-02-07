package ru.sabirzyanov.springtest.repos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.sabirzyanov.springtest.domain.User;


/**
 * Created by Marselius on 12.12.2018.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    Page<User> findAll(Pageable pageable);
    User findByUsername(String username);
    User findByEmail(String email);
    User findByActivationCode(String code);
}
