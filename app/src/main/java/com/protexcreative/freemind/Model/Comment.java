package com.protexcreative.freemind.Model;

public class Comment {

    private String comment;
    private String author;
    private String date;

    public Comment(String comment, String author, String date) {
        this.comment = comment;
        this.author = author;
        this.date = date;

    }

    public Comment(){


    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
