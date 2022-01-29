package com.rosenblat.richard.dto.birthdayMatch.imdb;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImdbBirthdayMatchResponse {
    List<String> matches;
}
