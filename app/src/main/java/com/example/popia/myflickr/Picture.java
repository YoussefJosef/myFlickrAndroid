package com.example.popia.myflickr;

/**
 * Created by popia on 10/1/16.
 */
public class Picture {

    private String title;
    private String link;
    private String media;
    private String date_taken;
    private String description;
    private String published;
    private String author;
    private String tags;

    public Picture(){}

    //Getters and setters
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {

        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public String getDate_taken() {
        return date_taken;
    }

    public void setDate_taken(String date_taken) {
        this.date_taken = date_taken;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublished() {
        return published;
    }

    public void setPublished(String published) {
        this.published = published;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
