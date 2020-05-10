package com.SevenDigITs.Solutions.justTap.listview;

public class UserEmrgClass {
    private int title;
    private int content;

    public UserEmrgClass() {

    }
    public UserEmrgClass(int title, int content) {
        this.title = title;
        this.content = content;
    }

    public boolean playText(int text) {
        title = text;
        return false;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }

    public int getContent() {
        return content;
    }

    public void setContent(int content) {
        this.content = content;
    }
}
