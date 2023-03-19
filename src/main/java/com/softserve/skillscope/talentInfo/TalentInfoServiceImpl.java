package com.softserve.skillscope.talentInfo;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TalentInfoServiceImpl implements TalentInfoService {
    TalentInfoRepository talentInfoRepo;
}
