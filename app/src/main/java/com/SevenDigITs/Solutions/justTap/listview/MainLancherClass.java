package com.SevenDigITs.Solutions.justTap.listview;

public class MainLancherClass {
    private int title;
    private int content;
    private int image;

    public MainLancherClass() {

    }

    public MainLancherClass(int title, int content, int image) {
        this.title = title;
        this.content = content;
        this.image = image;
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
