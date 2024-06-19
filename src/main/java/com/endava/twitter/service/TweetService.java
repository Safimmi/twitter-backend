package com.endava.twitter.service;

import com.endava.twitter.model.entity.TweetSorting;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.endava.twitter.model.dto.UserDto;
import com.endava.twitter.model.entity.Tweet;
import com.endava.twitter.model.dto.TweetDto;
import com.endava.twitter.repository.TweetRepository;
import com.endava.twitter.model.mapper.TweetMapper;
import com.endava.twitter.model.entity.PublicUser;

import com.endava.twitter.exception.custom.TweetNotFoundException;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class TweetService {

    @Autowired
    private TweetRepository tweetRepository;


    public String generateNowTimeStamp(){
        final String TWITTER_DATE_FORMAT ="EEE MMM dd HH:mm:ss ZZZZ yyyy";
        return new SimpleDateFormat(TWITTER_DATE_FORMAT).format(new Date());
    }

    public TweetDto findById(String tweetId){
        return tweetRepository.findById(tweetId)
                .map(TweetMapper.TWEET_INSTANCE::toDto)
                .orElseThrow(
                        () -> new TweetNotFoundException(tweetId)
                );
    }

    public List<TweetDto> findAll(){
        return tweetRepository
                .findAll()
                .stream()
                .map(TweetMapper.TWEET_INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public List<TweetDto> findAllSorting(String sortBy){

        Sort.Direction direction = (
                sortBy.equalsIgnoreCase(String.valueOf(TweetSorting.NEWEST)) ||
                        sortBy.equalsIgnoreCase(String.valueOf(TweetSorting.POPULAR))
        )? Sort.Direction.DESC : Sort.Direction.ASC;

        String field = (
                sortBy.equalsIgnoreCase(String.valueOf(TweetSorting.NEWEST)) ||
                        sortBy.equalsIgnoreCase(String.valueOf(TweetSorting.OLDEST))
        )? "createdAt" : "favoriteCount";

        return tweetRepository
                .findAll(Sort.by(direction, field))
                .stream()
                .map(TweetMapper.TWEET_INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public Page<TweetDto> findAllOnPages(int page, int size){

        Pageable p = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));

        List<TweetDto> tweetDtos = tweetRepository
                .findAll(p)
                .stream()
                .map(TweetMapper.TWEET_INSTANCE::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(tweetDtos);

    }

    public List<TweetDto> findAllTextsContaining(String filter){
        return tweetRepository.findAllByTextContaining(filter)
                .stream()
                .map(TweetMapper.TWEET_INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public List<TweetDto> findKeywords(List<String> keywords){

        keywords = Collections.singletonList("\\/^This is about cats.\\/");

        return tweetRepository.findAllByTextIn(keywords)
                .stream()
                .map(TweetMapper.TWEET_INSTANCE::toDto)
                .collect(Collectors.toList());
    }

    public Page<TweetDto> findFiltering(int page, int size, String sortBy, List<PublicUser> filtersUser, List<String> filtersText){

        Sort.Direction direction = (
                sortBy.equalsIgnoreCase(String.valueOf(TweetSorting.NEWEST)) ||
                        sortBy.equalsIgnoreCase(String.valueOf(TweetSorting.POPULAR))
        )? Sort.Direction.DESC : Sort.Direction.ASC;

        String field = (
                sortBy.equalsIgnoreCase(String.valueOf(TweetSorting.NEWEST)) ||
                        sortBy.equalsIgnoreCase(String.valueOf(TweetSorting.OLDEST))
        )? "createdAt" : "favoriteCount";

        Pageable p = PageRequest.of(page, size, Sort.by(direction, field));

        List<TweetDto> tweetDtos = tweetRepository
                .findAllByTextInOrUserIn(filtersText, filtersUser, p)
                .stream()
                .map(TweetMapper.TWEET_INSTANCE::toDto)
                .collect(Collectors.toList());

        return new PageImpl<>(tweetDtos);
    }

    public TweetDto addNewTweet(TweetDto tweetDto, UserDto userDto){

        String timeStamp = generateNowTimeStamp();

        PublicUser publicUser = new PublicUser(
                userDto.getId(),
                userDto.getName(),
                userDto.getUsername()
        );

        TweetDto tweetDto_build = TweetDto
                .builder()
                .createdAt(timeStamp)
                .lastModifiedAt(timeStamp)
                .text(tweetDto.getText())
                .favorites(new ArrayList<>())
                .favoriteCount(0)
                .image(tweetDto.getImage())
                .user(publicUser)
                .build();

        tweetRepository.save(TweetMapper.TWEET_INSTANCE.toEntity(tweetDto_build));
        return tweetDto_build;

    }

    public TweetDto updateTweet(String userId, String tweetId, TweetDto tweet){

        String timeStamp = generateNowTimeStamp();
        TweetDto tweetDto = findById(tweetId);

        tweetDto.setText(tweet.getText()!= null ? tweet.getText() : tweetDto.getText());
        tweetDto.setImage(tweet.getImage() != null ? tweet.getImage() : tweetDto.getImage());
        tweetDto.setLastModifiedAt((tweet.getText()!= null || tweet.getImage() != null) ? timeStamp : tweetDto.getLastModifiedAt());

        tweetRepository.save(TweetMapper.TWEET_INSTANCE.toEntity(tweetDto));
        return tweetDto;

    }

    public void deleteTweet(String userId, String tweetId){
        TweetDto tweetDto = findById(tweetId);
        tweetRepository.delete(TweetMapper.TWEET_INSTANCE.toEntity(tweetDto));
    }

    public TweetDto addToFavorites(String userId, String tweetId){
        TweetDto tweetDto = findById(tweetId);
        tweetDto.addUserToFavouriteToArray(userId);
        tweetRepository.save(TweetMapper.TWEET_INSTANCE.toEntity(tweetDto));
        return tweetDto;
    }

    public TweetDto removeFromFavorites(String userId, String tweetId){
        TweetDto tweetDto = findById(tweetId);
        tweetDto.removeUserFromFavouriteToArray(userId);
        tweetRepository.save(TweetMapper.TWEET_INSTANCE.toEntity(tweetDto));
        return  tweetDto;
    }


}
