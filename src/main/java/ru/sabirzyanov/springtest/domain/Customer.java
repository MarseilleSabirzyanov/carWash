package ru.sabirzyanov.springtest.domain;

import lombok.*;

import javax.persistence.*;

/**
 * Created by Marselius on 11.12.2018.
 */

@Entity
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    private String firstName;
    private String secondName;
    private String phoneNumber;
    private Long points;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    public Customer() {
    }

    public Customer(String firstName, String secondName, String phoneNumber, Long points, User user) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.phoneNumber = phoneNumber;
        this.points = points;
        this.user = user;
    }

    public String getUserName() {
        return user != null ? user.getUsername() : "<none>";
    }
}
