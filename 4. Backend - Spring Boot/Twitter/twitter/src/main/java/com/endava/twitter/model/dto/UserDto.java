package com.endava.twitter.model.dto;

import com.endava.twitter.model.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class UserDto {

    private String id;
    private String name;
    private String username;
    private String email;
    private String password;
    private List<String> favorites = new ArrayList<>();
    private List<String> friends = new ArrayList<>();
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
