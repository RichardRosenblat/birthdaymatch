package com.rosenblat.richard.dto.birthdaymatch;

import java.util.ArrayList;
import java.util.List;

import com.rosenblat.richard.dto.imdb.GetBioResponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BirthdayMatchResponse { 

   List<GetBioResponse> matches = new ArrayList<>();
   
   public boolean addActor(GetBioResponse match){
      return matches.add(match);
   };

   public boolean removeActor(GetBioResponse match){
      return matches.remove(match);
   };

}