package com.softserve.skillscope.security.admin.service.impl;

import com.softserve.skillscope.general.handler.exception.generalException.ConflictException;
import com.softserve.skillscope.general.handler.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.general.model.GeneralResponse;
import com.softserve.skillscope.general.util.service.UtilService;
import com.softserve.skillscope.proof.ProofRepository;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.security.admin.model.enums.AdminStatus;
import com.softserve.skillscope.security.admin.service.AdminService;
import com.softserve.skillscope.security.config.PaypalConfiguration;
import com.softserve.skillscope.skill.SkillRepository;
import com.softserve.skillscope.skill.model.entity.Skill;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;
import com.softserve.skillscope.user.Role;
import com.softserve.skillscope.user.UserRepository;
import com.softserve.skillscope.user.model.User;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private UtilService utilService;
    private UserRepository userRepo;
    private ProofRepository proofRepo;
    private PaypalConfiguration userProps;
    private SkillRepository skillRepo;

    @Override
    public GeneralResponse createAdmin(HttpServletRequest request) {
        if (!userRepo.existsByRoles(Role.ADMIN.getAuthority())) {
            RegistrationRequest registrationRequest
                    = new RegistrationRequest(request.getRemoteAddr(),
                    AdminStatus.UNLOCKED.name(), "admin@gmail.com", userProps.clientSecret(),
                    "Secret", "https://i.imgur.com/mtcTT14.jpg", LocalDate.now(), Set.of(Role.ADMIN));
            User user = utilService.buildUser(registrationRequest);
            userRepo.save(user);
            return new GeneralResponse(0L, "Admin was created successfully!");
        } else throw new ForbiddenRequestException("https://rb.gy/dk0vu");
    }

    @Override
    public GeneralResponse createSkillToDb(String text) {
        Optional<Skill> existingSkill = skillRepo.findByTitleIgnoreCase(text);
        if (existingSkill.isPresent()) {
            throw new ConflictException("Skill with title '" + text + "' already exists");
        }
        Skill skill = Skill.builder().title(text).build();
        skillRepo.save(skill);
        return new GeneralResponse(skill.getId(), "Skill was added successfully!");
    }

    @Override
    public GeneralResponse deleteUser(Long userId) {
        User user = utilService.findUserById(userId);
        if (!utilService.isNotCurrentUser(user)) {
            throw new ConflictException("You can't delete Admin");
        }
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
    public GeneralResponse updateSkill(Long skillId, String text) {
        Skill skill = utilService.findSkillById(skillId);
        skill.setTitle(text);
        skillRepo.save(skill);
        return new GeneralResponse(skillId, "Skill was updated successfully!");
    }

    @Override
    public GeneralResponse deleteSkill(Long skillId) {
        Skill skill = utilService.findSkillById(skillId);
        if (isSkillUsed(skill)) {
            throw new ConflictException("You can't delete this skill since it's used it talents or proofs");
        }
        skillRepo.delete(skill);
        return new GeneralResponse(skillId, "Skill was deleted successfully!");
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
            throw new ForbiddenRequestException("The registration is currently unavailable. Try again later.");
        }
    }

    private boolean isSkillUsed(Skill skill) {
        return !skill.getProofs().isEmpty() || !skill.getTalent().isEmpty();
    }

    private User getUser() {
        return userRepo.findByRoles(Role.ADMIN.getAuthority()).orElse(null);
    }
}
