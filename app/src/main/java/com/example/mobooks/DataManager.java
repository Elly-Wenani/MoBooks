package com.example.mobooks;

public class DataManager {
    public String bookTitle;
    public String bookAuthor;
    public int bookImage;

    public DataManager(String bookTitle, String bookAuthor) {
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookImage = bookImage;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public int getBookImage() {
        return bookImage;
    }
}
