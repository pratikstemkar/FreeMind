package com.protexcreative.freemind.Model;

public class Notification {
    private String userid;
    private String text;
    private String postid;
    private Boolean ispost;



    public Notification(String userid, String text, String postid, Boolean ispost) {
        this.userid = userid;
        this.text = text;
        this.postid = postid;
        this.ispost = ispost;
    }

    public Notification(){

    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPostid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }

    public Boolean getIspost() {
        return ispost;
    }

    public void setIspost(Boolean ispost) {
        this.ispost = ispost;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
