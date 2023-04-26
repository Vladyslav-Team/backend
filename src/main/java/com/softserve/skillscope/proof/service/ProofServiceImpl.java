package com.softserve.skillscope.proof.service;

import com.softserve.skillscope.config.SecurityConfiguration;
import com.softserve.skillscope.exception.generalException.BadRequestException;
import com.softserve.skillscope.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.exception.proofException.ProofAlreadyPublishedException;
import com.softserve.skillscope.exception.proofException.ProofHasNullValue;
import com.softserve.skillscope.exception.proofException.ProofNotFoundException;
import com.softserve.skillscope.exception.talentException.TalentNotFoundException;
import com.softserve.skillscope.generalModel.GeneralResponse;
import com.softserve.skillscope.kudos.KudosRepository;
import com.softserve.skillscope.mapper.proof.ProofMapper;
import com.softserve.skillscope.proof.ProofRepository;
import com.softserve.skillscope.proof.model.dto.FullProof;
import com.softserve.skillscope.proof.model.dto.GeneralProof;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.proof.model.entity.ProofProperties;
import com.softserve.skillscope.proof.model.request.ProofRequest;
import com.softserve.skillscope.proof.model.response.GeneralProofResponse;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import com.softserve.skillscope.talent.TalentRepository;
import com.softserve.skillscope.talent.model.entity.Talent;
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

@Service
@AllArgsConstructor
public class ProofServiceImpl implements ProofService {
    private TalentRepository talentRepo;
    private ProofRepository proofRepo;
    private KudosRepository kudosRepo;
    private ProofMapper proofMapper;
    private ProofProperties proofProp;
    private SecurityConfiguration securityConfig;

    @Override
    public FullProof getFullProof(Long proofId) {
        return proofMapper.toFullProof(findProofById(proofId));
    }


    @Override
    public GeneralProofResponse getAllProofByPage(Optional<Long> talentIdWrapper, int page, boolean newest) {
        try {
            Sort sort = newest ? Sort.by(proofProp.sortBy()).descending() : Sort.by(proofProp.sortBy()).ascending();
            Page<Proof> pageProofs;
            PageRequest pageRequest = PageRequest.of(page - 1, proofProp.concreteTalentProofPageSize(), sort);
            if (talentIdWrapper.isEmpty()) {
                pageProofs = proofRepo.findAllVisible(proofProp.visible(),
                        PageRequest.of(page - 1, proofProp.proofPageSize(), sort));
            } else {
                Long talentId = talentIdWrapper.get();
                Talent talent = talentRepo.findById(talentId).orElseThrow(TalentNotFoundException::new);
                if (securityConfig.isNotCurrentUser(talent.getUser())) {
                    pageProofs = proofRepo.findAllVisibleByTalentId(talentIdWrapper.get(),
                            proofProp.visible(), pageRequest);
                } else {
                    pageProofs = proofRepo.findForCurrentTalent(talentIdWrapper.get(), pageRequest);
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

    //TODO @SEM re-write the code for sponsor
//    public GeneralResponse addKudosToProofByTalent(Long proofId) {
//
//        String email = SecurityContextHolder.getContext().getAuthentication().getName();
//        Talent talent = findTalentByEmail(email);
//        Proof proof = findProofById(proofId);
//
//        if (talent.equals(proof.getTalent())) {
//            throw new BadRequestException("Talent cannot like their own post");
//        }
////        if (kudosRepo.findByTalentAndProof(talent, proof).isPresent()) {
////            throw new BadRequestException("Talent has already given kudos to this proof");
////        }
//        Kudos kudos = new Kudos();
////        kudos.setTalent(talent);
//        kudos.setAmount(1);
//        kudos.setKudosDate(LocalDateTime.now());
//        kudos.setProof(proof);
//        kudosRepo.save(kudos);
//
//        return new GeneralResponse(proof.getId(), "Kudos was added successfully!");
//    }

    @Override
    public GeneralResponse addProof(Long talentId, ProofRequest creationRequest) {
        Talent creator = findTalentById(talentId);
        if (securityConfig.isNotCurrentUser(creator.getUser())) {
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
        Proof proof = findProofById(proofId);
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
        Proof proof = findProofById(proofId);
        isNotEmptyOrNull(proof);
        if (proof.getStatus() == ProofStatus.PUBLISHED){
            throw new BadRequestException("Proof already published!");
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
        Proof proof = findProofById(proofId);
        isNotEmptyOrNull(proof);
        if (proof.getStatus() == ProofStatus.HIDDEN){
            throw new BadRequestException("Proof already hidden!");
        }
        if (proof.getStatus() == proofProp.defaultType() || proof.getStatus() == ProofStatus.PUBLISHED) {
            proof.setStatus(ProofStatus.HIDDEN);
        }
        proofRepo.save(proof);
        return new GeneralResponse(proofId, "Proof successfully hidden!");
    }

    private void checkForChanges(ProofRequest proofToUpdate, Proof proof) {
        if (proofToUpdate.title() != null && !proofToUpdate.title().equals(proof.getTitle())) {
            proof.setTitle(proofToUpdate.title());
        }
        if (proofToUpdate.description() != null && !proofToUpdate.description().equals(proof.getDescription())) {
            proof.setDescription(proofToUpdate.description());
        }
    }

    private void checkOwnProofs(Long talentId, Long proofId) {
        Talent talent = findTalentById(talentId);
        Proof proof = findProofById(proofId);
        User user = talent.getUser();

        List<Proof> proofList = proofRepo.findByTalentId(talentId);
        if (securityConfig.isNotCurrentUser(user) || !proofList.contains(proof)) {
            throw new ForbiddenRequestException();
        }
    }

    private Proof findProofById(Long proofId) {
        return proofRepo.findById(proofId)
                .orElseThrow(ProofNotFoundException::new);
    }

//    private Talent findTalentByEmail(String name) {
//        return talentRepo.findByEmail(name)
//                .orElseThrow(TalentNotFoundException::new);
//    }

    private Talent findTalentById(Long id) {
        return talentRepo.findById(id)
                .orElseThrow(TalentNotFoundException::new);
    }

    private void isNotEmptyOrNull(Proof proof) {
        if (!StringUtils.hasText(proof.getTitle()) || !StringUtils.hasText(proof.getDescription())) {
            throw new ProofHasNullValue();
        }
    }
}