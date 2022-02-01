package com.rosenblat.richard.dto.imdb.knownFor;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class KnownForResponse {
    
    private Summary summary;
    private Double imdbRating;
    private List<String> categories;
    private Title title;
    private WhereToWatch whereToWatch;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Summary {
        private List<String> characters;
        private String category;
        private String displayYear;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Image {
        private Integer width;
        private String id;
        private String url;
        private Integer height;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Title {
        private Image image;
        private String titleType;
        private Integer year;
        @JsonProperty("@type")
        private String type;
        private String id;
        private String title;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class WhereToWatch {
        private Boolean hasPhysicalOffers;
        private Boolean hasDigitalOffers;
        private Boolean freeWithPrime;
        private String releaseDate;
        private Boolean hasTvShowings;
        private Boolean hasShowtimes;
    }
}
