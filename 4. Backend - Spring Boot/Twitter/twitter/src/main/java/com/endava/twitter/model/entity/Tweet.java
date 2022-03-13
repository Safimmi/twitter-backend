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
    private String id;
    private String createdAt;
    private String lastModifiedAt;
    @Size(min = 0, max = 280)
    private String text;
    private String image;
    private List<String> favorites;
    private Integer favoriteCount;
    private PublicUser user;

}
