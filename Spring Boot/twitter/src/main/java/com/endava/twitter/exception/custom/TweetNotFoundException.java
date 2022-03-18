package com.endava.twitter.exception.custom;

public class TweetNotFoundException extends RuntimeException{

    private static final String TWEET_NOT_FOUND_MSG = "The tweet with id %s does not exist.";

    public TweetNotFoundException(String id_msg){
        super(String.format(TWEET_NOT_FOUND_MSG, id_msg));
    }

    public TweetNotFoundException(String id_msg, Throwable cause){
        super(String.format(TWEET_NOT_FOUND_MSG, id_msg), cause);
    }

}
