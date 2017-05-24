package com.group.creation.domisol.diary.calendar;

/**
 * Created by cob on 2017. 5. 15..
 */

public class CalendarItem {
    private String dayOfWeek;
    private String contents;

    public CalendarItem(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public CalendarItem(String dayOfWeek, String contents) {
        this.dayOfWeek = dayOfWeek;
        this.contents = contents;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }
}
