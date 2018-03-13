package com.easynfc.data.model;

/**
 * Created by pablorojas on 7/3/18.
 */

public class TagResponse {

    String type, content, tnf, rtd, size;
    String[] techList;

    public TagResponse(String type, String content, String[] techList, String tnf, String rtd, String size) {
        this.type = type;
        this.content = content;
        this.techList = techList;
        this.tnf = tnf;
        this.rtd = rtd;
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public String[] getTechList() {
        return techList;
    }

    public String getTnf() {
        return tnf;
    }

    public String getRtd() {
        return rtd;
    }

    public String getSize() {
        return size;
    }
}
