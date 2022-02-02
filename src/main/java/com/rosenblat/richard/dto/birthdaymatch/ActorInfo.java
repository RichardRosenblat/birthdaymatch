package com.rosenblat.richard.dto.birthdaymatch;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ActorInfo {

   private List<Spouse> spouses;
   private String birthPlace;
   private String realName;
   private List<String> akas;
   private Image image;
   private String gender;
   private String name;
   private List<MiniBio> miniBios;
   private List<String> nicknames;
   private List<String> trademarks;
   private String birthDate;
   private List<KnownFor> knownFor;

   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   @JsonInclude(Include.NON_NULL)
   public static class Spouse {

      private String fromDate;
      private String toDate;
      private Boolean current;
      private String name;
      private String attributes;

   }

   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   @JsonInclude(Include.NON_NULL)
   public static class Image {

      private Integer width;
      private String url;
      private Integer height;

   }

   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   @JsonInclude(Include.NON_NULL)
   public static class MiniBio {

      private String author;
      private String language;
      private String text;

   }

   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   @JsonInclude(Include.NON_NULL)
   public static class KnownFor {

      private Summary summary;
      private Double imdbRating;
      private List<String> categories;
      private Title title;
      private WhereToWatch whereToWatch;

   }

   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   @JsonInclude(Include.NON_NULL)
   public static class Summary {

      private List<String> characters;
      private String category;
      private String displayYear;

   }

   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   @JsonInclude(Include.NON_NULL)
   public static class Title {

      private Image image;
      private String titleType;
      private Integer year;
      private String title;

   }

   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   @JsonInclude(Include.NON_NULL)
   public static class WhereToWatch {

      private Boolean hasPhysicalOffers;
      private Boolean hasDigitalOffers;
      private Boolean freeWithPrime;
      private String releaseDate;
      private Boolean hasTvShowings;
      private Boolean hasShowtimes;

   }

}
