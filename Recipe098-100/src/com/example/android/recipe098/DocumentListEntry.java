package com.example.android.recipe098;

import com.google.api.client.util.Key;

public class DocumentListEntry {
    @Key
    public String title;

    @Key("gd:resourceId")
    public String id;

    public String getDocId() {
        if (id == null) {
            return "";
        } else {
            return id.substring(id.lastIndexOf(":") + 1);
        }
    }
}
