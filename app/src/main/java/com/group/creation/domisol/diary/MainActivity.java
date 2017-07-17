package com.group.creation.domisol.diary;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.group.creation.domisol.diary.calendar.CalendarFragment;
import com.group.creation.domisol.diary.contacts.ContactFragment;
import com.group.creation.domisol.diary.memo.MemoFragment;

public class MainActivity extends AppCompatActivity implements SectionSwitchable {
    private final Section calendarFragment = new CalendarFragment();
    private final Section memoFragment = new MemoFragment();
    private final Section contactFragment = new ContactFragment();
    private FrameLayout mainContent;
    private SQLiteDatabase readableDatabase;
    private SQLiteDatabase writableDatabase;
    private Section currentFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mainContent = (FrameLayout) findViewById(R.id.main_content);
        mainContent.setDrawingCacheEnabled(true);

        SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(this, "diary.db", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("" +
                        "create table MEMO (" +
                        "   page integer PRIMARY KEY," +
                        "   content text" +
                        ")");

                db.execSQL("" +
                        "create table CALENDAR_WEEK (" +
                        "   page integer PRIMARY KEY," +
                        "   month integer," +
                        "   week integer" +
                        ")");

                db.execSQL("" +
                        "create table CALENDAR_DAY (" +
                        "   id integer PRIMARY KEY AUTOINCREMENT," +
                        "   page integer," +
                        "   weekday integer," +
                        "   content text" +
                        ")");

                db.execSQL("" +
                        "create table CONTACT (" +
                        "   id integer PRIMARY KEY AUTOINCREMENT," +
                        "   page integer," +
                        "   idx integer," +
                        "   name text," +
                        "   email text," +
                        "   phone text" +
                        ")");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                //do nothing
            }

            @Override
            public void onOpen(SQLiteDatabase db) {
                super.onOpen(db);
            }
        };

        readableDatabase = sqLiteOpenHelper.getReadableDatabase();
        writableDatabase = sqLiteOpenHelper.getWritableDatabase();

        getSupportFragmentManager().beginTransaction().add(R.id.main_content, memoFragment).commit();
        this.currentFragment = memoFragment;
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
        this.currentFragment = (Section) fragment;
    }

    public SQLiteDatabase getReadableDatabase() {
        return readableDatabase;
    }

    public SQLiteDatabase getWritableDatabase() {
        return writableDatabase;
    }

    @Override
    public boolean next() {
        if (this.currentFragment == this.memoFragment) {
            replaceView(this.calendarFragment);
            this.currentFragment.goToFirstPage();
            return true;
        }
        if (this.currentFragment == this.calendarFragment) {
            replaceView(this.contactFragment);
            this.currentFragment.goToFirstPage();
            return true;
        }
        return false;
    }

    @Override
    public boolean prev() {
        if (this.currentFragment == this.calendarFragment) {
            replaceView(this.memoFragment);
            this.currentFragment.goToLastPage();
            return true;
        }
        if (this.currentFragment == this.contactFragment) {
            replaceView(this.calendarFragment);
            this.currentFragment.goToLastPage();
            return true;
        }
        return false;
    }
}
