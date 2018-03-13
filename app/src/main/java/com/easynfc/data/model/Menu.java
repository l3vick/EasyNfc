package com.easynfc.data.model;

import java.util.Arrays;
import java.util.List;

/**
 * Created by pablorojas on 20/2/18.
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

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public static List<Menu> getMenuList() {
        return Arrays.asList(new Menu("Write/", "write & save your favorites tags"),
                new Menu("Read/", "read tag content"),
                new Menu("My Tags/", "write, emulate & update your tags"));
    }
}
