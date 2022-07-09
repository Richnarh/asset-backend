package com.khoders.asset.services.auth;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.khoders.asset.entities.auth.UserAccount;
import com.khoders.resource.utilities.SystemUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private Collection<? extends GrantedAuthority> authorities;
    private String id;
    private String emailAddress;
    @JsonIgnore
    private String password;

    public UserDetailsImpl(String id, String emailAddress, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.emailAddress = emailAddress;
        this.password = password;
        this.authorities = authorities;


    }

    public static UserDetailsImpl build(UserAccount user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
                .collect(Collectors.toList());

        System.out.println("authorities --- "+ SystemUtils.KJson().toJson(authorities));
        System.out.println("password p --- "+ user.getPassword());
        UserDetailsImpl userDetails = new UserDetailsImpl(user.getId(),user.getEmailAddress(),user.getPassword(),authorities);
        System.out.println("userDetails --- "+ SystemUtils.KJson().toJson(userDetails));
        System.out.println("Authorities --- "+userDetails.authorities);
        return userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
