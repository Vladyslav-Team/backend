package com.softserve.skillscope.general.mapper.user;

import com.softserve.skillscope.user.model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserMapper {
    UserDetails toUserDetails(User userEntity);
}
