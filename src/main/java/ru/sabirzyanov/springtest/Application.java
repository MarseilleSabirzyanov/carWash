package ru.sabirzyanov.springtest;

/**
 * Created by Marselius on 11.12.2018.
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

//TODO история операций пользователя в профиле
//TODO поиск юзера: сообщение об ошибках(нет такого пользователя)
//TODO поиск истории: сообщение об ошибках(нет такого юзера или админа)
//TODO поменять id пользователя на число из 9 цифр
//TODO история и список юзеров на несколько страниц


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
