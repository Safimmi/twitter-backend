package com.endava.twitter.model.entity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Document(collection = "User")
public class User extends PublicUser{

    @NotNull
    @NotEmpty
    private String password;

    private List<String> favorites;
    private List<String> friends;

    public User(String name, String username, String password) {
        this.setName(name);
        this.setUsername(username);
        this.password = password;
        this.favorites = new ArrayList<>();
        this.friends = new ArrayList<>();
    }


}
