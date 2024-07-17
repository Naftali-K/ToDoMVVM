package com.nk.todomvvm.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ToDo {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String content;

    public ToDo() {
    }

    public ToDo(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "ToDo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
