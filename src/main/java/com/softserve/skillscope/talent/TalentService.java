package com.softserve.skillscope.talent;

import com.softserve.skillscope.talent.model.entity.Talent;

import java.util.List;

public interface TalentService {
    List<Talent> showAllTalents();
    void createTalent(Talent talent);
    void updateTalent(Talent talent);
    void deleteTalent(int id);
}
