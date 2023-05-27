package com.softserve.skillscope.security.admin.controller;

import com.softserve.skillscope.general.model.GeneralResponse;
import com.softserve.skillscope.security.admin.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {
    private AdminService adminService;

    @PostMapping("/create")
    public GeneralResponse createAdmin() {
        return adminService.createAdmin();
    }

    @PostMapping("/registration/lock")
    @PreAuthorize("hasRole('ADMIN')")
    public GeneralResponse lockRegistration() {
        return adminService.lockRegistration();
    }

    @PostMapping("/registration/unlock")
    @PreAuthorize("hasRole('ADMIN')")
    public GeneralResponse unlockRegistration() {
        return adminService.unlockRegistration();
    }

    @DeleteMapping("/delete/user/{user-id}")
    @PreAuthorize("hasRole('ADMIN')")
    public GeneralResponse deleteUser(@PathVariable("user-id") Long userId) {
        return adminService.deleteUser(userId);
    }

    @DeleteMapping("/delete/proof/{proof-id}")
    @PreAuthorize("hasRole('ADMIN')")
    public GeneralResponse deleteProof(@PathVariable("proof-id") Long proofId) {
        return adminService.deleteProof(proofId);
    }
}
