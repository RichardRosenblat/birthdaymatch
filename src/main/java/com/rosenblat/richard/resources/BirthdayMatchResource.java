package com.rosenblat.richard.resources;

import java.util.List;

import com.rosenblat.richard.bo.BirthdayMatchBO;
import com.rosenblat.richard.dto.birthdaymatch.BirthdayMatchResponse;
import com.rosenblat.richard.dto.imdb.GetBioResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BirthdayMatchResource {

    @Autowired
    BirthdayMatchBO bo;

    @GetMapping("hello")
    public String hello(@RequestParam String name) {
        log.info("Initiallizing hello to {}", name);
        return "Hello " + name;
    }
   
    @GetMapping("match")
    public BirthdayMatchResponse matchBirthday(@RequestParam int day, @RequestParam int month) {
        return bo.matchBirthday(day, month);
    }

    @GetMapping("born")
    public List<String> bornToday(@RequestParam int day, @RequestParam int month) {
        return bo.bornToday(day, month);
    }
    
    @GetMapping("bio")
    public GetBioResponse getBio(@RequestParam String code) {
        return bo.getBio(code);
    }
}
