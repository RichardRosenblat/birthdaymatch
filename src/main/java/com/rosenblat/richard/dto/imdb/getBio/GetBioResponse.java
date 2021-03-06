package com.rosenblat.richard.dto.imdb.getBio;

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
public class GetBioResponse {

   private String name;
   private String realName;
   private Image image;
   private String birthPlace;
   private String legacyNameText;
   private String gender;
   private String disambiguation;
   private List<MiniBio> miniBios;
   private String id;
   private List<String> nicknames;
   private String birthDate;
   private Double heightCentimeters;
   private List<String> trademarks;
   private List<String> akas;
   private List<Spouse> spouses;
   @JsonProperty("@type")
   private String type;

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
   public static class MiniBio {
      private String language;
      private String id;
      private String author;
      private String text;
   }

   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   @JsonIgnoreProperties(ignoreUnknown = true)
   public static class Spouse {

      private String fromDate;
      private String toDate;
      private Boolean current;
      private String name;
      private String attributes;
      private String id;

   }
}
