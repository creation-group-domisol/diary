package com.group.creation.domisol.diary.calendar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.group.creation.domisol.diary.MainActivity;
import com.group.creation.domisol.diary.R;
import com.group.creation.domisol.diary.Swipable;

/**
 * Created by cob on 2017. 3. 29..
 */

public class CalendarFragment extends Fragment implements Swipable {

    private int currentPage = 0;
    private static final int DURATION = 7;
    private SQLiteDatabase db;
    private Spinner weekSpinner;
    private Spinner monthSpinner;
    private CalendarItemListAdapter itemAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity) getContext();
        db = mainActivity.getReadableDatabase();

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_calendar, container, false);
        ListView calendarListContainer = (ListView) rootView.findViewById(R.id.calendarListContainer);

        monthSpinner = (Spinner) rootView.findViewById(R.id.month_spinner);
        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(getContext(), R.array.month_array, R.layout.support_simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);
        monthSpinner.setPrompt("월");

        weekSpinner = (Spinner) rootView.findViewById(R.id.week_spinner);
        ArrayAdapter<CharSequence> weekAdapter = ArrayAdapter.createFromResource(getContext(), R.array.week_array, R.layout.support_simple_spinner_dropdown_item);
        weekSpinner.setAdapter(weekAdapter);
        weekSpinner.setPrompt("주");

        itemAdapter = new CalendarItemListAdapter(getContext());
        initListItem(itemAdapter);
        calendarListContainer.setAdapter(itemAdapter);
        return rootView;
    }

    private void initListItem(CalendarItemListAdapter itemAdapter) {
        itemAdapter.addItem(new CalendarItem("월"));
        itemAdapter.addItem(new CalendarItem("화"));
        itemAdapter.addItem(new CalendarItem("수"));
        itemAdapter.addItem(new CalendarItem("목"));
        itemAdapter.addItem(new CalendarItem("금"));
        itemAdapter.addItem(new CalendarItem("토"));
        itemAdapter.addItem(new CalendarItem("일"));
    }


    private void saveWeek(int page){
        String selectedWeek = weekSpinner.getSelectedItem().toString();
        String selectedMonth = monthSpinner.getSelectedItem().toString();

        Cursor curPageCursor = db.rawQuery("select content from CALENDAR_WEEK where page = ?", new String[]{page + ""});
        if (curPageCursor.getCount() < 1 && (!selectedWeek.equals("") && !selectedMonth.equals(""))) {
            db.execSQL("insert into CALENDAR_WEEK (page, month, week) values(?, ?, ?)", new Object[]{page, selectedMonth, selectedWeek});
        } else {
            db.execSQL("update CALENDAR_WEEK set month = ?, week = ? where page = ?", new Object[]{selectedMonth, selectedWeek, page});
        }
        curPageCursor.close();
    }

    private void saveDay(int page){
        for (int i = 0; i < itemAdapter.getCount(); i++) {
            CalendarItem item = (CalendarItem) itemAdapter.getItem(i);
            saveDayItem(item, page, i);
        }
    }

    private void saveDayItem(CalendarItem calendarItem, int page, int weekday){
        String text = calendarItem.getContents();

//        "create table CALENDAR_DAY (" +
//                "   id integer PRIMARY KEY AUTOINCREMENT," +
//                "   page integer," +
//                "   weekday integer," +
//                "   content text" +
//                ")");

        Cursor curPageCursor = db.rawQuery("select content from CALENDAR_DAY where page = ? and weekday = ?", new String[]{page + "", weekday + ""});
        if (curPageCursor.getCount() < 1 && !text.equals("")) {
            db.execSQL("insert into CALENDAR_DAY (page, weekday, content) values(?, ?, ?)", new Object[]{page, weekday, text});
        } else {
            db.execSQL("update CALENDAR_DAY set content = ? where page = ? and weekday = ?", new Object[]{text, page, weekday});
        }
        curPageCursor.close();
    }

    @Override
    public void swipeLeft() {
        saveWeek(currentPage);
        saveDay(currentPage);
//        editText.setText(getContentThenChangeCurPage(curPage + 1));
    }

    @Override
    public void swipeRight() {
//        if (curPage == 0) {
//            return;
//        }
//        saveWeek(editText.getText().toString(), curPage);
//        saveDay(editText.getText().toString(), curPage);
//        editText.setText(getContentThenChangeCurPage(curPage - 1));
    }
}
