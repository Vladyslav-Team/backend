package com.softserve.skillscope.mapper;

import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.entity.TalentFlashcard;
import org.mapstruct.Mapper;
@Mapper
public interface TalentMapper {
    TalentFlashcard toTalentFlashcard(Talent talent);
}
