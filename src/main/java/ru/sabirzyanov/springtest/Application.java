package ru.sabirzyanov.springtest;

/**
 * Created by Marselius on 11.12.2018.
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

//TODO сохранение изменений каждой операции в истории
//TODO изменение данных юзера
//TODO поменять id пользователя на число из 9 цифр


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
