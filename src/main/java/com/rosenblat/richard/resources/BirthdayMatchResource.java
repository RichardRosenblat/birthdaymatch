package com.rosenblat.richard.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BirthdayMatchResource {

    @RequestMapping("hello")
    String hello(@RequestParam String name) {
        return "Hello " + name;
    }
}
