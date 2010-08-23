package com.example.android.recipe101;

import java.util.List;

import com.google.api.client.util.Key;

public class CalendarFeed {
    @Key("entry")
    public List<CalendarEntry> entries;
}
