package com.endava.twitter.model.dto;

import com.endava.twitter.model.entity.PublicUser;
import lombok.*;

import com.endava.twitter.model.entity.User;

import javax.validation.constraints.Size;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TweetDto {

    String id;
    String createdAt;
    String lastModifiedAt;

    @Size(min = 0, max = 280)
    String text;

    String image;
    List<String> favorites;
    Integer favoriteCount;
    PublicUser user;

    public void addUserToFavouriteToArray(String userId){
        if(!favorites.contains(userId)){
            favorites.add(userId);
            favoriteCount++;
        }
    }
    public void removeUserFromFavouriteToArray(String userId){
        if(favorites.contains(userId)){
            favorites.removeIf(i -> i.equals(userId));
            favoriteCount--;
        }
    }


}
