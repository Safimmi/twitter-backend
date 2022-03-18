package com.endava.twitter.controller;

import com.endava.twitter.model.mapper.UserMapper;
import com.endava.twitter.response.TweetResponseTemplate;
import com.endava.twitter.response.UserResponseTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.endava.twitter.model.dto.UserDto;
import com.endava.twitter.service.UserService;

import org.springframework.validation.annotation.Validated;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.*;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public  ResponseEntity<String> loginPost() {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body("You have logged in successfully");
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginGet() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body("You have been redirected to login");
    }

    @GetMapping("/user")
    ResponseEntity<UserDto> searchById(@RequestParam String id){
        UserDto userDto = userService.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("userId", userDto.getId())
                .body(userDto);

    }

    @PostMapping("/user/singup")
    ResponseEntity<UserResponseTemplate> createNewUser(@RequestBody @Valid UserDto userInfo){

        if(!userService.existsUserByUsername(userInfo.getUsername())){
            UserDto userDto = userService.createNewUser(userInfo);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .header("userId", userDto.getId())
                    .body(
                            new UserResponseTemplate(
                                    "User registered successfully.",
                                    userDto
                            )
                    );
        }
        else{
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(
                            new UserResponseTemplate(
                                    "Registration failed. \n A user is already registered with this username. ",
                                    null
                            )
                    );
        }



    }

    @PostMapping("/friends/create")
    ResponseEntity<String> addFriend(@RequestHeader String userId, @RequestParam String friendId){
        UserDto userDto = userService.addFriend(userId, friendId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("userId", userId)
                .header("friendId", friendId)
                .body("You followed a new friend.");
    }

    @PostMapping("/friends/destroy")
    ResponseEntity<String> removeFriend(@RequestHeader String userId, @RequestParam String friendId){
        userService.removeFriend(userId, friendId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("userId", userId)
                .header("friendId", friendId)
                .body("You unfollowed a friend.");
    }



}
