package com.endava.twitter.exception.custom;

public class UsernameNotFoundException extends RuntimeException{

    private static final String USER_NOT_FOUND_MSG = "The username %s does not exist.";

    public UsernameNotFoundException(String id_msg){
        super(String.format(USER_NOT_FOUND_MSG, id_msg));
    }

    public UsernameNotFoundException(String id_msg, Throwable cause){
        super(String.format(USER_NOT_FOUND_MSG, id_msg), cause);
    }

}
