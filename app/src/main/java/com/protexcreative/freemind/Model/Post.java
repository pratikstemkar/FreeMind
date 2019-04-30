package com.protexcreative.freemind.Model;

public class Post {

    private String postid;
    private String text_post;
    private String date;
    private String author;
    private int color;
    private String category;
    private Boolean anonymous;
    private int size;
    private int font;


    public Boolean getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(Boolean anonymous) {
        this.anonymous = anonymous;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Post(String postid, String text_post, String date, String author, int color, String category, int font, int size, Boolean anonymous) {
        this.postid = postid;
        this.text_post = text_post;
        this.date = date;
        this.author = author;
        this.color = color;
        this.category = category;
        this.font = font;
        this.size = size;
        this.anonymous = anonymous;
    }

    public int getFont() {
        return font;
    }

    public void setFont(int font) {
        this.font = font;
    }

    public Post(){


    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public String getText_post() {
        return text_post;
    }

    public void setText_post(String text_post) {
        this.text_post = text_post;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
