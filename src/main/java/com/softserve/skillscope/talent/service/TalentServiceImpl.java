package com.softserve.skillscope.talent.service;

import com.softserve.skillscope.amazon_s3.S3Service;
import com.softserve.skillscope.exception.generalException.BadRequestException;
import com.softserve.skillscope.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.exception.generalException.UnauthorizedUserException;
import com.softserve.skillscope.exception.talentException.TalentNotFoundException;
import com.softserve.skillscope.mapper.TalentMapper;
import com.softserve.skillscope.talent.TalentRepository;
import com.softserve.skillscope.talent.model.dto.GeneralTalent;
import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.entity.TalentProperties;
import com.softserve.skillscope.talent.model.request.TalentEditRequest;
import com.softserve.skillscope.talent.model.response.GeneralTalentResponse;
import com.softserve.skillscope.talent.model.response.TalentResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.http.entity.ContentType;
import org.imgscalr.Scalr;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TalentServiceImpl implements TalentService {
    private TalentProperties talentProp;
    private TalentRepository talentRepo;
    private TalentMapper talentMapper;
    private PasswordEncoder passwordEncoder;

    private S3Service s3Service; //todo to replace with interface

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
        if (isNotCurrentTalent(talent)) {
            throw new ForbiddenRequestException();
        }
        talentRepo.delete(talent);
        return new TalentResponse(talentId, "Deleted successfully!");
    }

    @Transactional
    @Override
    public TalentResponse editTalentProfile(Long talentId, TalentEditRequest talentToUpdate) {
        Talent talent = findTalentById(talentId);
        if (isNotCurrentTalent(talent)) {
            throw new ForbiddenRequestException();
        }
        boolean isSamePassword = passwordEncoder.matches(talentToUpdate.password(), talent.getPassword());

        if (!isSamePassword)
            talent.setPassword(passwordEncoder.encode(talentToUpdate.password()));

        talent.setName(talentToUpdate.name());
        talent.setSurname(talentToUpdate.surname());
        talent.getTalentInfo().setLocation(talentToUpdate.location());
        talent.getTalentInfo().setAge(talentToUpdate.birthday());

        checkIfFieldsNotEmpty(talentToUpdate, talent);
        Talent saveTalent = talentRepo.save(talent);

        return new TalentResponse(saveTalent.getId(), "Edited successfully!");
    }

    /*
     * This method checks the field for not null. If in request we didn't get that fields, don't edit them.
     */
    private void checkIfFieldsNotEmpty(TalentEditRequest talentToUpdate, Talent talent) {
        if (!Objects.equals(talent.getTalentInfo().getImage(), talentToUpdate.image()))
            talent.getTalentInfo().setImage(talentToUpdate.image());

        if (talentToUpdate.about() != null)
            talent.getTalentInfo().setAbout(talentToUpdate.about());

        if (talentToUpdate.phone() != null)
            talent.getTalentInfo().setPhone(talentToUpdate.phone());

        if (talentToUpdate.experience() != null)
            talent.getTalentInfo().setExperience(talentToUpdate.experience());

        if (talentToUpdate.education() != null)
            talent.getTalentInfo().setEducation(talentToUpdate.education());
    }

    private Talent findTalentById(Long id) {
        return talentRepo.findById(id)
                .orElseThrow(TalentNotFoundException::new);
    }

    private boolean isNotCurrentTalent(Talent talent) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return !email.equalsIgnoreCase(talent.getEmail());
    }

    @Override
    public void uploadImage(Long talentId, MultipartFile file) {
        Talent talent = findTalentById(talentId);
        if (isNotCurrentTalent(talent)) {
            throw new UnauthorizedUserException();
        }
        isFileEmpty(file);
        isFileImage(file);

        String path = String.format("%s/%s", s3Service.getBucketName(), talentId);
        String filename = String.format("%s-%s", file.getName(), UUID.randomUUID());
        try {
            s3Service.save(path, filename, file.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        editTalentProfile(talentId, new TalentEditRequest(
                talent.getPassword(),
                talent.getName(),
                talent.getSurname(),
                filename,
                talent.getTalentInfo().getExperience(),
                talent.getTalentInfo().getLocation(),
                talent.getTalentInfo().getAbout(),
                talent.getTalentInfo().getEducation(),
                talent.getTalentInfo().getAge(),
                talent.getTalentInfo().getPhone()
                ));
    }

    private void isFileImage(MultipartFile file) {
        if (!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(),
                           ContentType.IMAGE_PNG.getMimeType(),
                           ContentType.IMAGE_GIF.getMimeType())
                .contains(file.getContentType())) {
            throw new IllegalStateException("File must be an image");
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalStateException("Image is empty");
        }
    }
}
