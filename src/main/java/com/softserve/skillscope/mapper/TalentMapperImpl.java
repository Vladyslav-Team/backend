package com.softserve.skillscope.mapper;

import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.entity.TalentFlashcard;
import org.springframework.stereotype.Component;

@Component
public class TalentMapperImpl implements TalentMapper {

    @Override
    public TalentFlashcard toTalentFlashcard(Talent talent){
        if (talent == null){
            return null;
        }
        return new TalentFlashcard(talent.getId(),
                talent.getTalentInfo().getImage(),
                talent.getName(),
                talent.getSurname(),
                talent.getTalentInfo().getLocation());
    }
}