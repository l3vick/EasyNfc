package com.easynfc.data;

/**
 * Created by pablorojas on 19/2/18.
 */

public class Menu {
    private String title;
    private String subtitle;

    public Menu(String title, String subtitle) {
        this.title = title;
        this.subtitle = subtitle;
    }

    public String getTitle() {
        return title;
    }

    public String getSubtitle() {
        return subtitle;
    }
}
