package com.softserve.skillscope.general.util.service.impl;

import com.softserve.skillscope.general.handler.exception.generalException.BadRequestException;
import com.softserve.skillscope.general.handler.exception.generalException.UserAlreadyExistsException;
import com.softserve.skillscope.general.handler.exception.generalException.UserNotFoundException;
import com.softserve.skillscope.general.handler.exception.proofException.ProofNotFoundException;
import com.softserve.skillscope.general.handler.exception.skillException.SkillNotFoundException;
import com.softserve.skillscope.general.util.service.UtilService;
import com.softserve.skillscope.kudos.model.enity.Kudos;
import com.softserve.skillscope.kudos.KudosRepository;
import com.softserve.skillscope.proof.ProofRepository;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import com.softserve.skillscope.security.payment.model.enums.OrderStatus;
import com.softserve.skillscope.skill.SkillRepository;
import com.softserve.skillscope.skill.model.dto.SkillWithVerification;
import com.softserve.skillscope.skill.model.entity.Skill;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.request.RegistrationRequest;
import com.softserve.skillscope.user.Role;
import com.softserve.skillscope.user.UserRepository;
import com.softserve.skillscope.user.model.User;
import com.softserve.skillscope.user.model.UserProperties;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UtilServiceImpl implements UtilService {

    private UserRepository userRepo;
    private ProofRepository proofRepo;
    private SkillRepository skillRepo;
    private UserProperties userProps;
    private PasswordEncoder passwordEncoder;
    private KudosRepository kudosRepo;


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
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return !email.equalsIgnoreCase(user.getEmail());
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

    @Override
    public String getRoles(User saveUser) {
        Collection<? extends GrantedAuthority> auth =
                saveUser.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toSet());
        return auth.stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" "));
    }

    @Override
    public Set<String> getRole(Set<Role> roles) {
        return roles.stream().map(Role::getAuthority).collect(Collectors.toSet());
    }

    @Override
    public User createUser(RegistrationRequest request) {
        if (userRepo.existsByEmail(request.email())) {
            throw new UserAlreadyExistsException();
        }
        if (request.roles() == null) {
            throw new BadRequestException("Invalid user role");
        }
        return User.builder()
                .name(request.name())
                .surname(request.surname())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .roles(getRole(request.roles()))
                .build();
    }

    @Override
    public boolean updateTokenActivation(Sponsor sponsor) {
        return sponsor.getOrders().stream()
                .anyMatch(order -> order.getActivation() == OrderStatus.READY_TO_USE);
    }

    @Override
    public Set<Skill> getSkillsByProofId(Long proofId){
        Proof proof = findProofById(proofId);
        return skillRepo.findAllByProofsId(proof.getId());
    }

    @Override
    public Skill findSkillById(Long id) {
        return skillRepo.findById(id)
                .orElseThrow(SkillNotFoundException::new);
    }
    @Override
    public Set<Skill> parseAllSkills(String text) {
        Set<String> skills = Set.of(text.split(","));

        return skills.stream()
                .map(skill -> skillRepo.findByTitleIgnoreCase(skill))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Skill> stringToSkills(Set<String> newSet) {
        return newSet.stream().map(skillRepo::findByTitle).filter(Objects::nonNull).collect(Collectors.toSet());
    }
    @Override
    public void checkIfKudosIsPresent(Integer amount, Sponsor sponsor, Proof proof, Skill skill) {
        Kudos kudos = kudosRepo.findBySponsorAndProofAndSkill(sponsor, proof, skill);
        if (kudos != null) {
            kudos.setAmount(kudos.getAmount() + amount);
            kudos.setKudosDate(LocalDateTime.now());
        } else {
            kudos = Kudos.builder()
                    .sponsor(sponsor)
                    .amount(amount)
                    .kudosDate(LocalDateTime.now())
                    .proof(proof)
                    .skill(skill)
                    .build();
        }
        kudosRepo.save(kudos);
    }
    @Override
    public Set<SkillWithVerification> getSkillsWithVerification(Talent talent) {
        Set<Skill> verifiedSkills = getSkillsFromProofs(talent);
        return talent.getSkills().stream()
                .map(skill -> new SkillWithVerification(skill, verifiedSkills.contains(skill)))
                .collect(Collectors.toSet());
    }

    private Set<Skill> getSkillsFromProofs(Talent talent) {
        return talent.getProofs().stream()
                .flatMap(proof -> proof.getSkills().stream())
                .collect(Collectors.toSet());
    }

    @Override
    public Integer calculateTotalKudosAmount4CurrentUser(Talent talent) {
        return isNotCurrentUser(talent.getUser()) ?
                null :
                talent.getProofs().stream()
                        .filter(proof -> proof.getStatus() == ProofStatus.PUBLISHED)
                        .flatMapToInt(proof -> proof.getKudos().stream().mapToInt(Kudos::getAmount)).sum();
    }

    @Override
    public boolean isCurrentKudos(Kudos kudos, User user){
        return kudos.getSponsor().getId().equals(user.getId());
    }
}
