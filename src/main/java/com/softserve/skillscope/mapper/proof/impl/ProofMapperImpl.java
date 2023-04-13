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
        String description;
        String title;
        if (proof.getDescription() == null || proof.getDescription().length() <= 200)
            description = proof.getDescription();
        else
            description = proof.getDescription().substring(0,200).concat("...");
        if (proof.getTitle() == null || proof.getTitle().length() <= 20)
            title = proof.getTitle();
        else
            title = proof.getTitle().substring(0,20).concat("...");
        return GeneralProof.builder()
                .id(proof.getId())
                .publicationDate(proof.getPublicationDate())
                .title(title)
                .description(description)
                .status(proof.getStatus())
                .build();
    }
}
