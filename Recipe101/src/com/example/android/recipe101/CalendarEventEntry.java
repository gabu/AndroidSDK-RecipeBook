package com.example.android.recipe101;

import com.google.api.client.util.Key;

public class CalendarEventEntry {
    @Key
    public String title;

    @Key
    public String content;

    @Key("gd:when")
    public When when;
}
