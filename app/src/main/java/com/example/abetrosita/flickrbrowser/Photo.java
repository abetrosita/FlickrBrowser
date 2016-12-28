package com.example.abetrosita.flickrbrowser;

import java.io.Serializable;

/**
 * Created by AbetRosita on 12/26/2016.
 */

public class Photo implements Serializable{
    String title;
    String author;
    String authorId;
    String link;
    String tags;
    String image;

    private static final long serialVersionUID = 1L;

    public Photo(String title, String author, String authorId, String link, String tags, String image) {
        this.title = title;
        this.author = author;
        this.authorId = authorId;
        this.link = link;
        this.tags = tags;
        this.image = image;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {

        /*
        return "Photo{" +
                "title='" + this.title + '\'' +
                ", author='" + this.author + '\'' +
                ", authorId='" + this.authorId + '\'' +
                ", link='" + this.link + '\'' +
                ", tags='" + this.tags + '\'' +
                ", image='" + this.image + '\'' +
                '}';
                */

        return "Title: " + this.title + "\n" +
                "Image: " + this.image;
    }

    public String getTitle() {
        return this.title;
    }

    public String getAuthor() {
        return this.author;
    }

    public String getAuthorId() {
        return this.authorId;
    }

    public String getLink() {
        return this.link;
    }

    public String getTags() {
        return this.tags;
    }

    public String getImage() {
        return this.image;
    }
}
