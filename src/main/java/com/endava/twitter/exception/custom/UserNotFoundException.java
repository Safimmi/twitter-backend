package com.endava.twitter.exception.custom;

public class UserNotFoundException extends RuntimeException{

    private static final String USER_NOT_FOUND_MSG = "The user with id %s does not exist.";

    public UserNotFoundException(String id_msg){
        super(String.format(USER_NOT_FOUND_MSG, id_msg));
    }

    public UserNotFoundException(String id_msg, Throwable cause){
        super(String.format(USER_NOT_FOUND_MSG, id_msg), cause);
    }

}
