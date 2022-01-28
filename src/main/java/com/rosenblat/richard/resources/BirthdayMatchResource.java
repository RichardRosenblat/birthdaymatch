package com.rosenblat.richard.resources;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BirthdayMatchResource {

    @RequestMapping("hello")
    String hello(@RequestParam String name) {
        log.info("Initiallizing hello to {}", name);
        return "Hello " + name;
    }
}
