package com.rosenblat.richard.bo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.rosenblat.richard.dto.birthdaymatch.BirthdayMatchResponse;
import com.rosenblat.richard.dto.imdb.GetBioResponse;
import com.rosenblat.richard.services.BornTodayService;
import com.rosenblat.richard.services.GetBioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BirthdayMatchBO {

    @Autowired
    BornTodayService bornTodayService;

    @Autowired
    GetBioService getBioService;

    public BirthdayMatchResponse matchBirthday(int day, int month){
        log.info("BirthdayMatch Request recieved, matching born actors of day {}/{}", day, month);
        
        LocalDate date = LocalDate.of(2020, month, day);
        
        log.info("Calling born today service");
        List<String> matches = bornTodayService.getBornByDate(date);
        
        BirthdayMatchResponse response = new BirthdayMatchResponse();
        
        log.info("Starting matches loop");
        int i = 0; // TODO DEBUG LIMITOR REMOVE LATER
        for (String code : matches) {
            if (i>=3) break; // TODO DEBUG LIMITOR REMOVE LATER
            i++; // TODO DEBUG LIMITOR REMOVE LATER
            
            log.info("------------------------{}------------------------------", code);
            
            log.info("Getting bio of match {}", code);
            GetBioResponse match = getBioService.getBio(code);
            response.addActor(match);
            
            log.info("------------------------------------------------------");
        }
        return response;
    }

    public List<String> bornToday(int day, int month) {
        log.info("BornToday Request recieved, checking born actors of day {}/{}", day,month);
        LocalDate date = LocalDate.of(2020, month, day);
        return bornTodayService.getBornByDate(date);
    }

    public GetBioResponse getBio(String code) {
        log.info("Request recieved, getting bio of actor belonging to the code: {}", code);
        return getBioService.getBio(code);
    }
}
