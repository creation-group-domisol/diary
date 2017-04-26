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

public class MainActivity extends AppCompatActivity {
    private CalendarFragment calendarFragment = new CalendarFragment();
    private MemoFragment memoFragment;
    private ContactFragment contactFragment = new ContactFragment();
    private FrameLayout mainContent;
    private SQLiteDatabase readableDatabase;
    private SQLiteDatabase writableDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mainContent = (FrameLayout) findViewById(R.id.main_content);
        mainContent.setDrawingCacheEnabled(true);


        SQLiteDatabase sqLiteDatabase = openOrCreateDatabase("diary.db", MODE_PRIVATE, null);

        SQLiteOpenHelper sqLiteOpenHelper = new SQLiteOpenHelper(this, "diary.db", null, 1) {
            @Override
            public void onCreate(SQLiteDatabase db) {
                db.execSQL("" +
                        "create table MEMO (" +
                        "   page integer PRIMARY KEY autoincrement," +
                        "   content text" +
                        ")");

                db.execSQL("insert into MEMO (page, content) values(1,'default')");
            }

            @Override
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                //do nothing
            }

            @Override
            public void onOpen(SQLiteDatabase db) {
                memoFragment = new MemoFragment();
                super.onOpen(db);
            }
        };

        readableDatabase = sqLiteOpenHelper.getReadableDatabase();
        writableDatabase = sqLiteOpenHelper.getWritableDatabase();

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

    public SQLiteDatabase getReadableDatabase() {
        return readableDatabase;
    }

    public SQLiteDatabase getWritableDatabase() {
        return writableDatabase;
    }
}
