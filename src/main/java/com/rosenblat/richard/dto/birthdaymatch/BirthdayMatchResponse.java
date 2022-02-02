package com.rosenblat.richard.dto.birthdaymatch;

import java.util.ArrayList;
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
public class BirthdayMatchResponse { 

   private List<ActorInfo> matches = new ArrayList<>();


   public boolean addActorInfo(ActorInfo actorInfo) {
      return matches.add(actorInfo);
   }
}
