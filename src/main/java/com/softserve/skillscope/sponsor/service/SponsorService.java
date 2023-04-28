package com.softserve.skillscope.sponsor.service;

import com.softserve.skillscope.generalModel.GeneralResponse;
import com.softserve.skillscope.generalModel.UserImageResponse;
import com.softserve.skillscope.sponsor.model.dto.SponsorProfile;
import com.softserve.skillscope.sponsor.model.request.SponsorEditRequest;
import com.softserve.skillscope.sponsor.model.respone.GeneralSponsorResponse;

public interface SponsorService {
    GeneralSponsorResponse getAllSponsorsByPage(int page);
    SponsorProfile getSponsorProfile(Long sponsorId);
    GeneralResponse deleteSponsor(Long sponsorId);
    GeneralResponse editSponsorProfile(Long sponsorId, SponsorEditRequest sponsorToUpdate);
    UserImageResponse getSponsorImage(Long sponsorId);
    GeneralResponse buyKudos(Long sponsorId);
}
