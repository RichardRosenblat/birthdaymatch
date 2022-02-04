package com.rosenblat.richard.bo;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.rosenblat.richard.dto.DtoConverter;
import com.rosenblat.richard.dto.birthdaymatch.BirthdayMatchResponse;
import com.rosenblat.richard.dto.imdb.getBio.GetBioResponse;
import com.rosenblat.richard.dto.imdb.knownFor.KnownForResponse;
import com.rosenblat.richard.services.BornTodayService;
import com.rosenblat.richard.services.GetBioService;
import com.rosenblat.richard.services.KnownForService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class BirthdayMatchBO {

    @Autowired
    BornTodayService bornTodayService;

    @Autowired
    GetBioService getBioService;

    @Autowired
    KnownForService knownForService;

    public BirthdayMatchResponse matchBirthday(int day, int month) {
        log.info("BirthdayMatch Request recieved, matching born actors of day {}/{}", day, month);

        LocalDate date = LocalDate.of(2020, month, day);

        log.info("Calling born today service");
        List<String> matches = bornTodayService.getBornByDate(date);

        BirthdayMatchResponse response = new BirthdayMatchResponse();
        log.info("Starting matches loop");
        FillResponse(matches, response);

        return response;
    }

    private void FillResponse(List<String> matches, BirthdayMatchResponse response) {
        int i = 0;
        for (String code : matches) {
            if (i >= 5)
                break;

            log.info("{}", code);
            addActorInfo(response, code);
            log.info("");

            i++;
        }
    }

    // This methot only has the default access modifier to allow the @Async
    // annotattion to work
    @Async
    void addActorInfo(BirthdayMatchResponse response, String code) {

        GetBioResponse bio;
        List<KnownForResponse> knownFor;

        try {
            bio = getBioService.getBio(code).get();
            knownFor = knownForService.knownFor(code).get();

        } catch (InterruptedException | ExecutionException e) {
            log.error("Thread error while getting info about actor", e);
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Thread error while getting info about actor", e);
        }

        response.addActorInfo(DtoConverter.getActorInfo(bio, knownFor));

    }

    public List<String> bornToday(int day, int month) {

        LocalDate date = LocalDate.of(2020, month, day);

        return bornTodayService.getBornByDate(date);

    }

    public GetBioResponse getBio(String code) {

        try {
            return getBioService.getBio(code).get();

        } catch (InterruptedException | ExecutionException e) {
            log.error("Thread error while getting Bio", e);
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Thread error while getting Bio", e);
        }

    }

    public List<KnownForResponse> getKnownFor(String code) {

        try {
            return knownForService.knownFor(code).get();

        } catch (InterruptedException | ExecutionException e) {
            log.error("Thread error while getting known for", e);
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Thread error while getting known for", e);
        }
    }
}
