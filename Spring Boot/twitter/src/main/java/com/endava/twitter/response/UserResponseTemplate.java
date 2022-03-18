package com.endava.twitter.response;

import com.endava.twitter.model.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseTemplate {
    private String msg;
    private UserDto userDto;

    public UserResponseTemplate(String msg) {
        this.msg = msg;
    }

    public UserResponseTemplate(String msg, UserDto userDto) {
        this.msg = msg;
        this.userDto = userDto;
    }
}
