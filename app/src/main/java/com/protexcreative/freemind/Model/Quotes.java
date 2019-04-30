package com.protexcreative.freemind.Model;

public class Quotes {

    private int color;
    private int font;
    private String quotes_text;
    private String quotesid;
    private long timeend;
    private long timestart;
    private String userid;

    public Quotes(int color, int font, String quotes_text, String quotesid, long timeend, long timestart, String userid) {
        this.color = color;
        this.font = font;
        this.quotes_text = quotes_text;
        this.quotesid = quotesid;
        this.timeend = timeend;
        this.timestart = timestart;
        this.userid = userid;
    }

    public Quotes() {
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getFont() {
        return font;
    }

    public void setFont(int font) {
        this.font = font;
    }

    public String getQuotes_text() {
        return quotes_text;
    }

    public void setQuotes_text(String quotes_text) {
        this.quotes_text = quotes_text;
    }

    public String getQuotesid() {
        return quotesid;
    }

    public void setQuotesid(String quotesid) {
        this.quotesid = quotesid;
    }

    public long getTimeend() {
        return timeend;
    }

    public void setTimeend(long timeend) {
        this.timeend = timeend;
    }

    public long getTimestart() {
        return timestart;
    }

    public void setTimestart(long timestart) {
        this.timestart = timestart;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
