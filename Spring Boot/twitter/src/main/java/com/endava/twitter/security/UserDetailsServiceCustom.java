package com.endava.twitter.security;

import com.endava.twitter.exception.custom.TweetNotFoundException;
import com.endava.twitter.model.entity.User;
import com.endava.twitter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceCustom implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.
                findByUsername(username)
                .orElseThrow(
                        () -> new UsernameNotFoundException(username)
                );
        UserDetailsCustom userDetailsCustom = new UserDetailsCustom(user);
        return userDetailsCustom;
    }

}
