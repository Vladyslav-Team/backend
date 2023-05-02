package com.softserve.skillscope.sponsor.controller;

import com.softserve.skillscope.generalModel.GeneralResponse;
import com.softserve.skillscope.generalModel.UserImageResponse;
import com.softserve.skillscope.sponsor.model.dto.SponsorProfile;
import com.softserve.skillscope.sponsor.model.request.SponsorEditRequest;
import com.softserve.skillscope.sponsor.model.respone.GeneralSponsorResponse;
import com.softserve.skillscope.sponsor.service.SponsorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class SponsorController {
    private SponsorService sponsorService;

    @GetMapping("/sponsors")
    @ResponseStatus(HttpStatus.OK)
    public GeneralSponsorResponse showAllSponsors(@RequestParam(defaultValue = "1") int page) {
        return sponsorService.getAllSponsorsByPage(page);
    }

    @GetMapping("/sponsors/{sponsor-id}")
    @ResponseStatus(HttpStatus.OK)
    public SponsorProfile showSponsorProfile(@PathVariable("sponsor-id") Long sponsorId) {
        return sponsorService.getSponsorProfile(sponsorId);
    }

    @DeleteMapping("/sponsors/{sponsor-id}")
    @ResponseBody
    GeneralResponse deleteSponsor(@PathVariable("sponsor-id") Long sponsorId) {
        return sponsorService.deleteSponsor(sponsorId);
    }

    @PatchMapping("/sponsors/{sponsor-id}")
    ResponseEntity<GeneralResponse> editSponsor(@PathVariable("sponsor-id") Long sponsorId,
                                               @RequestBody(required = false) @Valid SponsorEditRequest sponsorProfile) {
        return ResponseEntity.status(HttpStatus.OK).body(sponsorService.editSponsorProfile(sponsorId, sponsorProfile));
    }

    @GetMapping("/sponsor/image/{sponsor-id}")
    @ResponseStatus(HttpStatus.OK)
    public UserImageResponse showSponsorImage(@PathVariable("sponsor-id") Long sponsorId) {
        return sponsorService.getSponsorImage(sponsorId);
    }

    @PostMapping("/sponsors/{sponsor-id}/kudos")
    public ResponseEntity<GeneralResponse> buyKudos(@PathVariable("sponsor-id") Long sponsporId) {
        return ResponseEntity.status(HttpStatus.OK).body(sponsorService.buyKudos(sponsporId));
    }
}
