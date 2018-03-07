package com.easynfc.data.model;

/**
 * Created by pablorojas on 7/3/18.
 */

public class TagData {

    String imageType, content, subtype, tittle, typeTag, size;

    public TagData(String imageType, String content, String subtype, String tittle, String typeTag, String size) {
        this.imageType = imageType;
        this.content = content;
        this.subtype = subtype;
        this.tittle = tittle;
        this.typeTag = typeTag;
        this.size = size;
    }

    public String getImageType() {
        return imageType;
    }

    public String getContent() {
        return content;
    }

    public String getSubtype() {
        return subtype;
    }

    public String getTittle() {
        return tittle;
    }

    public String getTypeTag() {
        return typeTag;
    }

    public String getSize() {
        return size;
    }
}
