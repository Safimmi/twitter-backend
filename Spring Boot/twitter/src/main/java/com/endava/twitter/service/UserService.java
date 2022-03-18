package com.endava.twitter.service;

//import org.springframework.security.crypto.password.PasswordEncoder;
import com.endava.twitter.security.UserRole;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.endava.twitter.model.dto.UserDto;
import com.endava.twitter.repository.UserRepository;
import com.endava.twitter.model.mapper.UserMapper;

import com.endava.twitter.exception.custom.UserNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

  /*  @Autowired
    private PasswordEncoder passwordEncoder;*/

    public UserDto findById(String id){
         return userRepository.findById(id)
                 .map(UserMapper.USER_INSTANCE::toDto)
                 .orElseThrow(
                         () -> new UserNotFoundException(id)
                 );

    }

    public UserDto findByUsername(String username){
        return userRepository.findByUsername(username)
                .map(UserMapper.USER_INSTANCE::toDto)
                .orElseThrow(
                        () -> new UserNotFoundException(username)
                );
    }

    public boolean existsUserByUsername(String username){
        return userRepository.existsUserByUsername(username);
    }

    public UserDto createNewUser (UserDto userInfo){

        UserDto userDto_build = UserDto
                .builder()
                .name(userInfo.getName())
                .username(userInfo.getUsername())
                //.password(passwordEncoder.encode(userInfo.getPassword()))
                .favorites(new ArrayList<>())
                .friends(new ArrayList<>())
                .role(UserRole.USER)
                .build();

        userRepository.save(UserMapper.USER_INSTANCE.toEntity(userDto_build));
        return findByUsername(userInfo.getUsername());
    }

    public List<UserDto> findFiltersById(List<String> filters){
        return userRepository
                .findAllByIdIn(filters)
                .stream()
                .map(UserMapper.USER_INSTANCE::toDto)
                .collect(Collectors.toList());
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
