package com.endava.twitter.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.endava.twitter.model.entity.Tweet;

import java.util.Optional;

@Repository
public interface TweetRepository extends MongoRepository<Tweet, String> {
    Optional<Tweet> findById(String id);
}
