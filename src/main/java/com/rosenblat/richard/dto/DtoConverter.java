package com.rosenblat.richard.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.rosenblat.richard.dto.birthdaymatch.ActorInfo;
import com.rosenblat.richard.dto.birthdaymatch.ActorInfo.Image;
import com.rosenblat.richard.dto.birthdaymatch.ActorInfo.KnownFor;
import com.rosenblat.richard.dto.birthdaymatch.ActorInfo.MiniBio;
import com.rosenblat.richard.dto.birthdaymatch.ActorInfo.Spouse;
import com.rosenblat.richard.dto.birthdaymatch.ActorInfo.Summary;
import com.rosenblat.richard.dto.birthdaymatch.ActorInfo.Title;
import com.rosenblat.richard.dto.birthdaymatch.ActorInfo.WhereToWatch;
import com.rosenblat.richard.dto.imdb.getBio.GetBioResponse;
import com.rosenblat.richard.dto.imdb.knownFor.KnownForResponse;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DtoConverter {
    public static ActorInfo getActorInfo(GetBioResponse bio, List<KnownForResponse> knownFor) {

        ActorInfo actorInfo = new ActorInfo();

        actorInfo.setBirthPlace(bio.getBirthPlace());
        actorInfo.setRealName(bio.getRealName());
        actorInfo.setAkas(bio.getAkas());
        actorInfo.setGender(bio.getGender());
        actorInfo.setName(bio.getName());
        actorInfo.setNicknames(bio.getNicknames());
        actorInfo.setTrademarks(bio.getTrademarks());
        actorInfo.setBirthDate(bio.getBirthDate());
        actorInfo.setImage(getActorInfoImageFromBio(bio));
        actorInfo.setSpouses(getActorInfoSpousesFromBio(bio));
        actorInfo.setMiniBios(getActorInfoMiniBioFromBio(bio));
        actorInfo.setKnownFor(getActorInfoKnownFor(knownFor));

        return actorInfo;
    }

    public static List<MiniBio> getActorInfoMiniBioFromBio(GetBioResponse bio) {
        if (bio.getMiniBios() == null) {
            return null;
        }
        List<MiniBio> miniBios = bio.getMiniBios()
                .stream().map(x -> new ActorInfo.MiniBio(
                        x.getAuthor(),
                        x.getLanguage(),
                        x.getText()))
                .collect(Collectors.toList());

        return miniBios;
    }

    public static List<Spouse> getActorInfoSpousesFromBio(GetBioResponse bio) {
        if (bio.getSpouses() == null) {
            return null;
        }
        List<Spouse> spouses = bio.getSpouses()
                .stream().map(x -> new ActorInfo.Spouse(
                        x.getFromDate(),
                        x.getToDate(),
                        x.getCurrent(),
                        x.getName(),
                        x.getAttributes()))
                .collect(Collectors.toList());

        return spouses;
    }

    public static Image getActorInfoImageFromBio(GetBioResponse bio) {
        if (bio.getImage() == null) {
            return null;
        }

        return new ActorInfo.Image(
                bio.getImage().getWidth(),
                bio.getImage().getUrl(),
                bio.getImage().getHeight());
    }

    public static List<KnownFor> getActorInfoKnownFor(List<KnownForResponse> knownFor) {
        if (knownFor == null) {
            return null;
        }
        List<KnownFor> actorInfoKnownFor = knownFor
                .stream().map(x -> new ActorInfo.KnownFor(
                        getActorInfoSummaryFromKnownFor(x),
                        x.getImdbRating(),
                        x.getCategories(),
                        getActorInfoTitleFromKnownFor(x),
                        getActorInfoWhereToWatchFromKnownFor(x)))
                .collect(Collectors.toList());

        return actorInfoKnownFor;
    }

    private static Summary getActorInfoSummaryFromKnownFor(KnownForResponse x) {
        return x.getSummary() == null ? null
                : new ActorInfo.Summary(
                        x.getSummary().getCharacters(),
                        x.getSummary().getCategory(),
                        x.getSummary().getDisplayYear());
    }

    private static Title getActorInfoTitleFromKnownFor(KnownForResponse x) {
        return x.getTitle() == null ? null
                : new ActorInfo.Title(
                        x.getTitle().getImage() == null ? null
                                : new ActorInfo.Image(
                                        x.getTitle().getImage().getWidth(),
                                        x.getTitle().getImage().getUrl(),
                                        x.getTitle().getImage().getHeight()),
                        x.getTitle().getTitleType(),
                        x.getTitle().getYear(),
                        x.getTitle().getTitle());
    }

    private static WhereToWatch getActorInfoWhereToWatchFromKnownFor(KnownForResponse x) {
        return x.getWhereToWatch() == null ? null
                : new ActorInfo.WhereToWatch(
                        x.getWhereToWatch().getHasPhysicalOffers(),
                        x.getWhereToWatch().getHasDigitalOffers(),
                        x.getWhereToWatch().getFreeWithPrime(),
                        x.getWhereToWatch().getReleaseDate(),
                        x.getWhereToWatch().getHasTvShowings(),
                        x.getWhereToWatch().getHasShowtimes());
    }

}
