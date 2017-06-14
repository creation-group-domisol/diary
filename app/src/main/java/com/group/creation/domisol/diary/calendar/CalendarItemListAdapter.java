package com.group.creation.domisol.diary.calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cob on 2017. 5. 15..
 */

public class CalendarItemListAdapter extends BaseAdapter {
    private Context context;
    private List<CalendarItem> calendarItems = new ArrayList<>();

    public CalendarItemListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.calendarItems.size();
    }

    @Override
    public Object getItem(int position) {
        return calendarItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CalendarItemView itemView;
        if (convertView == null) {
            itemView = new CalendarItemView(context, calendarItems.get(position));
        } else {
            itemView = (CalendarItemView) convertView;
        }
        return itemView;
    }

    public void init() {
        clear();
        addItem(new CalendarItem("월"));
        addItem(new CalendarItem("화"));
        addItem(new CalendarItem("수"));
        addItem(new CalendarItem("목"));
        addItem(new CalendarItem("금"));
        addItem(new CalendarItem("토"));
        addItem(new CalendarItem("일"));
    }

    public void clear() {
        calendarItems.clear();
    }

    public void addItem(CalendarItem calendarItem) {
        this.calendarItems.add(calendarItem);
    }

    public void setItem(int idx, String contents) {
        this.calendarItems.get(idx).setContents(contents);
    }
}
