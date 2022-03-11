package com.endava.twitter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.endava.twitter.model.dto.UserDto;
import com.endava.twitter.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    ResponseEntity<UserDto> searchById(@RequestParam String id){

        UserDto userDto = userService.findById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("userId", userDto.getId())
                .body(userDto);

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
