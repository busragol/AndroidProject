package com.example.books;

public class Book {
    //These attributes must match with attributes in firebase database
    private String title;
    private String author;
    private String fee;
    private String category_name;

    public Book() {

    }

    public Book(String title, String author, String fee, String category_name) {
        this.title = title;
        this.author = author;
        this.fee = fee;
        this.category_name = category_name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
