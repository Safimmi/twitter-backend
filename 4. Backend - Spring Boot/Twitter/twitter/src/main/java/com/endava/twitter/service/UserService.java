package com.endava.twitter.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.endava.twitter.model.dto.UserDto;
import com.endava.twitter.repository.UserRepository;
import com.endava.twitter.model.mapper.UserMapper;

import com.endava.twitter.exception.UserNotFoundException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

     public UserDto findById(String id){

         return userRepository.findById(id)
                 .map(UserMapper.USER_INSTANCE::toDto)
                 .orElseThrow(
                         () -> new UserNotFoundException(id)
                 );

    }

    public UserDto addToFavorites(String userId, String tweetId){
        UserDto userDto = findById(userId);
        userDto.addTweetToFavoritesArray(tweetId);
        userRepository.save(UserMapper.USER_INSTANCE.toEntity(userDto));
        return userDto;
    }

    public void removeFromFavorites(String userId, String tweetId){
        UserDto userDto = findById(userId);
        userDto.removeTweetFromFavoritesArray(tweetId);
        userRepository.save(UserMapper.USER_INSTANCE.toEntity(userDto));
    }

    public UserDto addFriend(String userId, String friendId){
        UserDto userDto = findById(userId);
        userDto.addUserToFriendsArray(friendId);
        userRepository.save(UserMapper.USER_INSTANCE.toEntity(userDto));
        return userDto;
    }

    public void removeFriend(String userId, String friendId){
        UserDto userDto = findById(userId);
        userDto.removeUserFromFriendsArray(friendId);
        userRepository.save(UserMapper.USER_INSTANCE.toEntity(userDto));
    }


}
