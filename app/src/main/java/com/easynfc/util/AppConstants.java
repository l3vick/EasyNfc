package com.easynfc.util;

/**
 * Created by pablorojas on 4/2/18.
 */

public class AppConstants {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 1;




    public enum TagTypes {

        TEXT {
            public String toString() {
                return "text";
            }
        },

        URL {
            public String toString() {
                return "url";
            }
        },
        SMS {
            public String toString() {
                return "sms";
            }
        },

        PHONE {
            public String toString() {
                return "phone";
            }
        },
        AAR {
            public String toString() {
                return "aar";
            }
        },

        LOCATION {
            public String toString() {
                return "location";
            }
        },
        WIFI {
            public String toString() {
                return "wifi";
            }
        },

        EMAIL {
            public String toString() {
                return "email";
            }
        },
        NDEF {
            public String toString() {
                return "ndef";
            }
        },
        LOCK {
            public String toString() {
                return "lock";
            }
        }
    }

    public static TagTypes getTypeTag(String type) {
        for (TagTypes tagType : TagTypes.values()) {
            if (type.equals(tagType.toString())){
                return tagType;
            }
        }
        return null;
    }
}
