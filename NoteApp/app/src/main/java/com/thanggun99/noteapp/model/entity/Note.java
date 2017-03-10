package com.thanggun99.noteapp.model.entity;

import java.io.Serializable;

public class Note implements Serializable {

    private int id;
    private String date;
    private String content;
    private String color;

    public Note(int id, String date, String content, String color) {
        this.id = id;
        this.date = date;
        this.content = content;
        this.color = color;
    }

    public Note() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
