package com.softserve.skillscope.proof.service;

import com.softserve.skillscope.config.SecurityConfiguration;
import com.softserve.skillscope.exception.generalException.BadRequestException;
import com.softserve.skillscope.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.exception.proofException.ProofAlreadyPublished;
import com.softserve.skillscope.exception.proofException.ProofNotFoundException;
import com.softserve.skillscope.exception.talentException.TalentNotFoundException;
import com.softserve.skillscope.generalModel.generalResponse.GeneralResponse;
import com.softserve.skillscope.mapper.proof.ProofMapper;
import com.softserve.skillscope.proof.ProofRepository;
import com.softserve.skillscope.proof.model.ProofEditRequest;
import com.softserve.skillscope.proof.model.dto.FullProof;
import com.softserve.skillscope.proof.model.dto.GeneralProof;
import com.softserve.skillscope.proof.model.dto.ProofCreationDto;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.proof.model.entity.ProofProperties;
import com.softserve.skillscope.proof.model.response.GeneralProofResponse;
import com.softserve.skillscope.talent.TalentRepository;
import com.softserve.skillscope.talent.model.entity.Talent;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProofServiceImpl implements ProofService {
    private TalentRepository talentRepo;
    private ProofRepository proofRepo;
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
            if (talentIdWrapper.isEmpty()) {
                pageProofs = proofRepo.findAllVisible(proofProp.visible(), PageRequest.of(page - 1, proofProp.proofPageSize(), sort));
            }
            else {
                if (!talentRepo.existsById(talentIdWrapper.get())) {
                    throw new TalentNotFoundException();
                }
                Talent talent = talentIdWrapper.map(talentRepo::findById).orElse(null).get();
                if (!securityConfig.isNotCurrentTalent(talent)) {
                    pageProofs = proofRepo.findForCurrentTalent(talentIdWrapper.get(), PageRequest.of(page - 1, proofProp.concreteTalentProofPageSize(), sort));
                }else {
                    pageProofs = proofRepo.findAllVisibleByTalentId(talentIdWrapper.get(), proofProp.visible(), PageRequest.of(page - 1, proofProp.concreteTalentProofPageSize(), sort));
                }
            }
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
        }
        catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public GeneralResponse addProof(Long talentId, ProofCreationDto creationRequest) {
        Talent creator = findTalentById(talentId);
        if (securityConfig.isNotCurrentTalent(creator)) {
            throw new ForbiddenRequestException();
        }
        LocalDate date = LocalDate.now();
        Proof proof = Proof.builder()
                .publicationDate(date)
                .talent(creator)
                .title(creationRequest.title())
                .description(creationRequest.description())
                .status(proofProp.defaultType())
                .build();
        proofRepo.save(proof);
        return new GeneralResponse(talentId, "Created successfully!");
    }
    
     @Override
    public GeneralResponse deleteProofById(Long talentId, Long proofId) {
        Talent sender = findTalentById(talentId);
        findProofById(proofId);
        if (securityConfig.isNotCurrentTalent(sender))
            throw new ForbiddenRequestException();
        proofRepo.deleteById(proofId);
        return new GeneralResponse(proofId, "Successfully deleted");
    }

    @Transactional
    @Override
    public GeneralResponse editProof(Long talentId, Long proofId, ProofEditRequest proofToUpdate) {
        Talent talent = findTalentById(talentId);
        Proof proof = findProofById(proofId);
        if (securityConfig.isNotCurrentTalent(talent)) {
            throw new ForbiddenRequestException();
        }
        if (proof.getStatus() != proofProp.defaultType()){
            throw new ProofAlreadyPublished();
        }
        checkForChanges(proofToUpdate, proof);

        proofRepo.save(proof);

        return new GeneralResponse(proofId, "Edited successfully!");
    }

    private void checkForChanges(ProofEditRequest proofToUpdate, Proof proof){
        if (proofToUpdate.title() != null && !proofToUpdate.title().equals(proof.getTitle())) {
            proof.setTitle(proofToUpdate.title());
            proof.setPublicationDate(LocalDate.now());
        }
        if (proofToUpdate.description() != null && !proofToUpdate.description().equals(proof.getDescription())) {
            proof.setDescription(proofToUpdate.description());
            proof.setPublicationDate(LocalDate.now());
        }
    }

    private Proof findProofById(Long proofId) {
        return proofRepo.findById(proofId)
                .orElseThrow(ProofNotFoundException::new);
    }
    private Talent findTalentById(Long id) {
        return talentRepo.findById(id)
                .orElseThrow(TalentNotFoundException::new);
    }
}