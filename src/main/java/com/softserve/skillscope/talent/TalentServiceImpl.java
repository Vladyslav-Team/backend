package com.softserve.skillscope.talent;

import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.entity.TalentFlashcard;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TalentServiceImpl implements TalentService {

    @Autowired
    TalentRepository talentRepo;

    @Override
    public List<TalentFlashcard> showAllTalents() {
        return talentRepo.findAll().stream()
                .map(talent -> new TalentFlashcard(
                        talent.getId(),
                        talent.getTalentInfo().getImage(),
                        talent.getName(),
                        talent.getSurname(),
                        talent.getTalentInfo().getLocation()))
                .collect(Collectors.toList());
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
    public void deleteTalent(Long id) {
        talentRepo.deleteById(id);
    }
}
