package com.sabanciuniv.cs310hw2_v0;

public class News {

    private int id;
    private String title;
    private String text;
    private String date;
    private String image;
    private String categoryName;

    public News() {
    }

    public News(int id, String title, String text, String date, String image, String categoryName) {
        this.id = id;
        this.title = title;
        this.text = text;
        this.date = date;
        this.image = image;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getDate() {
        return date;
    }

    public String getImage() {
        return image;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
