package com.neuqer.mail.model;

import javax.persistence.*;

/**
 * Created by Hotown on 17/5/22.
 */
@Table(name = "log")
public class Log implements BaseModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    @Column(name = "mobile_id")
    private Long mobileId;

    private String message;

    @Column(name = "sended_at")
    private Long sendedAt;

    private Boolean status;

    @Column(name = "created_at")
    private Long createdAt;

    @Column(name = "updated_at")
    private Long updatedAt;
}
