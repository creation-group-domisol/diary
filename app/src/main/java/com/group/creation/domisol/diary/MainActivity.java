package com.group.creation.domisol.diary;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.group.creation.domisol.diary.calendar.CalendarFragment;
import com.group.creation.domisol.diary.contacts.ContactFragment;
import com.group.creation.domisol.diary.memo.MemoFragment;

public class MainActivity extends AppCompatActivity {
    private CalendarFragment calendarFragment = new CalendarFragment();
    private MemoFragment memoFragment = new MemoFragment();
    private ContactFragment contactFragment = new ContactFragment();
    private FrameLayout mainContent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mainContent = (FrameLayout) findViewById(R.id.main_content);
        mainContent.setDrawingCacheEnabled(true);

        getSupportFragmentManager().beginTransaction().add(R.id.main_content, memoFragment).commit();
    }


    public void onToCalendar(View view) {
        replaceView(calendarFragment);
    }

    public void onToMemo(View view) {
        replaceView(memoFragment);
    }

    public void onToContacts(View view) {
        replaceView(contactFragment);
    }

    private void replaceView(Fragment fragment) {
        if (fragment.isVisible())
            return;
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, fragment).commit();
    }
}
