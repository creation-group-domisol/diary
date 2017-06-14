package com.group.creation.domisol.diary.calendar;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by cob on 2017. 5. 15..
 */

public class CalendarItemListAdapter extends BaseAdapter {
    private Context context;
    private List<CalendarItem> calendarItems = new ArrayList<>();
    private List<CalendarItemView> itemViews = new ArrayList<>();

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
        itemViews.add(itemView);
        return itemView;
    }

    public void init() {
        addItem(new CalendarItem("월"));
        addItem(new CalendarItem("화"));
        addItem(new CalendarItem("수"));
        addItem(new CalendarItem("목"));
        addItem(new CalendarItem("금"));
        addItem(new CalendarItem("토"));
        addItem(new CalendarItem("일"));
    }

    public void clear() {
        for (CalendarItem calendarItem : calendarItems) {
            calendarItem.setContents(null);
        }
    }

    public void addItem(CalendarItem calendarItem) {
        this.calendarItems.add(calendarItem);
    }

    public void setItem(int idx, String contents) {
        this.calendarItems.get(idx).setContents(contents);
        this.itemViews.get(idx).setContents(contents);
    }
}
