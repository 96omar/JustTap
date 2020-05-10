package com.SevenDigITs.Solutions.justTap.listview;

public class UserMiniCardClass {
    private int title;

    public UserMiniCardClass() {

    }
    public boolean playText(int text) {
        title = text;
        return false;
    }
    public UserMiniCardClass(int title) {
        this.title = title;
    }

    public int getTitle() {
        return title;
    }

    public void setTitle(int title) {
        this.title = title;
    }
}
