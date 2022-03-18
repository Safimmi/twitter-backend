package com.endava.twitter.response;

import com.endava.twitter.model.dto.TweetDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TweetResponseTemplate {

    private String msg;
    private TweetDto tweetDto;

    public TweetResponseTemplate(String msg) {
        this.msg = msg;
    }

    public TweetResponseTemplate(String msg, TweetDto tweetDto) {
        this.tweetDto = tweetDto;
        this.msg = msg;
    }
}
