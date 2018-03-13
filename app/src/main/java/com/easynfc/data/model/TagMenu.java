package com.easynfc.data.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Arrays;
import java.util.List;

/**
 * Created by pablorojas on 21/2/18.
 */

public class TagMenu {

    private String type;

    private String img;

    public TagMenu(String type, String img) {
        this.type = type;
        this.img = img;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public static List<TagMenu> getTagMenuList() {
        return Arrays.asList(new TagMenu("Simple-Text", "ic_text_eb_superlight"),
                new TagMenu("Url", "ic_url_eb_superlight"),
                new TagMenu("Sms", "ic_sms_eb_superlight"),
                new TagMenu("Phone", "ic_phone_eb_superlight"),
                new TagMenu("App-Launcher", "ic_aar_eb_superlight"),
                new TagMenu("Location", "ic_location_eb_superlight"),
                new TagMenu("Wi-Fi", "ic_wifi_eb_superlight"),
                new TagMenu("Email", "ic_email_eb_superlight"),
                new TagMenu("NDEF-Format", "ic_format_eb_superlight"),
                new TagMenu("Lock", "ic_lock_eb_superlight"));
    }
}
