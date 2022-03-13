package com.endava.twitter.repository;

import com.endava.twitter.model.entity.PublicUser;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.endava.twitter.model.entity.Tweet;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface TweetRepository extends MongoRepository<Tweet, String> {
    Optional<Tweet> findById(String id);
    List<Tweet> findAll();

    Page<Tweet> findAll(Pageable p);

    //{"text": {$in : [/.*?0.*/i] } }
    //@Query (value = "{text: {$in: ?0 } }")
    List<Tweet> findAllByTextIn(List<String> value);
    List<Tweet> findAllByTextContaining(String value);
    List<Tweet> findAllByTextInOrUserIn(List<String> filtersText, List<PublicUser> filtersUser);

}
