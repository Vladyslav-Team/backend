package com.softserve.skillscope.talent.model.entity;

import com.softserve.skillscope.talentInfo.model.entity.TalentInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class TalentFlashcard {

    private final Long id;
    private final String name;
    private final String surname;
    private final String image;
    private final String location;


}
