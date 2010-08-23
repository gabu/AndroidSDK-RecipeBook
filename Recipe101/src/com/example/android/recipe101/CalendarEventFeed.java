package com.example.android.recipe101;

import java.util.List;

import com.google.api.client.util.Key;

public class CalendarEventFeed {
    @Key("entry")
    public List<CalendarEventEntry> entries;
}
