package com.softserve.skillscope.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    TALENT,
    SPONSOR;
    private final String roleName = "ROLE_" + name();

    @Override
    public String getAuthority() {
        return this.roleName;
    }
}
