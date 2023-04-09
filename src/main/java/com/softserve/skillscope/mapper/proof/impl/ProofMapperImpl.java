package com.softserve.skillscope.mapper.proof.impl;

import com.softserve.skillscope.mapper.proof.ProofMapper;
import com.softserve.skillscope.proof.model.dto.FullProof;
import com.softserve.skillscope.proof.model.dto.GeneralProof;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.talent.model.entity.Talent;
import org.springframework.stereotype.Component;

@Component
public class ProofMapperImpl implements ProofMapper {
    @Override
    public FullProof toFullProof(Proof proof) {
        Talent talent = proof.getTalent();
        return FullProof.builder()
                .id(proof.getId())
                .talentId(talent.getId())
                .talentName(talent.getName())
                .talentSurname(talent.getSurname())
                .publicationDate(proof.getPublicationDate())
                .title(proof.getTitle())
                .description(proof.getDescription())
                .status(proof.getStatus())
                .build();
    }
    @Override
    public GeneralProof toGeneralProof(Proof proof){
        return GeneralProof.builder()
                .id(proof.getId())
                .publicationDate(proof.getPublicationDate())
                .title(proof.getTitle())
                .build();
    }
}
