package com.softserve.skillscope.sponsor.service;

import com.softserve.skillscope.general.model.GeneralResponse;
import com.softserve.skillscope.general.model.ImageResponse;
import com.softserve.skillscope.sponsor.model.dto.SponsorProfile;
import com.softserve.skillscope.sponsor.model.request.SponsorEditRequest;
import com.softserve.skillscope.sponsor.model.respone.GeneralSponsorResponse;

public interface SponsorService {
    GeneralSponsorResponse getAllSponsorsByPage(int page);

    SponsorProfile getSponsorProfile(Long sponsorId);

    GeneralResponse deleteSponsor(Long sponsorId);

    GeneralResponse editSponsorProfile(Long sponsorId, SponsorEditRequest sponsorToUpdate);

    ImageResponse getSponsorImage(Long sponsorId);

    GeneralResponse buyKudos(Long sponsorId);
}
