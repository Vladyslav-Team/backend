package com.softserve.skillscope.user;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    TALENT("ROLE_TALENT"),
    SPONSOR("ROLE_SPONSOR");
    private final String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return this.roleName;
    }

    public String getName() {
        return name();
    }

    public String getRoleName() {
        return roleName;
    }
}
