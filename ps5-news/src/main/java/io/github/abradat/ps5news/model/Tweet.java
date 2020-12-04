package io.github.abradat.ps5news.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TWEET")
public class Tweet {
    @Id
    @Column(name = "ID")
    private long id;

    @Column(name = "USERNAME", columnDefinition = "VARCHAR(50)")
    private String username;

    @Column(name = "TEXT", columnDefinition = "VARCHAR(300)")
    private String tweetText;

    @Enumerated(EnumType.STRING)
    @Column(name = "SENTIMENT")
    private TypeSentiment sentiment;

    @Column(name = "PUBLISH_DATE")
    private Date publishDate;

    public Tweet() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTweetText() {
        return tweetText;
    }

    public void setTweetText(String tweetText) {
        this.tweetText = tweetText;
    }

    public TypeSentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(TypeSentiment sentiment) {
        this.sentiment = sentiment;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }
}
