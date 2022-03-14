package com.endava.twitter.model.entity;

import com.endava.twitter.security.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "User")
public class User extends PublicUser{

    @NotNull
    private String password;

    private List<String> favorites;
    private List<String> friends;

    private UserRole role;

    public User(String name, String username, String password) {
        this.setName(name);
        this.setUsername(username);
        this.password = password;
        this.favorites = new ArrayList<>();
        this.friends = new ArrayList<>();
        this.role = UserRole.USER;
    }


}
