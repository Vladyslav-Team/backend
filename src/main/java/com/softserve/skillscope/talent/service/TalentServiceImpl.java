package com.softserve.skillscope.talent.service;

import com.softserve.skillscope.exception.generalException.BadRequestException;
import com.softserve.skillscope.mapper.TalentMapper;
import com.softserve.skillscope.talent.TalentRepository;
import com.softserve.skillscope.talent.model.dto.responce.GeneralTalent;
import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.entity.TalentProperties;
import com.softserve.skillscope.talent.service.TalentService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class TalentServiceImpl implements TalentService {
    private TalentProperties talentProp;

    private TalentRepository talentRepo;
    private TalentMapper talentMapper;

    @Override
    public Map<String, Object> getAllTalentsByPage(int page) {
        try {
            Page<Talent> pageTalents = talentRepo.findAll(PageRequest.of(page - 1, talentProp.talentPageSize()));
            int totalPages = pageTalents.getTotalPages();

            if (page > totalPages) {
                throw new BadRequestException("Page index must not be bigger than expected");
            }

            List<GeneralTalent> talentsList = pageTalents.stream()
                    .map(talentMapper::toGeneralTalent)
                    .toList();

            Map<String, Object> response = new HashMap<>();
            response.put("totalItems", pageTalents.getTotalElements());
            response.put("talents", talentsList);
            response.put("totalPages", totalPages);
            response.put("currentPage", page);

            return response;

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
