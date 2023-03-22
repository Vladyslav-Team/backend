package com.softserve.skillscope.talent;

import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.entity.TalentFlashcard;

import java.util.List;

public interface TalentService {
    List<TalentFlashcard> showAllTalents();
    void createTalent(Talent talent);
    void updateTalent(Talent talent);
    void deleteTalent(Long id);
}
