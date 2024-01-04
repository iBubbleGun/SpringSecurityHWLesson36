package edu.hillel.springsecurityhwlesson36.controller;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {

    private static final Logger LOG = LoggerFactory.getLogger(PingController.class);

    @GetMapping("/ping")
    public String ping(@NotNull Authentication authentication) {
        LOG.info("Call ping() method by user {} and returned OK.", authentication.getName());
        return "OK";
    }
}
