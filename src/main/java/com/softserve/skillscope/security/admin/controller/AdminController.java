package com.softserve.skillscope.security.admin.controller;

import com.softserve.skillscope.general.model.GeneralResponse;
import com.softserve.skillscope.security.admin.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
@Tag(name = "Admin", description = "API for Admin")
public class AdminController {
    private AdminService adminService;

    @PostMapping("/create")
    @Operation(summary = "Create admin")
    public GeneralResponse createAdmin(HttpServletRequest request) {
        return adminService.createAdmin(request);
    }

    @PostMapping("/create/skill")
    @Operation(summary = "Create Skill")
    public GeneralResponse createSkill(@RequestParam("title") String title) {
        return adminService.createSkillToDb(title);
    }

    @PostMapping("/update/skill/{skill-id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete Skill")
    public GeneralResponse deleteSkill(@PathVariable("skill-id") Long skillId,
                                       @RequestParam("title") String title) {
        return adminService.updateSkill(skillId, title);
    }

    @PostMapping("/registration/lock")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Lock the registration for new users.")
    public GeneralResponse lockRegistration() {
        return adminService.lockRegistration();
    }

    @PostMapping("/registration/unlock")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Unlock the registration for new users.")
    public GeneralResponse unlockRegistration() {
        return adminService.unlockRegistration();
    }

    @DeleteMapping("/delete/user/{user-id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete user.")
    public GeneralResponse deleteUser(@PathVariable("user-id") Long userId) {
        return adminService.deleteUser(userId);
    }

    @DeleteMapping("/delete/proof/{proof-id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete Proof.")
    public GeneralResponse deleteProof(@PathVariable("proof-id") Long proofId) {
        return adminService.deleteProof(proofId);
    }

    @DeleteMapping("/delete/skill/{skill-id}")
    @Operation(summary = "Delete Skill.")
    @PreAuthorize("hasRole('ADMIN')")
    public GeneralResponse deleteSkill(@PathVariable("skill-id") Long skillId) {
        return adminService.deleteSkill(skillId);
    }
}
