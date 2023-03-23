package com.softserve.skillscope.exception;

import com.softserve.skillscope.exception.talentException.TalentNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/skillscope")
    public String TestController() {
        return "Hello";
    }
}
