package com.softserve.skillscope.talent;

import com.softserve.skillscope.mapper.TalentMapper;
import com.softserve.skillscope.talent.model.entity.TalentFlashcard;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TalentServiceImpl implements TalentService {
    TalentRepository talentRepo;
    private final TalentMapper talentMapper;

    public List<TalentFlashcard> showAllTalents(){
        return talentRepo.findAll().stream()
                .map(talentMapper::toTalentFlashcard)
                .toList();
    }
}
