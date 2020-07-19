package com.example.mobooks.DataModels;

public class LocalBooksMode {
    public String bookTitle;
    public String bookAuthor;
    public int bookImage;

    public LocalBooksMode(String bookTitle, String bookAuthor) {
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

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public void setBookImage(int bookImage) {
        this.bookImage = bookImage;
    }
}
