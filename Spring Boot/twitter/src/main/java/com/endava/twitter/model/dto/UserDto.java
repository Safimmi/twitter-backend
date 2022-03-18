package com.endava.twitter.model.dto;

import com.endava.twitter.security.UserRole;
import lombok.*;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {


    private String id;

    @NotNull
    private String name;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private List<String> favorites;
    private List<String> friends;

    private UserRole role;


    public void addTweetToFavoritesArray(String tweetId){
        if(!favorites.contains(tweetId)){
            favorites.add(tweetId);
        }
    }
    public void removeTweetFromFavoritesArray(String tweetId){
        if(favorites.contains(tweetId)){
            favorites.removeIf(i -> i.equals(tweetId));
        }
    }

    public void addUserToFriendsArray(String friendId){
        if(!friends.contains(friendId)){
            friends.add(friendId);
        }
    }
    public void removeUserFromFriendsArray(String friendId){
        if(friends.contains(friendId)){
            friends.removeIf(i -> i.equals(friendId));
        }
    }

}
