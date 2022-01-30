package com.rosenblat.richard.resources;

import java.time.LocalDate;
import java.util.List;

import com.rosenblat.richard.dto.imdb.GetBioResponse;
import com.rosenblat.richard.services.BornTodayService;
import com.rosenblat.richard.services.GetBioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class BirthdayMatchResource {

    @Autowired
    BornTodayService bornTodayService;

    @Autowired
    GetBioService GetBioService;

    @GetMapping("hello")
    String hello(@RequestParam String name) {
        log.info("Initiallizing hello to {}", name);
        return "Hello " + name;
    }

    @GetMapping("born")
    List<String> bornToday(@RequestParam int day,@RequestParam int month) {
        log.info("BornToday Request recieved, checking born actors of day {}/{}", day,month);
        LocalDate date = LocalDate.of(2020, month, day);
        return bornTodayService.getBornByDate(date);
    }

    @GetMapping("bio")
    GetBioResponse getBio(@RequestParam String code) {
        log.info("Request recieved, getting bio of actor belonging to the code: {}", code);
        return GetBioService.getBio(code);
    }
}
