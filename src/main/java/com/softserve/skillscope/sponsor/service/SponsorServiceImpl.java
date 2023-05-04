package com.softserve.skillscope.sponsor.service;

import com.softserve.skillscope.general.handler.exception.generalException.BadRequestException;
import com.softserve.skillscope.general.handler.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.general.handler.exception.generalException.UserNotFoundException;
import com.softserve.skillscope.general.mapper.sponsor.SponsorMapper;
import com.softserve.skillscope.general.model.GeneralResponse;
import com.softserve.skillscope.general.model.ImageResponse;
import com.softserve.skillscope.general.util.service.UtilService;
import com.softserve.skillscope.sponsor.SponsorRepository;
import com.softserve.skillscope.sponsor.model.dto.GeneralSponsor;
import com.softserve.skillscope.sponsor.model.dto.SponsorProfile;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import com.softserve.skillscope.sponsor.model.request.SponsorEditRequest;
import com.softserve.skillscope.sponsor.model.respone.GeneralSponsorResponse;
import com.softserve.skillscope.user.model.User;
import com.softserve.skillscope.user.model.UserProperties;
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
public class SponsorServiceImpl implements SponsorService {
    private UserProperties userProp;
    private SponsorRepository sponsorRepo;
    private SponsorMapper sponsorMapper;
    private PasswordEncoder passwordEncoder;
    private UtilService utilService;


    @Override
    public GeneralSponsorResponse getAllSponsorsByPage(int page) {
        try {
            Page<Sponsor> pageSponsors =
                    sponsorRepo.findAllByOrderByIdDesc(PageRequest.of(page - 1, userProp.userPageSize()));
            if (pageSponsors.isEmpty()) throw new UserNotFoundException();

            int totalPages = pageSponsors.getTotalPages();

            if (page > totalPages) {
                throw new BadRequestException("Page index must not be bigger than expected");
            }

            List<GeneralSponsor> sponsors = new ArrayList<>(pageSponsors.stream()
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
        User sponsor = utilService.findUserById(sponsorId);
        if (utilService.isNotCurrentUser(sponsor)) {
            throw new ForbiddenRequestException();
        }
        return sponsorMapper.toSponsorProfile(sponsor.getSponsor());
    }

    @Override
    public ImageResponse getSponsorImage(Long sponsorId) {
        return sponsorMapper.toSponsorImage(utilService.findUserById(sponsorId).getSponsor());
    }

    @Transactional
    @Override
    public GeneralResponse editSponsorProfile(Long sponsorId, SponsorEditRequest sponsorToUpdate) {
        Sponsor sponsor = utilService.findUserById(sponsorId).getSponsor();
        if (utilService.isNotCurrentUser(sponsor.getUser())) {
            throw new ForbiddenRequestException();
        }
        checkIfFieldsNotEmpty(sponsorToUpdate, sponsor);
        Sponsor saveSponsor = sponsorRepo.save(sponsor);
        return new GeneralResponse(saveSponsor.getId(), "Edit successfully!");
    }

    //FIXME by @PanfiDen: change a check
    @Override
    public GeneralResponse buyKudos(Long sponsorId) {
        Sponsor sponsor = utilService.findUserById(sponsorId).getSponsor();
        if (sponsor == null){
            throw new BadRequestException("test");
        }
        if (utilService.isNotCurrentUser(sponsor.getUser())) {
            throw new ForbiddenRequestException();
        }
        sponsor.setBalance(sponsor.getBalance() + 1);
        sponsorRepo.save(sponsor);
        return new GeneralResponse(sponsorId, "Kudos has been purchased successfully!");
    }

    private void checkIfFieldsNotEmpty(SponsorEditRequest sponsorToUpdate, Sponsor sponsor) {
        if (sponsorToUpdate == null) {
            throw new BadRequestException("No changes were applied");
        }
        sponsor.getUser().setName(utilService.validateField(sponsorToUpdate.name(), sponsor.getUser().getName()));
        sponsor.getUser().setSurname(utilService.validateField(sponsorToUpdate.surname(), sponsor.getUser().getSurname()));
        sponsor.setLocation(utilService.validateField(sponsorToUpdate.location(), sponsor.getLocation()));
        sponsor.setBirthday(sponsorToUpdate.birthday() != null ? sponsorToUpdate.birthday() : sponsor.getBirthday());
        if (sponsorToUpdate.password() != null) {
            sponsor.getUser().setPassword(
                    passwordEncoder.matches(sponsorToUpdate.password(), sponsor.getUser().getPassword())
                            ? sponsor.getUser().getPassword()
                            : passwordEncoder.encode(sponsorToUpdate.password())
            );
        }
        sponsor.setImage(utilService.validateField(sponsorToUpdate.image(), sponsor.getImage()));
        sponsor.setPhone(utilService.validateField(sponsorToUpdate.phone(), sponsor.getPhone()));
    }
}
