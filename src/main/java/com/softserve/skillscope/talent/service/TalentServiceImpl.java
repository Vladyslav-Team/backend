package com.softserve.skillscope.talent.service;

import com.softserve.skillscope.config.SecurityConfiguration;
import com.softserve.skillscope.exception.generalException.BadRequestException;
import com.softserve.skillscope.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.exception.generalException.UserNotFoundException;
import com.softserve.skillscope.generalModel.GeneralResponse;
import com.softserve.skillscope.generalModel.UserImageResponse;
import com.softserve.skillscope.mapper.talent.TalentMapper;
import com.softserve.skillscope.talent.TalentRepository;
import com.softserve.skillscope.talent.model.dto.GeneralTalent;
import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.entity.TalentProperties;
import com.softserve.skillscope.talent.model.request.TalentEditRequest;
import com.softserve.skillscope.talent.model.response.GeneralTalentResponse;
import com.softserve.skillscope.user.UserRepository;
import com.softserve.skillscope.user.model.User;
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

    //FIXME @SEM check the code
    @Override
    public GeneralResponse delete(Long talentId) {
        User talent = findUserById(talentId);
        if (securityConfig.isNotCurrentUser(talent)) {
            throw new ForbiddenRequestException();
        }
        userRepo.delete(talent);
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
    public UserImageResponse getTalentImage(Long talentId) {
        return talentMapper.toTalentImage(findTalentById(talentId));
    }

    /*
     * This method checks the field for not null. If in request we didn't get that fields, don't edit them.
     */
    private void checkIfFieldsNotEmpty(TalentEditRequest talentToUpdate, Talent talent) {
        if (talentToUpdate.name() != null)
            talent.getUser().setName(talentToUpdate.name());

        if (talentToUpdate.surname() != null)
            talent.getUser().setName(talentToUpdate.surname());

        if (talentToUpdate.location() != null)
            talent.setLocation(talentToUpdate.location());

        if (talentToUpdate.birthday() != null)
            talent.setBirthday(talentToUpdate.birthday());

        if (talentToUpdate.password() != null) {
            boolean isSamePassword = passwordEncoder.matches(talentToUpdate.password(), talent.getUser().getPassword());
            if (!isSamePassword) {
                talent.getUser().setPassword(passwordEncoder.encode(talentToUpdate.password()));
            }
        }
        if (talentToUpdate.image() != null)
            talent.setImage(talentToUpdate.image());

        if (talentToUpdate.about() != null)
            talent.setAbout(talentToUpdate.about());

        if (talentToUpdate.phone() != null)
            talent.setPhone(talentToUpdate.phone());

        if (talentToUpdate.experience() != null)
            talent.setExperience(talentToUpdate.experience());

        if (talentToUpdate.education() != null)
            talent.setEducation(talentToUpdate.education());
    }

    private Talent findTalentById(Long id) {
        return talentRepo.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    private User findUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }
}
