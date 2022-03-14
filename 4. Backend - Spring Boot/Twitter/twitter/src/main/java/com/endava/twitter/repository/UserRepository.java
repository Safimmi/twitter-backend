package com.endava.twitter.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.endava.twitter.model.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findById(String id);
    Optional<User> findByUsername(String username);

    List<User> findAllByIdIn(List<String> filters);
    Boolean existsUserByUsername(String username);
}
