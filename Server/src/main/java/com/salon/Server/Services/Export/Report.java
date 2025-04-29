package com.salon.Server.Services.Export;

import java.io.Serializable;

public class Report implements Serializable {
    int id;
    String title;
    String date;
    String author;
    String content;
    public Report(int id, String title, String date, String author, String content) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.author = author;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }
    public String getDate() {
        return date;
    }
    public String getAuthor() {
        return author;
    }
    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }
}
