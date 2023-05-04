package com.softserve.skillscope.user.service.impl;

import com.softserve.skillscope.general.handler.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.general.model.GeneralResponse;
import com.softserve.skillscope.general.util.service.UtilService;
import com.softserve.skillscope.user.UserRepository;
import com.softserve.skillscope.user.model.User;
import com.softserve.skillscope.user.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private UserRepository userRepo;
    private UtilService utilService;

    @Override
    public GeneralResponse deleteUser(Long userId) {
        User user = utilService.findUserById(userId);
        if (utilService.isNotCurrentUser(user)) {
            throw new ForbiddenRequestException();
        }
        userRepo.delete(user);
        return new GeneralResponse(userId, "Deleted successfully!");
    }
}
