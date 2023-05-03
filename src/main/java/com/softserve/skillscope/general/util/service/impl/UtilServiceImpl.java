package com.softserve.skillscope.general.util.service.impl;

import com.softserve.skillscope.general.handler.exception.generalException.UserNotFoundException;
import com.softserve.skillscope.general.handler.exception.proofException.ProofNotFoundException;
import com.softserve.skillscope.general.util.service.UtilService;
import com.softserve.skillscope.proof.ProofRepository;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import com.softserve.skillscope.sponsor.model.request.SponsorEditRequest;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;
import com.softserve.skillscope.user.UserRepository;
import com.softserve.skillscope.user.model.User;
import com.softserve.skillscope.user.model.UserProperties;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UtilServiceImpl implements UtilService {

    private UserRepository userRepo;
    private ProofRepository proofRepo;
    private UserProperties userProps;

    @Override
    public User findUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User findUserByEmail(String name) {
        return userRepo.findByEmail(name)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return email.equals("anonymousUser") ? null : findUserByEmail(email);
    }

    @Override
    public boolean isNotCurrentUser(User user) {
        return !getCurrentUser().equals(user.getEmail());
    }

    @Override
    public Proof findProofById(Long proofId) {
        return proofRepo.findById(proofId)
                .orElseThrow(ProofNotFoundException::new);
    }

    @Override
    public String checkEmptyUserImage(RegistrationRequest request) {
        return request.image() == null
                ? userProps.defaultImage() : request.image();
    }

    @Override
    public String validateField(String requestField, String field) {
        return requestField == null ? field : requestField;
    }
}
