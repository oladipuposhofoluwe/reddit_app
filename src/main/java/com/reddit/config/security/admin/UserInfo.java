package com.reddit.config.security.admin;

import com.reddit.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;

public class UserInfo implements UserDetails {

    private final User user;

    public UserInfo(User user) {
        this.user = user;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return this.getUserAuthorities();
//    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthorities("USER");
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
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
        return true;
    }


//    private List<GrantedAuthority> getAuthorities(Collection<String> privileges) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        privileges.forEach((privilege) -> {
//            authorities.add(new SimpleGrantedAuthority(privilege));
//        });
//        return authorities;
//    }


    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
        return singletonList(new SimpleGrantedAuthority(role));
    }


    @Override
    public String toString() {
        System.out.println("noowoo");
        return "UserInfo{" +
                "userName=" + this.getUsername() +
                ",isEnabled=" + this.isEnabled() +
                "user=" + user +
                '}';
    }

    public User getUser(){
        return user;
    }
}