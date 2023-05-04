package com.softserve.skillscope.general.mapper.sponsor;

import com.softserve.skillscope.general.model.ImageResponse;
import com.softserve.skillscope.sponsor.model.dto.GeneralSponsor;
import com.softserve.skillscope.sponsor.model.dto.SponsorProfile;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;

public interface SponsorMapper {
    GeneralSponsor toGeneralSponsor(Sponsor sponsor);
    SponsorProfile toSponsorProfile(Sponsor sponsor);
    ImageResponse toSponsorImage(Sponsor sponsor);
}
