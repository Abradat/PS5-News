package io.github.abradat.ps5news.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "CNN_NEWS")
public class CnnNews implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private long id;

    @Column(name = "TITLE", nullable = false, columnDefinition = "VARCHAR(255)")
    private String title;

    @Column(name = "BODY", nullable = false, columnDefinition = "TEXT")
    private String body;

    @Column(name = "PUBLISH_DATE", nullable = false)
    private Date publishDate;

    @Column(name = "HEADER_IMAGE_URL", nullable = false, columnDefinition = "VARCHAR(255)")
    private String headerImageUrl;

    @Column(name = "URL", nullable = false, columnDefinition = "VARCHAR(255)", unique = true)
    private String url;

    public CnnNews() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(Date publishDate) {
        this.publishDate = publishDate;
    }

    public String getHeaderImageUrl() {
        return headerImageUrl;
    }

    public void setHeaderImageUrl(String headerImageUrl) {
        this.headerImageUrl = headerImageUrl;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
