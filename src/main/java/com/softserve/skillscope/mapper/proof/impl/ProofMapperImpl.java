package com.softserve.skillscope.mapper.proof.impl;

import com.softserve.skillscope.mapper.proof.ProofMapper;
import com.softserve.skillscope.proof.model.dto.FullProof;
import com.softserve.skillscope.proof.model.dto.GeneralProof;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.proof.model.entity.ProofProperties;
import com.softserve.skillscope.talent.model.entity.Talent;
import org.springframework.stereotype.Component;


@Component
public class ProofMapperImpl implements ProofMapper {
    ProofProperties proofProps;
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
    public GeneralProof toGeneralProof(Proof proof) {
        String description = proof.getDescription();
        String title = proof.getTitle();

        if (proof.getDescription() != null && proof.getDescription().length() > proofProps.descriptionLength())
            description = description.substring(0, proofProps.descriptionLength()).concat("...");
        if (proof.getTitle() != null && proof.getTitle().length() > proofProps.titleLength())
            title = title.substring(0, proofProps.titleLength()).concat("...");

        return GeneralProof.builder()
                .id(proof.getId())
                .publicationDate(proof.getPublicationDate())
                .title(title)
                .description(description)
                .status(proof.getStatus())
                .build();
    }
}
