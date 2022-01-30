package com.rosenblat.richard.dto.imdb;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetBioResponse {

   private Image image;
   private String birthPlace;
   private String realName;
   private String legacyNameText;
   private String gender;
   private String type;
   private String disambiguation;
   private String name;
   private List<MiniBios> miniBios;
   private String id;
   private String birthDate;
   private Double heightCentimeters;

   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   public static class Image {

      private Integer width;
      private String id;
      private String url;
      private Integer height;
   }

   @Data
   @NoArgsConstructor
   @AllArgsConstructor
   public static class MiniBios {

      private String language;
      private String id;
      private String text;

   }
}
