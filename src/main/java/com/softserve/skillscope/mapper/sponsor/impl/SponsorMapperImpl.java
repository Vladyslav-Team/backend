package com.softserve.skillscope.mapper.sponsor.impl;

import com.softserve.skillscope.generalModel.UserImageResponse;
import com.softserve.skillscope.mapper.sponsor.SponsorMapper;
import com.softserve.skillscope.sponsor.model.dto.GeneralSponsor;
import com.softserve.skillscope.sponsor.model.dto.SponsorProfile;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class SponsorMapperImpl implements SponsorMapper {
    @Override
    public GeneralSponsor toGeneralSponsor(Sponsor sponsor) {
        return GeneralSponsor.builder()
                .id(sponsor.getId())
                .image(sponsor.getSponsorInfo().getImage())
                .name(sponsor.getName())
                .surname(sponsor.getSurname())
                .location(sponsor.getSponsorInfo().getLocation())
                .build();
    }

    @Override
    public SponsorProfile toSponsorProfile(Sponsor sponsor) {
        return SponsorProfile.builder()
                .id(sponsor.getId())
                .image(sponsor.getSponsorInfo().getImage())
                .name(sponsor.getName())
                .surname(sponsor.getSurname())
                .location(sponsor.getSponsorInfo().getLocation())
                .age(sponsor.getSponsorInfo().getBirthday() != null ? Period.between(sponsor.getSponsorInfo().getBirthday(), LocalDate.now()).getYears() : 0)
                .email(sponsor.getEmail())
                .phone(sponsor.getSponsorInfo().getPhone())
                .build();
    }

    @Override
    public UserImageResponse toSponsorImage(Sponsor sponsor) {
        return new UserImageResponse(sponsor.getSponsorInfo().getImage());
    }
}
