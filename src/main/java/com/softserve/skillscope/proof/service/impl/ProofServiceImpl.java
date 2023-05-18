package com.softserve.skillscope.proof.service.impl;

import com.softserve.skillscope.general.handler.exception.generalException.BadRequestException;
import com.softserve.skillscope.general.handler.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.general.handler.exception.generalException.UserNotFoundException;
import com.softserve.skillscope.general.handler.exception.proofException.ProofAlreadyPublishedException;
import com.softserve.skillscope.general.handler.exception.proofException.ProofHasNullValue;
import com.softserve.skillscope.general.handler.exception.proofException.ProofNotFoundException;
import com.softserve.skillscope.general.handler.exception.skillException.SkillNotFoundException;
import com.softserve.skillscope.general.mapper.proof.ProofMapper;
import com.softserve.skillscope.general.model.GeneralResponse;
import com.softserve.skillscope.general.util.service.UtilService;
import com.softserve.skillscope.kudos.KudosRepository;
import com.softserve.skillscope.kudos.model.enity.Kudos;
import com.softserve.skillscope.kudos.model.request.KudosAmountRequest;
import com.softserve.skillscope.kudos.model.response.KudosResponse;
import com.softserve.skillscope.proof.ProofRepository;
import com.softserve.skillscope.proof.model.dto.FullProof;
import com.softserve.skillscope.proof.model.dto.GeneralProof;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.proof.model.entity.ProofProperties;
import com.softserve.skillscope.proof.model.request.ProofRequest;
import com.softserve.skillscope.proof.model.response.GeneralProofResponse;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import com.softserve.skillscope.proof.service.ProofService;
import com.softserve.skillscope.skill.model.entity.Skill;
import com.softserve.skillscope.skill.model.request.AddSkillsRequest;
import com.softserve.skillscope.skill.model.response.SkillResponse;
import com.softserve.skillscope.sponsor.SponsorRepository;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.user.Role;
import com.softserve.skillscope.user.UserRepository;
import com.softserve.skillscope.user.model.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.flywaydb.core.internal.util.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@AllArgsConstructor
public class ProofServiceImpl implements ProofService {
    private SponsorRepository sponsorRepo;
    private ProofRepository proofRepo;
    private KudosRepository kudosRepo;
    private UserRepository userRepo;
    private ProofMapper proofMapper;
    private ProofProperties proofProp;
    private UtilService utilService;

    @Override
    public FullProof getFullProof(Long proofId) {
        return proofMapper.toFullProof(utilService.findProofById(proofId));
    }


    @Override
    public GeneralProofResponse getAllProofByPage(Optional<Long> userIdWrapper, int page, boolean newest) {
        try {
            Sort sort = newest ? Sort.by(proofProp.sortBy()).descending() : Sort.by(proofProp.sortBy()).ascending();
            Page<Proof> pageProofs;
            PageRequest pageRequest = PageRequest.of(page - 1, proofProp.concreteUserProofPageSize(), sort);

            if (userIdWrapper.isEmpty()) {
                pageProofs = proofRepo.findAllVisible(proofProp.visible(),
                        PageRequest.of(page - 1, proofProp.proofPageSize(), sort));
            } else {
                Long talentId = userIdWrapper.get();
                User user = userRepo.findById(talentId).orElseThrow(UserNotFoundException::new);
                if (user.getRoles().contains(Role.SPONSOR.getAuthority())) {
                    pageProofs = proofRepo.findAllKudosedBySponsorId(userIdWrapper.get(),
                            proofProp.visible(), pageRequest);
                } else if (utilService.isNotCurrentUser(user)) {
                    pageProofs = proofRepo.findAllVisibleByTalentId(userIdWrapper.get(),
                            proofProp.visible(), pageRequest);
                } else {
                    pageProofs = proofRepo.findForCurrentTalent(userIdWrapper.get(), pageRequest);
                }
            }
            if (pageProofs.isEmpty()) throw new ProofNotFoundException("No proofs was found");

            int totalPages = pageProofs.getTotalPages();


            if (page > totalPages) {
                throw new BadRequestException("Page index must not be bigger than expected");
            }

            List<GeneralProof> proofs = pageProofs.stream()
                    .map(proofMapper::toGeneralProof)
                    .toList();

            return GeneralProofResponse.builder()
                    .totalItems(pageProofs.getTotalElements())
                    .totalPage(totalPages)
                    .currentPage(page)
                    .proofs(proofs)
                    .build();

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public GeneralResponse addKudosToProofBySponsor(Long proofId, KudosAmountRequest kudosAmountRequest) {
        if (kudosAmountRequest == null || kudosAmountRequest.amount() < 1) {
            throw new BadRequestException("Amount of Kudos must not be less than 1!");
        }
        Integer amount = kudosAmountRequest.amount();
        Sponsor sponsor = utilService.getCurrentUser().getSponsor();
        if (sponsor.getBalance() < amount) {
            throw new BadRequestException("Not enough kudos on the balance sheet");
        }
        Proof proof = utilService.findProofById(proofId);
        Kudos kudos = Kudos.builder()
                .sponsor(sponsor)
                .amount(amount)
                .kudosDate(LocalDateTime.now())
                .proof(proof)
                .build();
        sponsor.setBalance(sponsor.getBalance() - amount);
        kudosRepo.save(kudos);
        sponsorRepo.save(sponsor);
        return new GeneralResponse(proof.getId(), amount + " kudos was added successfully!");
    }

    @Override
    public KudosResponse showAmountKudosOfProof(Long proofId) {
        Proof proof = utilService.findProofById(proofId);
        User user = utilService.getCurrentUser();
        int totalKudos = proof.getKudos().stream()
                .mapToInt(Kudos::getAmount)
                .sum();

        int currentUserKudos = proof.getKudos().stream()
                .filter(kudos -> kudos.getSponsor() != null)
                .filter(kudos -> user != null && kudos.getSponsor().getId().equals(user.getId()))
                .mapToInt(Kudos::getAmount)
                .sum();
        return new KudosResponse(proofId, isClicked(proofId), totalKudos, currentUserKudos);
    }
    
    @Override
    public GeneralResponse addProof(Long talentId, ProofRequest creationRequest) {
        Talent creator = utilService.findUserById(talentId).getTalent();
        if (utilService.isNotCurrentUser(creator.getUser())) {
            throw new ForbiddenRequestException();
        }
        Proof proof = Proof.builder()
                .publicationDate(null)
                .talent(creator)
                .title(creationRequest.title())
                .description(creationRequest.description())
                .status(proofProp.defaultType())
                .build();

        proofRepo.save(proof);
        return new GeneralResponse(proof.getId(), "Created successfully!");
    }

    @Override
    public GeneralResponse deleteProofById(Long talentId, Long proofId) {
        checkOwnProofs(talentId, proofId);
        proofRepo.deleteById(proofId);
        return new GeneralResponse(proofId, "Successfully deleted");
    }

    @Transactional
    @Override
    public GeneralResponse editProofById(Long talentId, Long proofId, ProofRequest proofToUpdate) {
        Proof proof = utilService.findProofById(proofId);
        checkOwnProofs(talentId, proofId);
        if (proof.getStatus() != proofProp.defaultType()) {
            throw new ProofAlreadyPublishedException();
        }
        checkForChanges(proofToUpdate, proof);

        proofRepo.save(proof);

        return new GeneralResponse(proofId, "Edited successfully!");
    }

    @Override
    public GeneralResponse publishProofById(Long talentId, Long proofId) {
        checkOwnProofs(talentId, proofId);
        Proof proof = utilService.findProofById(proofId);
        isNotEmptyOrNull(proof);
        if (proof.getStatus() == ProofStatus.PUBLISHED) {
            throw new BadRequestException("Proof has already been published!");
        }
        if (proof.getStatus() == ProofStatus.HIDDEN || proof.getStatus() == proofProp.defaultType()) {
            proof.setStatus(ProofStatus.PUBLISHED);
            if (proof.getPublicationDate() == null) {
                proof.setPublicationDate(LocalDateTime.now());
            }
        }
        proofRepo.save(proof);
        return new GeneralResponse(proofId, "Proof successfully published!");
    }

    @Override
    public GeneralResponse hideProofById(Long talentId, Long proofId) {
        checkOwnProofs(talentId, proofId);
        Proof proof = utilService.findProofById(proofId);
        isNotEmptyOrNull(proof);
        if (proof.getStatus() == ProofStatus.HIDDEN) {
            throw new BadRequestException("Proof has already been hidden!");
        }
        if (proof.getStatus() == proofProp.defaultType() || proof.getStatus() == ProofStatus.PUBLISHED) {
            proof.setStatus(ProofStatus.HIDDEN);
        }
        proofRepo.save(proof);
        return new GeneralResponse(proofId, "Proof successfully hidden!");
    }

    @Override
        public SkillResponse getAllSkillByProof(Long proofId){
        Set<Skill> skills = utilService.getSkillsByProofId(proofId);
        if (skills.size() < 1){
            throw new BadRequestException("Any skills not found");
        }
        return new SkillResponse(proofId, skills);
    }

    @Override
    public GeneralResponse addSkillsOnProof(Long talentId, Long proofId, AddSkillsRequest newSkillsRequest) {
        if (newSkillsRequest == null || newSkillsRequest.skills() == null) {
            throw new BadRequestException("No skills were applied");
        }
        checkOwnProofs(talentId, proofId);
        Proof proof = utilService.findProofById(proofId);
        if (proof.getStatus() != proofProp.defaultType()) {
            throw new ProofAlreadyPublishedException();
        } else if (proof.getSkills().size() >= 4){
            throw new BadRequestException("Proof cannot contain more than 4 Skills");
        }
        Set<Skill> newSkills = utilService.stringToSkills(newSkillsRequest.skills());
        proof.getSkills().addAll(newSkills);
        proof.setSkills(proof.getSkills());
        proofRepo.save(proof);

        return new GeneralResponse(proofId, "Skills successfully added!");
    }

    @Override
    public GeneralResponse deleteSkillFromProof(Long talentId, Long proofId, Long skillId) {
        Skill skill = utilService.findSkillById(skillId);
        checkOwnProofs(talentId, proofId);
        Proof proof = utilService.findProofById(proofId);
        if (proof.getStatus() != proofProp.defaultType()) {
            throw new ProofAlreadyPublishedException();
        } else if (proof.getSkills().size() < 1){
            throw new BadRequestException("Proof cannot contain less than 0 Skills");
        }
        if (!proof.getSkills().contains(skill)){
            throw new SkillNotFoundException();
        }
        proof.getSkills().remove(skill);
        proofRepo.save(proof);
        return new GeneralResponse(proofId, "Skill " + skill.getTitle() + " successfully deleted!");
    }


    private void checkForChanges(ProofRequest proofToUpdate, Proof proof) {
        if (proofToUpdate == null) {
            throw new BadRequestException("No changes were applied");
        }
        if (proofToUpdate.title() != null && !proofToUpdate.title().equals(proof.getTitle())) {
            proof.setTitle(proofToUpdate.title());
        }
        if (proofToUpdate.description() != null && !proofToUpdate.description().equals(proof.getDescription())) {
            proof.setDescription(proofToUpdate.description());
        }
    }

    private void checkOwnProofs(Long talentId, Long proofId) {
        Talent talent = utilService.findUserById(talentId).getTalent();
        Proof proof = utilService.findProofById(proofId);
        User user = talent.getUser();

        List<Proof> proofList = proofRepo.findByTalentId(talentId);
        if (utilService.isNotCurrentUser(user) || !proofList.contains(proof)) {
            throw new ForbiddenRequestException();
        }
    }

    private void isNotEmptyOrNull(Proof proof) {
        if (!StringUtils.hasText(proof.getTitle()) || !StringUtils.hasText(proof.getDescription())) {
            throw new ProofHasNullValue();
        }
    }

    private boolean isClicked(Long proofId) {
        User user = utilService.getCurrentUser();
        if (user == null || user.getRoles().contains(Role.TALENT.getAuthority())) {
            return false;
        }
        Proof proof = utilService.findProofById(proofId);
        return !kudosRepo.findBySponsorAndProof(user.getSponsor(), proof).isEmpty();
    }
}