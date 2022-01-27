package com.rosenblat.richard.services;

import java.time.LocalDate;
import java.util.List;

// import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class BornTodayService {
    // @Value("${URL.IMDB}")
    private String imdbURL = "https://imdb8.p.rapidapi.com";

    public List<String> getBornByDate(LocalDate date) {
        Integer day = date.getDayOfMonth();
        Integer month = date.getMonthValue();
        log.info("Getting actorns born in {} / {}", day, month);
        log.info("Initializing request to {}", imdbURL);

        return null;
    }

}
