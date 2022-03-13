package com.endava.twitter.controller;

import com.endava.twitter.model.entity.PublicUser;
import com.endava.twitter.model.mapper.PublicUserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;

import com.endava.twitter.model.dto.TweetDto;
import com.endava.twitter.service.TweetService;
import com.endava.twitter.response.TweetResponseTemplate;
import com.endava.twitter.model.dto.UserDto;
import com.endava.twitter.service.UserService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

//import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @GetMapping("/tweets")
    ResponseEntity<List<TweetDto>> findAll(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tweetService.findAll());
    }

    @GetMapping("/tweets/sorting")
    ResponseEntity<List<TweetDto>> findAllSorting(@RequestParam String sortBy){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tweetService.findAllSorting(sortBy));
    }

    @GetMapping("/tweets/filtering")
    ResponseEntity<List<TweetDto>> findFiltering(
            @RequestParam(value = "search", required = false) List<String> filtersText,
            @RequestParam(value = "user", required = false) List<String> filtersUser
    ){

        //Default Values
        filtersText = filtersText != null ? filtersText : new ArrayList<>();
        filtersUser = filtersUser != null? filtersUser : new ArrayList<>();

        /*List<String> filters = Stream
                .concat(search.stream(), user.stream())
                .collect(Collectors.toList());*/


        //Formating : Filtering
        List<UserDto> userDtoFilters = userService.findFiltersById(filtersUser);
        List<PublicUser> publicUserFilters = userDtoFilters
                .stream()
                .map(PublicUserMapper.PUBLIC_USER_INSTANCE::toEntity)
                .distinct()
                .collect(Collectors.toList());

        List<String> textFullFilters = filtersText.stream()
                .map(s -> tweetService.findAllTextsContaining(s))
                .flatMap(Collection::stream)
                .map(TweetDto::getText)
                .distinct()
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(tweetService.findFiltering(textFullFilters, publicUserFilters));
    }

    @GetMapping("tweets/page")
    ResponseEntity <Page<TweetDto>> findAllOnPages(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "limit", required = false) Integer size
    ){

        //Default Values
        page = page != null ? page : 0;
        size = size != null? size : 100;

        return ResponseEntity
                .status(HttpStatus.OK)
                .body( tweetService.findAllOnPages(page, size));
    }

    @PostMapping("/tweets")
    ResponseEntity<TweetResponseTemplate> addNewTweet(@RequestHeader String userId, @RequestBody @Validated TweetDto tweet) {
        UserDto userDto = userService.findById(userId);
        TweetDto tweetDto = tweetService.addNewTweet(tweet, userDto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .header("userId", userId)
                .header("tweetId", tweetDto.getId())
                .body(
                        new TweetResponseTemplate(
                                "Your new tweet has been created successfully.",
                                tweetDto
                        )
                );

    }

    @PutMapping("/tweets")
    ResponseEntity<TweetResponseTemplate> updateTweet(@RequestHeader String userId, @RequestParam String tweetId, @RequestBody @Validated TweetDto tweet){
        userService.findById(userId);
        TweetDto tweetDto = tweetService.updateTweet(userId, tweetId, tweet);

        return ResponseEntity
                .status(HttpStatus.OK)
                .header("userId", userId)
                .header("tweetId", tweetId)
                .body(
                        new TweetResponseTemplate(
                            "Tweet has been updated successfully.",
                            tweetDto
                        )
                );
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
    ResponseEntity<TweetResponseTemplate> addToFavorites(@RequestHeader String userId, @RequestParam String tweetId){
        userService.findById(userId);
        TweetDto tweetDto = tweetService.addToFavorites(userId, tweetId);
        userService.addToFavorites(userId, tweetId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("userId", userId)
                .header("tweetId", tweetId)
                .body(
                        new TweetResponseTemplate(
                                "Tweet has been added to favourites successfully.",
                                tweetDto
                        )
                );
    }

    @PostMapping("/favorites/destroy")
    ResponseEntity<TweetResponseTemplate> removeFromFavorites(@RequestHeader String userId, @RequestParam String tweetId){
        userService.findById(userId);
        TweetDto tweetDto = tweetService.removeFromFavorites(userId, tweetId);
        userService.removeFromFavorites(userId, tweetId);
        return ResponseEntity
                .status(HttpStatus.OK)
                .header("userId", userId)
                .header("tweetId", tweetId)
                .body(
                        new TweetResponseTemplate(
                                "Tweet has been removed from favourites successfully.",
                                tweetDto
                        )
                );
    }



}
