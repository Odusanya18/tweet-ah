package com.twitter.tweetah.model;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

public class Tweet {
    @Id
    private String id;

    @NotBlank
    @Size(max = 140)
    private String text;

    @NotNull
    private Date createdAt = new Date();

    public Tweet() {
    }

    public Tweet(String text) {
        this.setId(id);
        this.setText(text);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
