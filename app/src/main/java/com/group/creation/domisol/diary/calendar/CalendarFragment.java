package com.group.creation.domisol.diary.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.group.creation.domisol.diary.R;

/**
 * Created by cob on 2017. 3. 29..
 */

public class CalendarFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_calendar, container, false);
        ListView calendarListContainer = (ListView) rootView.findViewById(R.id.calendarListContainer);
        CalendarItemListAdapter itemAdapter = new CalendarItemListAdapter(getContext());
        itemAdapter.addItem(new CalendarItem(1, "contents01"));
        itemAdapter.addItem(new CalendarItem(2, "contents02"));
        calendarListContainer.setAdapter(itemAdapter);
        return rootView;
    }
}
