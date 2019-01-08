package com.oauth2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login() {
        return "auth Hello!";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register() {
        return "auth register Hello!";
    }

}
