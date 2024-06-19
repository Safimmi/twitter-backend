package com.endava.twitter.model.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Document(collection = "Tweet")
@AllArgsConstructor
@NoArgsConstructor
public class Tweet {

    @Id
    private String id;
    private String createdAt;
    private String lastModifiedAt;
    @NotNull
    @Size(min = 0, max = 280)
    private String text;
    @NotNull
    private String image;
    private List<String> favorites;
    private Integer favoriteCount;
    private PublicUser user;

}
