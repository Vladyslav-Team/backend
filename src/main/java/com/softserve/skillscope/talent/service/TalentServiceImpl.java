package com.softserve.skillscope.talent.service;

import com.softserve.skillscope.sercurity.config.SecurityConfiguration;
import com.softserve.skillscope.general.handler.exception.generalException.BadRequestException;
import com.softserve.skillscope.general.handler.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.general.handler.exception.generalException.UserNotFoundException;
import com.softserve.skillscope.general.model.GeneralResponse;
import com.softserve.skillscope.general.model.ImageResponse;
import com.softserve.skillscope.general.mapper.talent.TalentMapper;
import com.softserve.skillscope.talent.TalentRepository;
import com.softserve.skillscope.talent.model.dto.GeneralTalent;
import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.entity.TalentProperties;
import com.softserve.skillscope.talent.model.request.TalentEditRequest;
import com.softserve.skillscope.talent.model.response.GeneralTalentResponse;
import com.softserve.skillscope.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TalentServiceImpl implements TalentService {
    private TalentProperties talentProp;
    private TalentRepository talentRepo;
    private UserRepository userRepo;
    private TalentMapper talentMapper;
    private PasswordEncoder passwordEncoder;
    private SecurityConfiguration securityConfig;

    @Override
    public GeneralTalentResponse getAllTalentsByPage(int page) {
        try {
            Page<Talent> pageTalents =
                    talentRepo.findAllByOrderByIdDesc(PageRequest.of(page - 1, talentProp.talentPageSize()));
            if (pageTalents.isEmpty()) throw new UserNotFoundException();

            int totalPages = pageTalents.getTotalPages();

            if (page > totalPages) {
                throw new BadRequestException("Page index must not be bigger than expected");
            }

            List<GeneralTalent> talents = new ArrayList<>(pageTalents.stream()
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
    public GeneralResponse delete(Long talentId) {
        Talent talent = findTalentById(talentId);
        if (securityConfig.isNotCurrentUser(talent.getUser())) {
            throw new ForbiddenRequestException();
        }
        talentRepo.delete(talent);
        return new GeneralResponse(talentId, "Deleted successfully!");
    }

    @Transactional
    @Override
    public GeneralResponse editTalentProfile(Long talentId, TalentEditRequest talentToUpdate) {
        Talent talent = findTalentById(talentId);
        if (securityConfig.isNotCurrentUser(talent.getUser())) {
            throw new ForbiddenRequestException();
        }
        checkIfFieldsNotEmpty(talentToUpdate, talent);
        Talent saveTalent = talentRepo.save(talent);

        return new GeneralResponse(saveTalent.getId(), "Edited successfully!");
    }

    /*
     * This method returns String with talent's image, so frontend can draw the avatar.
     * Check if it's the image of own talent, in another case forbidden to get it.
     */
    @Override
    public ImageResponse getTalentImage(Long talentId) {
        return talentMapper.toTalentImage(findTalentById(talentId));
    }

    private String validateField(String requestField, String field) {
        return requestField == null ? field : requestField;
    }

    /*
     * This method checks the field for not null. If in request we didn't get that fields, don't edit them.
     */
    private void checkIfFieldsNotEmpty(TalentEditRequest talentToUpdate, Talent talent) {
        talent.getUser().setName(validateField(talentToUpdate.name(), talent.getUser().getName()));
        talent.getUser().setSurname(validateField(talentToUpdate.surname(), talent.getUser().getSurname()));
        talent.setLocation(validateField(talentToUpdate.location(), talent.getLocation()));
        talent.setBirthday(talentToUpdate.birthday() != null ? talentToUpdate.birthday() : talent.getBirthday());

        if (talentToUpdate.password() != null) {
            talent.getUser().setPassword(
                    passwordEncoder.matches(talentToUpdate.password(), talent.getUser().getPassword())
                            ? talent.getUser().getPassword()
                            : passwordEncoder.encode(talentToUpdate.password())
            );
        }
        talent.setImage(validateField(talentToUpdate.image(), talent.getImage()));
        talent.setAbout(validateField(talentToUpdate.about(), talent.getAbout()));
        talent.setPhone(validateField(talentToUpdate.phone(), talent.getPhone()));
        talent.setExperience(validateField(talentToUpdate.experience(), talent.getExperience()));
        talent.setEducation(validateField(talentToUpdate.education(), talent.getEducation()));
    }
    private Talent findTalentById(Long id) {
        return talentRepo.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }
}
