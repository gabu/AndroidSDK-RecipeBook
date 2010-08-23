package com.example.android.recipe101;

import com.google.api.client.util.Key;

public class CalendarEntry {
    @Key
    public String title;

    @Key("id")
    public String id;

    public String getUserID() {
        if (id == null) {
            return "";
        } else {
            return id.substring(id.lastIndexOf("/") + 1);
        }
    }
}
