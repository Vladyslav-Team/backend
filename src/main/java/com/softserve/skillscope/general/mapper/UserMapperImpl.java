package com.softserve.skillscope.general.mapper;

import com.softserve.skillscope.user.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import static org.springframework.security.core.userdetails.User.withUsername;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDetails toUserDetails(User userEntity) {
        return withUsername(userEntity.getEmail())
                .password(userEntity.getPassword())
                .authorities(userEntity.getRoles().stream().map(SimpleGrantedAuthority::new).toList())
                .build();
    }
}
