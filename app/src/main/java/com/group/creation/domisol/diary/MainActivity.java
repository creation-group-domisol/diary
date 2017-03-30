package com.group.creation.domisol.diary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.group.creation.domisol.diary.calendar.CalendarFragment;

public class MainActivity extends AppCompatActivity {
    private CalendarFragment calendarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        calendarFragment = new CalendarFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.main_content, calendarFragment).commit();
    }

    public void onToCalendar() {
        if (calendarFragment.isVisible())
            return;
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, calendarFragment).commit();
    }
}
