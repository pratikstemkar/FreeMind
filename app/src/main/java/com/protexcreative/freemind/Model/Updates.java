package com.protexcreative.freemind.Model;

public class Updates {

    private String version;
    private String date;
    private String link;
    private String size;
    private String description;
    private int versionCode;
    private Boolean visiblility;

    public Updates(String version, String date, String link, String size, String description, int versionCode, Boolean visiblility) {
        this.version = version;
        this.date = date;
        this.link = link;
        this.size = size;
        this.description = description;
        this.versionCode = versionCode;
        this.visiblility = visiblility;
    }

    public Updates() {
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public Boolean getVisiblility() {
        return visiblility;
    }

    public void setVisiblility(Boolean visiblility) {
        this.visiblility = visiblility;
    }
}
