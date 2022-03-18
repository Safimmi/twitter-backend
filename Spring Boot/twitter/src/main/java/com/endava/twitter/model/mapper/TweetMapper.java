package com.endava.twitter.model.mapper;

import com.endava.twitter.model.dto.TweetDto;
import com.endava.twitter.model.entity.Tweet;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TweetMapper {
    TweetMapper TWEET_INSTANCE = Mappers.getMapper(TweetMapper.class);
    TweetDto toDto(Tweet tweet);
    Tweet toEntity(TweetDto tweetDto);
}
