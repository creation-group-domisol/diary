package com.group.creation.domisol.diary.calendar;

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
import android.widget.TextView;

import com.group.creation.domisol.diary.MainActivity;
import com.group.creation.domisol.diary.R;
import com.group.creation.domisol.diary.Swipable;

import java.util.ArrayList;

/**
 * Created by cob on 2017. 3. 29..
 */

public class CalendarFragment extends Fragment implements Swipable {

    private int currentPage = 0;
    private static final int DURATION = 7;
    private SQLiteDatabase db;
    private Spinner weekSpinner;
    private Spinner monthSpinner;
    private TextView pageNumber;
    private CalendarItemListAdapter itemAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity) getContext();
        db = mainActivity.getReadableDatabase();

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_calendar, container, false);
        ListView calendarListContainer = (ListView) rootView.findViewById(R.id.calendar_list_container);

        monthSpinner = (Spinner) rootView.findViewById(R.id.month_spinner);
        ArrayAdapter<CharSequence> monthAdapter = ArrayAdapter.createFromResource(getContext(), R.array.month_array, R.layout.support_simple_spinner_dropdown_item);
        monthSpinner.setAdapter(monthAdapter);

        weekSpinner = (Spinner) rootView.findViewById(R.id.week_spinner);
        ArrayAdapter<CharSequence> weekAdapter = ArrayAdapter.createFromResource(getContext(), R.array.week_array, R.layout.support_simple_spinner_dropdown_item);
        weekSpinner.setAdapter(weekAdapter);

        itemAdapter = new CalendarItemListAdapter(getContext(), R.layout.calendar_list_item, new ArrayList<CalendarItem>());
        itemAdapter.init();

        pageNumber = (TextView) rootView.findViewById(R.id.page_number);
        pageNumber.setText(currentPage+"");
        calendarListContainer.setAdapter(itemAdapter);

        loadCalendar();
        return rootView;
    }

    private void saveWeek(int page){
        String selectedWeek = weekSpinner.getSelectedItem().toString();
        String selectedMonth = monthSpinner.getSelectedItem().toString();

        Cursor curPageCursor = db.rawQuery("select * from CALENDAR_WEEK where page = ?", new String[]{page + ""});
        if (curPageCursor.getCount() < 1 && ((selectedWeek == null || !selectedWeek.equals("")) && (selectedMonth == null || !selectedMonth.equals("")))) {
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
        if (curPageCursor.getCount() < 1 && (text == null || !text.equals(""))) {
            db.execSQL("insert into CALENDAR_DAY (page, weekday, content) values(?, ?, ?)", new Object[]{page, weekday, text});
        } else {
            db.execSQL("update CALENDAR_DAY set content = ? where page = ? and weekday = ?", new Object[]{text, page, weekday});
        }
        curPageCursor.close();
    }

    private void loadWeek(int page)  {
        Cursor curPageCursor = db.rawQuery("select * from CALENDAR_WEEK where page = ?", new String[]{page + ""});
        if (curPageCursor.getCount() < 1) {
            monthSpinner.setSelection(0);
            weekSpinner.setSelection(0);
            return;
        }
        curPageCursor.moveToNext();
        Integer month = curPageCursor.getInt(1);
        Integer week = curPageCursor.getInt(2);
        curPageCursor.close();

        monthSpinner.setSelection(month);
        weekSpinner.setSelection(week);
    }

    private void loadDay(int page) {
        Cursor curPageCursor = db.rawQuery("select * from CALENDAR_DAY where page = ?", new String[]{page + ""});
        if (curPageCursor.getCount() < 1)
            return;
        for (int i = 0; i < curPageCursor.getCount(); i++) {
            curPageCursor.moveToNext();
            int week = curPageCursor.getInt(2);
            String contents = curPageCursor.getString(3);
            itemAdapter.setItem(week, contents);
        }
        curPageCursor.close();
    }

    @Override
    public void swipeLeft() {
        saveCalendar();
        currentPage++;
        loadCalendar();
    }

    private void saveCalendar() {
        saveWeek(currentPage);
        saveDay(currentPage);
        itemAdapter.clear();
    }

    @Override
    public void swipeRight() {
        if (currentPage == 0) {
            return;
        }
        saveCalendar();
        currentPage--;
        loadCalendar();
    }

    private void loadCalendar() {
        loadWeek(currentPage);
        loadDay(currentPage);
        itemAdapter.notifyDataSetChanged();
        pageNumber.setText(currentPage+"");
    }
}
