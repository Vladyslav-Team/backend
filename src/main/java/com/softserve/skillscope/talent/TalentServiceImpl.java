package com.softserve.skillscope.talent;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TalentServiceImpl implements TalentService {
    TalentRepository talentRepo;
}
