package com.softserve.skillscope.security.admin.service.impl;

import com.softserve.skillscope.general.handler.exception.generalException.BadRequestException;
import com.softserve.skillscope.general.handler.exception.generalException.UserNotFoundException;
import com.softserve.skillscope.general.model.GeneralResponse;
import com.softserve.skillscope.general.util.service.UtilService;
import com.softserve.skillscope.proof.ProofRepository;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.security.admin.model.enums.AdminStatus;
import com.softserve.skillscope.security.admin.service.AdminService;
import com.softserve.skillscope.security.config.PaypalConfiguration;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;
import com.softserve.skillscope.user.Role;
import com.softserve.skillscope.user.UserRepository;
import com.softserve.skillscope.user.model.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private UtilService utilService;
    private UserRepository userRepo;
    private ProofRepository proofRepo;
    private PaypalConfiguration userProps;

    @Override
    public GeneralResponse createAdmin(HttpServletRequest request) {
        if (!userRepo.existsByRoles(Role.ADMIN.getAuthority())) {
            RegistrationRequest registrationRequest
                    = new RegistrationRequest(request.getRemoteAddr(), "Admin", "admin@gmail.com", userProps.clientSecret(),
                    "Secret", "https://i.imgur.com/mtcTT14.jpg", LocalDate.now(), Set.of(Role.ADMIN));
            User user = utilService.buildUser(registrationRequest);
            userRepo.save(user);
            return new GeneralResponse(0L, "Admin was created successfully!");
        } else throw new BadRequestException("https://rb.gy/dk0vu");
    }

    @Override
    public GeneralResponse deleteUser(Long userId) {
        User user = utilService.findUserById(userId);
        userRepo.delete(user);
        return new GeneralResponse(userId, "User was deleted successfully!");
    }

    @Override
    public GeneralResponse deleteProof(Long proofId) {
        Proof proof = utilService.findProofById(proofId);
        proofRepo.delete(proof);
        return new GeneralResponse(proofId, "Proof was deleted successfully!");
    }

    @Override
    public GeneralResponse lockRegistration() {
        User user = getUser();
        user.setSurname(AdminStatus.LOCKED.name());
        userRepo.save(user);
        return new GeneralResponse(user.getId(), "Registration was locked successfully!");
    }

    @Override
    public GeneralResponse unlockRegistration() {
        User user = getUser();
        user.setSurname(AdminStatus.UNLOCKED.name());
        userRepo.save(user);
        return new GeneralResponse(user.getId(), "Registration was unlocked successfully!");
    }

    @Override
    public void checkIfRegistrationIsLocked() {
        User user = getUser();
        if (user != null && user.getSurname().equalsIgnoreCase(AdminStatus.LOCKED.name())) {
            throw new BadRequestException("The registration is currently unavailable. Try again later.");
        }
    }

    private User getUser() {
        return userRepo.findByRoles(Role.ADMIN.getAuthority()).orElseThrow(UserNotFoundException::new);
    }
}
