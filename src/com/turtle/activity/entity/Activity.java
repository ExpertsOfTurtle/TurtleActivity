package com.turtle.activity.entity;

public class Activity {
    private Integer idactivity;

    private String username;

    private String datetime;

    private String type;

    private String result;

    public Integer getIdactivity() {
        return idactivity;
    }

    public void setIdactivity(Integer idactivity) {
        this.idactivity = idactivity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}