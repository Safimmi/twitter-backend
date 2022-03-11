package com.endava.twitter.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import com.endava.twitter.model.dto.TweetDto;
import com.endava.twitter.service.TweetService;
import com.endava.twitter.model.dto.UserDto;
import com.endava.twitter.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

@RestController
public class TweetController {

    @Autowired
    private TweetService tweetService;
    @Autowired
    private UserService userService;

    @GetMapping("/tweet/{tweetId}")
    ResponseEntity<TweetDto> searchById(@RequestHeader String userId, @PathVariable String tweetId){

        userService.findById(userId);
        TweetDto tweetDto = tweetService.findById(tweetId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("userId", userId)
                .body(tweetDto);

    }

    @PostMapping("/tweets")
    ResponseEntity<String> addNewTweet(@RequestHeader String userId, @RequestBody @Validated TweetDto tweet) {

        UserDto userDto = userService.findById(userId);
        TweetDto tweetDto = tweetService.addNewTweet(tweet, userDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("userId", userId)
                .header("tweetId", tweetDto.getId())
                .body("New tweet created successfully.");

    }

    @PutMapping("/tweets")
    ResponseEntity<String> updateTweet(@RequestHeader String userId, @RequestParam String tweetId, @RequestBody @Validated TweetDto tweet){

        userService.findById(userId);
        TweetDto tweetDto = tweetService.updateTweet(userId, tweetId, tweet);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("userId", userId)
                .header("tweetId", tweetId)
                .body("Tweet has been updated successfully.");
    }

    @DeleteMapping("/tweets/{tweetId}")
    ResponseEntity<String> deleteTweet(@RequestHeader String userId, @PathVariable String tweetId){

        userService.findById(userId);
        tweetService.deleteTweet(userId, tweetId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("userId", userId)
                .header("tweetId", tweetId)
                .body("Tweet has been deleted successfully.");

    }

    @PostMapping("/favorites/create")
    ResponseEntity<String> addToFavorites(@RequestHeader String userId, @RequestParam String tweetId){
        userService.findById(userId);
        TweetDto tweetDto = tweetService.addToFavorites(userId, tweetId);
        userService.addToFavorites(userId, tweetId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("userId", userId)
                .header("tweetId", tweetId)
                .body("Tweet has been added to favourites successfully.");
    }

    @PostMapping("/favorites/destroy")
    ResponseEntity<String> removeFromFavorites(@RequestHeader String userId, @RequestParam String tweetId){
        userService.findById(userId);
        tweetService.removeFromFavorites(userId, tweetId);
        userService.removeFromFavorites(userId, tweetId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("userId", userId)
                .header("tweetId", tweetId)
                .body("Tweet has been removed from favourites successfully.");
    }


}
