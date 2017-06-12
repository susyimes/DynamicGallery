package com.susyimes.dynamicgallerylib.bus;

/**
 *  on 2016/6/21 0021.
 */
public class DAction {
    String action;
    int position;
    String title;

    public DAction(String action) {
        this.action = action;
    }

    public DAction(int position, String title) {
        this.title = title;
        this.position = position;
    }

    public DAction(String action, int position) {
        this.action = action;
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
