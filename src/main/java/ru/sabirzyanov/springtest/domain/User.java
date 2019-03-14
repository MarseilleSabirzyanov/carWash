package ru.sabirzyanov.springtest.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.Set;

/**
 * Created by Marselius on 12.12.2018.
 */

@Getter
@Setter
@Entity
@Table(name = "usr")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(length = 7)
    @NotBlank(message = "User ID не может быть пустым")
    private String username;

    //@NotBlank(message = "Password cannot be empty")
    private String password;

    @NotBlank(message = "Имя не может быть пустым")
    private String name = "";

    private String phone;

    private String surname = "";

    @Email(message = "Email не корректный")
    @NotBlank(message = "Email не может быть пустым")
    private String email = "";
    private Long score = 0L;
    private boolean active;
    private String activationCode;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    public User() {
    }

    public User(String username, String password, String name, String email, Long score, boolean active, Set<Role> roles, String surname, String phone) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.score = score;
        this.active = active;
        this.roles = roles;
        this.surname = surname;
        this.phone = phone;
    }

    public boolean isAdmin() {
        return roles.contains(Role.ADMIN);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

}
