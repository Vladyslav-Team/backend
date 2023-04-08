package com.softserve.skillscope.talent.controller;

import com.softserve.skillscope.exception.generalException.S3Exception;
import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.request.TalentEditRequest;
import com.softserve.skillscope.talent.model.response.GeneralTalentResponse;
import com.softserve.skillscope.talent.model.response.TalentResponse;
import com.softserve.skillscope.talent.service.TalentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
public class TalentController {
    private TalentService talentService;

    @GetMapping("/talents")
    @ResponseStatus(HttpStatus.OK)
    public GeneralTalentResponse showAllTalents(@RequestParam(defaultValue = "1") int page) {
        return talentService.getAllTalentsByPage(page);
    }

    @GetMapping("/talents/{talent-id}")
    @ResponseStatus(HttpStatus.OK)
    public TalentProfile showTalentProfile(@PathVariable("talent-id") Long talentId) {
        return talentService.getTalentProfile(talentId);
    }

     @DeleteMapping("/talents/{talent-id}")
    @ResponseBody
     TalentResponse delete(@PathVariable("talent-id") Long talentId) {
        return talentService.delete(talentId);
    }

    @PatchMapping("/talents/{talent-id}")
    ResponseEntity<TalentResponse> editTalent(@PathVariable("talent-id") Long talentId,
                                              @RequestBody @Valid TalentEditRequest talentProfile) {
        return ResponseEntity.status(HttpStatus.OK).body(talentService.editTalentProfile(talentId, talentProfile));
    }

    @PostMapping(
            path = "/{talent_id}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void uploadImage(@PathVariable("talent_id") Long talentId, @RequestParam MultipartFile file) {
        try {
            talentService.uploadTalentProfileImage(talentId, file);
        } catch (Exception e) {
            throw new S3Exception();
        }
    }

    @GetMapping(path = "/{talent_id}/image/download")
    public byte[] downloadImage(@PathVariable("talent_id") Long talentId) {
        return talentService.downloadTalentProfileImage(talentId);
    }

    @DeleteMapping("/{talent_id}/image/delete")
    public void deleteTalentProfileImage(@PathVariable("talent_id") Long talentId) {
        talentService.deleteTalentProfileImage(talentId);
    }

    @GetMapping("/images")
    public List<String> getAllFiles() {
        return talentService.getAllImages();
    }
}
