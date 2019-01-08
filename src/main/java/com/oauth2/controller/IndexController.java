package com.oauth2.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class IndexController {

    @RequestMapping(value ="/hello", method = RequestMethod.GET)
    public String sayHello() {
        return "Welcome Oauth2 User!";
    }

}
