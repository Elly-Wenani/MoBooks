package com.example.mobooks;

import java.io.Serializable;

public class BooksMode implements Serializable {
    private String id;
    private String bkTitle;
    private String bkAuthor;
    private String bkImageUrl;
    private String bkImageName;

    public BooksMode(){}

    public BooksMode(String bkTitle, String bkAuthor, String bkImageUrl, String bkImageName) {
        this.setId(id);
        this.setBkTitle(bkTitle);
        this.setBkAuthor(bkAuthor);
        this.setBkImageUrl(bkImageUrl);
        this.setBkImageName(bkImageName);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBkTitle() {
        return bkTitle;
    }

    public void setBkTitle(String bkTitle) {
        this.bkTitle = bkTitle;
    }

    public String getBkAuthor() {
        return bkAuthor;
    }

    public void setBkAuthor(String bkAuthor) {
        this.bkAuthor = bkAuthor;
    }

    public String getBkImageUrl() {
        return bkImageUrl;
    }

    public void setBkImageUrl(String bkImageUrl) {
        this.bkImageUrl = bkImageUrl;
    }

    public String getBkImageName() {
        return bkImageName;
    }

    public void setBkImageName(String bkImageName) {
        this.bkImageName = bkImageName;
    }
}
