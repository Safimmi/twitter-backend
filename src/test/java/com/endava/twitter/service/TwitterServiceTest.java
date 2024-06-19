package com.endava.twitter.service;

import com.endava.twitter.exception.custom.TweetNotFoundException;
import com.endava.twitter.model.dto.TweetDto;
import com.endava.twitter.model.entity.Tweet;
import com.endava.twitter.repository.TweetRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TwitterServiceTest {

    @InjectMocks
    private TweetService tweetService;
    @Mock
    private TweetRepository tweetRepository;

    @Test
    public void fail_findById_recordDoesNotExist(){
        String tweetId = "0000000_false";
        when(tweetRepository.findById(tweetId))
                .thenReturn(Optional.empty());
        try {
            tweetService.findById(tweetId);
            fail("Test was supposed to fail.");
        }catch (Exception e){
            assertTrue(e instanceof TweetNotFoundException);
            verify(tweetRepository).findById(tweetId);
            verifyNoMoreInteractions(tweetRepository);
        }
    }

    @Test
    public void success_findById_recordDoesExist(){
        String tweetId = "0000000_true";
        String text = "This is a test tweet.";
        String image = "image_test.img";

        Tweet tweet = new Tweet();
        tweet.setId(tweetId);
        tweet.setText(text);
        tweet.setImage(image);

        when(tweetRepository.findById(tweetId))
                .thenReturn(Optional.of(tweet));

        TweetDto tweetDto = tweetService.findById(tweetId);

        assertEquals(tweet.getId(), tweetDto.getId());
        assertEquals(tweet.getText(), tweetDto.getText());
        assertEquals(tweet.getImage(), tweetDto.getImage());

        verify(tweetRepository).findById(tweetId);
        verifyNoMoreInteractions(tweetRepository);

    }



}
