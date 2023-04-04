package com.softserve.skillscope.talent.service;

import com.softserve.skillscope.exception.generalException.BadRequestException;
import com.softserve.skillscope.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.exception.talentException.TalentNotFoundException;
import com.softserve.skillscope.mapper.TalentMapper;
import com.softserve.skillscope.talent.TalentRepository;
import com.softserve.skillscope.talent.model.dto.GeneralTalent;
import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.entity.TalentProperties;
import com.softserve.skillscope.talent.model.response.GeneralTalentResponse;
import com.softserve.skillscope.talent.model.response.TalentResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TalentServiceImpl implements TalentService {
    private TalentProperties talentProp;

    private TalentRepository talentRepo;
    private TalentMapper talentMapper;

    @Override
    public GeneralTalentResponse getAllTalentsByPage(int page) {
        try {
            Page<Talent> pageTalents =
                    talentRepo.findAllByOrderByIdDesc(PageRequest.of(page - 1, talentProp.talentPageSize()));
            int totalPages = pageTalents.getTotalPages();

            if (page > totalPages) {
                throw new BadRequestException("Page index must not be bigger than expected");
            }

            List<GeneralTalent> talents = new java.util.ArrayList<>(pageTalents.stream()
                    .map(talentMapper::toGeneralTalent)
                    .toList());

            return GeneralTalentResponse.builder()
                    .totalItems(pageTalents.getTotalElements())
                    .totalPage(totalPages)
                    .currentPage(page)
                    .talents(talents)
                    .build();

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }


    @Override
    public TalentProfile getTalentProfile(Long talentId) {
        return talentMapper.toTalentProfile(findTalentById(talentId));
    }
    
    @Override
    public TalentResponse delete(Long talentId) {
        Talent talent = findTalentById(talentId);
        if (!isCurrentTalent(talent)){
            throw new ForbiddenRequestException();
        }
        talentRepo.delete(talent);
        return new TalentResponse(talentId, "Deleted successfully!");
    }

    private Talent findTalentById(Long id) {
        return talentRepo.findById(id)
                .orElseThrow(TalentNotFoundException::new);
    }

    private boolean isCurrentTalent(Talent talent) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return email.equalsIgnoreCase(talent.getEmail());
    }
}
