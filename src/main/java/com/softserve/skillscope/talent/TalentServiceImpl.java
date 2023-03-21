package com.softserve.skillscope.talent;

import com.softserve.skillscope.talent.model.entity.Talent;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TalentServiceImpl implements TalentService {

    @Autowired
    TalentRepository talentRepo;

    @Override
    public List<Talent> showAllTalents(){
        return talentRepo.findAll();
    }

    @Override
    public void createTalent(Talent talent) {
        talentRepo.save(talent);
    }

    @Override
    public void updateTalent(Talent talent) {
        talentRepo.save(talent);
    }

    @Override
    public void deleteTalent(int id) {
        talentRepo.deleteById(id);
    }
}
