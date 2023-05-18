package com.softserve.skillscope.sponsor.controller;

import com.softserve.skillscope.general.model.GeneralResponse;
import com.softserve.skillscope.general.model.ImageResponse;
import com.softserve.skillscope.security.payment.model.dto.OrdersResponse;
import com.softserve.skillscope.sponsor.model.dto.SponsorProfile;
import com.softserve.skillscope.sponsor.model.request.SponsorEditRequest;
import com.softserve.skillscope.sponsor.model.respone.GeneralSponsorResponse;
import com.softserve.skillscope.sponsor.service.SponsorService;
import com.softserve.skillscope.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@Tag(name = "Sponsor", description = "API for Sponsor")
@PreAuthorize("hasRole('SPONSOR')")
public class SponsorController {
    private SponsorService sponsorService;
    private UserService userService;

    @GetMapping("/sponsors")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all sponsors")
    public GeneralSponsorResponse showAllSponsors(@RequestParam(defaultValue = "1") int page) {
        return sponsorService.getAllSponsorsByPage(page);
    }

    @GetMapping("/sponsors/{sponsor-id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get sponsor's profile")
    public SponsorProfile showSponsorProfile(@PathVariable("sponsor-id") Long sponsorId) {
        return sponsorService.getSponsorProfile(sponsorId);
    }

    @DeleteMapping("/sponsors/{sponsor-id}")
    @Operation(summary = "Delete sponsor")
    GeneralResponse deleteSponsor(@PathVariable("sponsor-id") Long sponsorId) {
        return userService.deleteUser(sponsorId);
    }

    @PatchMapping("/sponsors/{sponsor-id}")
    @Operation(summary = "Edit sponsor")
    ResponseEntity<GeneralResponse> editSponsor(@PathVariable("sponsor-id") Long sponsorId,
                                                @RequestBody(required = false) @Valid SponsorEditRequest sponsorProfile) {
        return ResponseEntity.status(HttpStatus.OK).body(sponsorService.editSponsorProfile(sponsorId, sponsorProfile));
    }

    @GetMapping("/sponsor/image/{sponsor-id}")
    @Operation(summary = "Get image")
    @ResponseStatus(HttpStatus.OK)
    public ImageResponse showSponsorImage(@PathVariable("sponsor-id") Long sponsorId) {
        return sponsorService.getSponsorImage(sponsorId);
    }

    @PostMapping("/sponsors/{sponsor-id}/kudos")
    @Operation(summary = "Add kudos to the balance")
    public ResponseEntity<GeneralResponse> buyKudos(@PathVariable("sponsor-id") Long sponsorId,
                                                    @RequestParam("amount") int kudosAmount) {
        return ResponseEntity.status(HttpStatus.OK).body(sponsorService.buyKudos(sponsorId, kudosAmount));
    }
    @GetMapping("/sponsors/{sponsor-id}/kudos")
    @Operation(summary = "Check the ability to buy kudos")
    public Boolean canBuyKudos(@PathVariable("sponsor-id") Long sponsorId) {
        return sponsorService.canBuyKudos(sponsorId);
    }
    @GetMapping("/sponsors/{sponsor-id}/orders")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get sponsor's orders")
    public OrdersResponse showAllSponsorsBills(@PathVariable("sponsor-id") Long sponsorId) {
        return sponsorService.getAllOrders(sponsorId);
    }
}
