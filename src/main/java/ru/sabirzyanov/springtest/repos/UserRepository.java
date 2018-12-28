package ru.sabirzyanov.springtest.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.sabirzyanov.springtest.domain.User;


/**
 * Created by Marselius on 12.12.2018.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByActivationCode(String code);
}
