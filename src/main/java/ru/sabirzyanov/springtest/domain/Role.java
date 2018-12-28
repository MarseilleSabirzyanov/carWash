package ru.sabirzyanov.springtest.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by Marselius on 12.12.2018.
 */
public enum Role implements GrantedAuthority {
    USER,
    ADMIN;


    @Override
    public String getAuthority() {
        return name();
    }
}
