package com.softserve.skillscope.mapper.sponsor;

import com.softserve.skillscope.generalModel.UserImageResponse;
import com.softserve.skillscope.sponsor.model.dto.GeneralSponsor;
import com.softserve.skillscope.sponsor.model.dto.SponsorProfile;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;

public interface SponsorMapper {
    GeneralSponsor toGeneralSponsor(Sponsor sponsor);
    SponsorProfile toSponsorProfile(Sponsor sponsor);
    UserImageResponse toSponsorImage(Sponsor sponsor);
}
