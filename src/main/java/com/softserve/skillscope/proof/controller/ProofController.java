package com.softserve.skillscope.proof.controller;

import com.softserve.skillscope.proof.service.ProofService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class ProofController {
    private ProofService proofService;
}
