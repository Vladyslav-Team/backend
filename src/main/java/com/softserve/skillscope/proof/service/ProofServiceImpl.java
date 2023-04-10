package com.softserve.skillscope.proof.service;

import com.softserve.skillscope.exception.generalException.BadRequestException;
import com.softserve.skillscope.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.exception.proofException.ProofNotFoundException;
import com.softserve.skillscope.exception.talentException.TalentNotFoundException;
import com.softserve.skillscope.mapper.proof.ProofMapper;
import com.softserve.skillscope.proof.ProofRepository;
import com.softserve.skillscope.proof.model.dto.FullProof;
import com.softserve.skillscope.proof.model.dto.GeneralProof;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.proof.model.entity.ProofProperties;
import com.softserve.skillscope.proof.model.response.GeneralProofResponse;
import com.softserve.skillscope.proof.model.response.ProofResponse;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import com.softserve.skillscope.talent.TalentRepository;
import com.softserve.skillscope.talent.model.entity.Talent;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProofServiceImpl implements ProofService {
    private TalentRepository talentRepo;
    private ProofRepository proofRepo;
    private ProofMapper proofMapper;
    private ProofProperties proofProp;

    @Override
    public FullProof getFullProof(Long proofId) {
        return proofMapper.toFullProof(findProofById(proofId));
    }

    @Override
    public GeneralProofResponse getAllProofByPage(Optional<Long> talentIdWrapper, int page, boolean newest) {

        try {
            Sort sort = newest ? Sort.by(proofProp.sortBy()).descending() : Sort.by(proofProp.sortBy()).ascending();

            Page<Proof> pageProofs = null;
            if (talentIdWrapper.isEmpty()) {
                pageProofs = proofRepo.findAll(PageRequest.of(page - 1, proofProp.proofPageSize(), sort));
            }
            else {
                if (!talentRepo.existsById(talentIdWrapper.get())) {
                    throw new TalentNotFoundException();
                }
                pageProofs = proofRepo.findByTalent_Id(talentIdWrapper.get() ,PageRequest.of(page - 1, proofProp.concreteTalentProofPageSize(), sort));
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
    public ProofResponse deleteProofById(Long talentId, Long proofId) {
        Talent sender = talentRepo.findById(talentId).orElseThrow(TalentNotFoundException::new);
        if (isNotCurrentTalent(sender))
            throw new ForbiddenRequestException();
        if (!sender.getProofs().stream().map(Proof::getId).toList().contains(proofId))
            throw new ProofNotFoundException();
        proofRepo.deleteById(proofId);
        return new ProofResponse(proofId, "Successfully deleted");
    }

    private boolean isNotCurrentTalent(Talent talent) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return !email.equalsIgnoreCase(talent.getEmail());
    }

    public ProofStatus setProofStatus(ProofStatus status) {
        for (ProofStatus validProofStatus : ProofStatus.values()) {
            if (validProofStatus == status) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + status);
    }

    private Proof findProofById(Long proofId) {
        return proofRepo.findById(proofId)
                .orElseThrow(ProofNotFoundException::new);
    }
}