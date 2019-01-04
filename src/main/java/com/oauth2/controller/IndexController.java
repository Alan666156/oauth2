package com.oauth2.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class IndexController {

    @RequestMapping(value ="/hello", method = RequestMethod.GET)
    public String sayHello() {
        return "Hello Oauth2 User!";
    }

}
