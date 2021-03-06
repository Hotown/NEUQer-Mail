package com.neuqer.mail.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Hotown on 17/5/15.
 */
@Getter
@Setter
@Table(name = "token")
public class Token implements BaseModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private String token;

    private Integer client;

    private Long createdAt;

    private Long updatedAt;

    private Long expiredAt;
}
