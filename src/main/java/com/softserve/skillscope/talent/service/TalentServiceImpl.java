package com.softserve.skillscope.talent.service;

import com.softserve.skillscope.amazon_s3.S3ServiceImpl;
import com.softserve.skillscope.config.SecurityConfiguration;
import com.softserve.skillscope.exception.generalException.BadRequestException;
import com.softserve.skillscope.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.exception.generalException.S3Exception;
import com.softserve.skillscope.exception.generalException.UnauthorizedUserException;
import com.softserve.skillscope.exception.talentException.TalentNotFoundException;
import com.softserve.skillscope.generalModel.GeneralResponse;
import com.softserve.skillscope.mapper.talent.TalentMapper;
import com.softserve.skillscope.talent.TalentRepository;
import com.softserve.skillscope.talent.model.dto.GeneralTalent;
import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.entity.TalentProperties;
import com.softserve.skillscope.talent.model.request.TalentEditRequest;
import com.softserve.skillscope.talent.model.response.GeneralTalentResponse;
import com.softserve.skillscope.talent.model.response.TalentImageResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.http.entity.ContentType;
import org.imgscalr.Scalr;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TalentServiceImpl implements TalentService {
    private TalentProperties talentProp;
    private TalentRepository talentRepo;
    private TalentMapper talentMapper;
    private PasswordEncoder passwordEncoder;
    private SecurityConfiguration securityConfig;

    private S3ServiceImpl s3ServiceImpl;

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
    public GeneralResponse delete(Long talentId) {
        Talent talent = findTalentById(talentId);
        if (securityConfig.isNotCurrentTalent(talent)) {
            throw new ForbiddenRequestException();
        }
        talentRepo.delete(talent);
        return new GeneralResponse(talentId, "Deleted successfully!");
    }

    @Transactional
    @Override
    public GeneralResponse editTalentProfile(Long talentId, TalentEditRequest talentToUpdate) {
        Talent talent = findTalentById(talentId);
        if (securityConfig.isNotCurrentTalent(talent)) {
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
    public TalentImageResponse getTalentImage(Long talentId) {
        return talentMapper.toTalentImage(findTalentById(talentId));
    }

    /*
     * This method checks the field for not null. If in request we didn't get that fields, don't edit them.
     */
    private void checkIfFieldsNotEmpty(TalentEditRequest talentToUpdate, Talent talent) {
        if (talentToUpdate.name() != null)
            talent.setName(talentToUpdate.name());

        if (talentToUpdate.surname() != null)
            talent.setSurname(talentToUpdate.surname());

        if (talentToUpdate.location() != null)
            talent.getTalentInfo().setLocation(talentToUpdate.location());

        if (talentToUpdate.birthday() != null)
            talent.getTalentInfo().setBirthday(talentToUpdate.birthday());

        if (talentToUpdate.password() != null) {
            boolean isSamePassword = passwordEncoder.matches(talentToUpdate.password(), talent.getPassword());
            if (!isSamePassword) {
                talent.setPassword(passwordEncoder.encode(talentToUpdate.password()));
            }
        }
        if (talentToUpdate.image() != null)
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
    public void uploadTalentProfileImage(Long talentId, MultipartFile file) {
        Talent talent = findTalentById(talentId);
        if (isNotCurrentTalent(talent)) {
            throw new UnauthorizedUserException();
        }
        isFileEmpty(file);
        isFileImage(file);
        //Write this code just like this, because if we add exception to the method signature we should use try-catch
        // block every time when we will use this method, so it is convinient to place try-catch blocks here
        BufferedImage resizedImage = null;
        try {
            resizedImage = resizeImage(ImageIO.read(file.getInputStream()), 300);
        } catch (Exception e) {
            throw new S3Exception();
        }
        File newfile = new File(file.getName());
        try {
            ImageIO.write(resizedImage, "jpg", newfile);
        } catch (IOException e) {
            throw new S3Exception();
        }
        InputStream targetStream = null;
        try {
            targetStream = new FileInputStream(newfile);
        } catch (FileNotFoundException e) {
            throw new S3Exception();
        }

        String path = String.format("%s/%s", s3ServiceImpl.getBucketName(), talentId);
        String filename = String.format("%s-%s", file.getName(), UUID.randomUUID());
        s3ServiceImpl.save(path, filename, targetStream);

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

    @Override
    public List<String> getAllImages() {
        return s3ServiceImpl.listAllFiles();
    }

    @Override
    public byte[] downloadTalentProfileImage(Long talentId) {
        Talent talent = talentRepo.findById(talentId).orElseThrow(TalentNotFoundException::new);
        String path = String.format("%s/%s", talentId, talent.getTalentInfo().getImage());
        return s3ServiceImpl.download(path);
    }

    public void deleteTalentProfileImage(Long talentId) {
        Talent talent = talentRepo.findById(talentId).orElseThrow(TalentNotFoundException::new);
        String path = String.format("%s/%s", talentId, talent.getTalentInfo().getImage());
        s3ServiceImpl.deleteFile(path);
        editTalentProfile(talentId, new TalentEditRequest(
                talent.getPassword(),
                talent.getName(),
                talent.getSurname(),
                "here should be the path of default talent profile image",
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

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth) {
        return Scalr.resize(originalImage, targetWidth);
    }
}
