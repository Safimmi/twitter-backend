package com.endava.twitter.model.entity;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "Tweet")
public class Tweet {

    @Id
    String id;
    String createdAt;
    String lastModifiedAt;
    @Size(min = 0, max = 280)
    String text;
    String image;
    List<String> favorites = new ArrayList<>();
    int favoriteCount;
    PublicUser user;

}
