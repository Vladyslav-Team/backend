package com.softserve.skillscope.proof.service;

import com.softserve.skillscope.exception.proofException.ProofNotFoundException;
import com.softserve.skillscope.mapper.proof.ProofMapper;
import com.softserve.skillscope.proof.ProofRepository;
import com.softserve.skillscope.proof.model.dto.FullProof;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.proof.model.response.ProofStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ProofServiceImpl implements ProofService {
    private ProofRepository proofRepo;
    private ProofMapper proofMapper;

    @Override
    public FullProof getFullProof(Long proofId) {
        return proofMapper.toFullProof(findProofById(proofId));
    }

    @Override
    public GeneralProofResponse getAllProofByPage(int page, boolean newest) {
        try {
            Sort sort = newest ? Sort.by(proofProp.sortBy()).descending() : Sort.by(proofProp.sortBy()).ascending();

            Page<Proof> pageProofs = proofRepo.findAll(PageRequest.of(page - 1, proofProp.proofPageSize(), sort));
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