package io.github.abradat.ps5news.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "CNN_NEWS")
public class CnnNews implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;

    @Column(name = "NEWS_TITLE", nullable = false, columnDefinition = "VARCHAR(100)")
    private String newsTitle;

    @Column(name = "NEWS_BODY", nullable = false, columnDefinition = "TEXT")
    private String newsBody;

    @Column(name = "NEWS_TIME", nullable = false)
    private LocalDateTime newsTime;

    public CnnNews() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public String getNewsBody() {
        return newsBody;
    }

    public void setNewsBody(String newsBody) {
        this.newsBody = newsBody;
    }

    public LocalDateTime getNewsTime() {
        return newsTime;
    }

    public void setNewsTime(LocalDateTime newsTime) {
        this.newsTime = newsTime;
    }
}
