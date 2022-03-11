package com.endava.twitter.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@AllArgsConstructor
@Document
public class PublicUser {

    @Id
    private String id;
    private String name;
    private String username;

}
