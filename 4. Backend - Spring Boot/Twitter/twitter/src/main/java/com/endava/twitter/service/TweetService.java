package com.endava.twitter.service;

import com.endava.twitter.model.dto.UserDto;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.endava.twitter.model.dto.TweetDto;
import com.endava.twitter.repository.TweetRepository;
import com.endava.twitter.model.mapper.TweetMapper;
import com.endava.twitter.model.entity.PublicUser;

import com.endava.twitter.exception.TweetNotFoundException;

import java.text.SimpleDateFormat;
import java.util.Date;



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
                .image(tweetDto.getImage())
                .user(publicUser)
                .build();

        tweetRepository.save(TweetMapper.TWEET_INSTANCE.toEntity(tweetDto_build));
        return tweetDto_build;

    }

    public TweetDto updateTweet(String userId, String tweetId, TweetDto tweet){

        String timeStamp = generateNowTimeStamp();
        String text = tweet.getText();
        String image = tweet.getImage();

        TweetDto tweetDto = findById(tweetId);

        if(text!=null){tweetDto.setText(tweet.getText());}
        if(image!=null){tweetDto.setImage(tweet.getImage());}
        if(text!=null || image!=null) {tweetDto.setLastModifiedAt(timeStamp);}

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

    public void removeFromFavorites(String userId, String tweetId){
        TweetDto tweetDto = findById(tweetId);
        tweetDto.removeUserFromFavouriteToArray(userId);
        tweetRepository.save(TweetMapper.TWEET_INSTANCE.toEntity(tweetDto));
    }


}
