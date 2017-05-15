package com.group.creation.domisol.diary.calendar;

/**
 * Created by cob on 2017. 5. 15..
 */

public class CalendarItem {
    private int date;
    private String contents;

    public CalendarItem(int date, String contents) {
        this.date = date;
        this.contents = contents;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
