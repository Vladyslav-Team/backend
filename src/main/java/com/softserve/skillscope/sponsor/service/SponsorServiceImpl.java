package com.softserve.skillscope.sponsor.service;

import com.softserve.skillscope.config.SecurityConfiguration;
import com.softserve.skillscope.exception.generalException.BadRequestException;
import com.softserve.skillscope.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.exception.generalException.UserNotFoundException;
import com.softserve.skillscope.generalModel.GeneralResponse;
import com.softserve.skillscope.generalModel.UserImageResponse;
import com.softserve.skillscope.mapper.sponsor.SponsorMapper;
import com.softserve.skillscope.sponsor.SponsorRepository;
import com.softserve.skillscope.sponsor.model.dto.GeneralSponsor;
import com.softserve.skillscope.sponsor.model.dto.SponsorProfile;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import com.softserve.skillscope.sponsor.model.entity.SponsorProperties;
import com.softserve.skillscope.sponsor.model.request.SponsorEditRequest;
import com.softserve.skillscope.sponsor.model.respone.GeneralSponsorResponse;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SponsorServiceImpl implements SponsorService {
    private SponsorProperties sponsorProp;
    private SponsorRepository sponsorRepo;
    private SponsorMapper sponsorMapper;
    private PasswordEncoder passwordEncoder;
    private SecurityConfiguration securityConfig;

    @Override
    public GeneralSponsorResponse getAllSponsorsByPage(int page) {
        try {
            Page<Sponsor> pageSponsors =
                    sponsorRepo.findAllByOrderByIdDesc(PageRequest.of(page - 1, sponsorProp.sponsorPageSize()));
            int totalPages = pageSponsors.getTotalPages();

            if (page > totalPages) {
                throw new BadRequestException("Page index must not be bigger than expected");
            }

            List<GeneralSponsor> sponsors = new java.util.ArrayList<>(pageSponsors.stream()
                    .map(sponsorMapper::toGeneralSponsor)
                    .toList());

            return GeneralSponsorResponse.builder()
                    .totalItems(pageSponsors.getTotalElements())
                    .totalPage(totalPages)
                    .currentPage(page)
                    .sponsors(sponsors)
                    .build();

        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public SponsorProfile getSponsorProfile(Long sponsorId) {
        return sponsorMapper.toSponsorProfile(findSponsorById(sponsorId));
    }

    @Override
    public GeneralResponse deleteSponsor(Long sponsorId) {
        Sponsor sponsor = findSponsorById(sponsorId);
        if (securityConfig.isNotCurrentSponsor(sponsor)){
            throw new ForbiddenRequestException();
        }
        sponsorRepo.delete(sponsor);
        return new GeneralResponse(sponsorId, "Deleted successfully!");
    }

    @Override
    public UserImageResponse getSponsorImage(Long sponsorId) {
        return sponsorMapper.toSponsorImage(findSponsorById(sponsorId));
    }

    @Transactional
    @Override
    public GeneralResponse editSponsorProfile(Long sponsorId, SponsorEditRequest sponsorToUpdate) {
        Sponsor sponsor = findSponsorById(sponsorId);
        if (securityConfig.isNotCurrentSponsor(sponsor)){
            throw new ForbiddenRequestException();
        }
        checkIfFieldsNotEmpty(sponsorToUpdate, sponsor);
        Sponsor saveSponsor = sponsorRepo.save(sponsor);
        return new GeneralResponse(saveSponsor.getId(), "Edit successfully!");
    }

    private void checkIfFieldsNotEmpty(SponsorEditRequest sponsorToUpdate, Sponsor sponsor) {
        if (sponsorToUpdate.name() != null)
            sponsor.setName(sponsorToUpdate.name());

        if (sponsorToUpdate.surname() != null)
            sponsor.setSurname(sponsorToUpdate.surname());

        if (sponsorToUpdate.location() != null)
            sponsor.getSponsorInfo().setLocation(sponsorToUpdate.location());

        if (sponsorToUpdate.birthday() != null)
            sponsor.getSponsorInfo().setBirthday(sponsorToUpdate.birthday());

        if (sponsorToUpdate.password() != null) {
            boolean isSamePassword = passwordEncoder.matches(sponsorToUpdate.password(), sponsor.getPassword());
            if (!isSamePassword) {
                sponsor.setPassword(passwordEncoder.encode(sponsorToUpdate.password()));
            }
        }
        if (sponsorToUpdate.image() != null)
            sponsor.getSponsorInfo().setImage(sponsorToUpdate.image());

        if (sponsorToUpdate.phone() != null)
            sponsor.getSponsorInfo().setPhone(sponsorToUpdate.phone());
    }

    private Sponsor findSponsorById(Long id) {
        return sponsorRepo.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }
}
