package com.group.creation.domisol.diary.memo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.group.creation.domisol.diary.MainActivity;
import com.group.creation.domisol.diary.R;
import com.group.creation.domisol.diary.Swipable;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Created by daumkakao on 2017. 4. 3..
 */

public class MemoFragment extends Fragment implements Swipable {

    private static int curPage = 0;
    private EditText editText;
    private SQLiteDatabase db;
    private ViewGroup rootView;
    private TextView pageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        MainActivity mainActivity = (MainActivity) getContext();
        db = mainActivity.getReadableDatabase();

        rootView = (ViewGroup) inflater.inflate(R.layout.fragment_memo, container, false);
        editText = (EditText) rootView.findViewById(R.id.memo_content);
        pageView = (TextView) rootView.findViewById(R.id.memo_page);
        editText.getBackground().clearColorFilter();
        editText.setText(getContentThenChangeCurPage(0));

        final LinearLayout backgoundLayout = (LinearLayout) rootView.findViewById(R.id.memo_backgound);

        backgoundLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgoundLayout.requestFocus();
            }
        });


        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    String text = editText.getText().toString();
                    Cursor curPageCursor = db.rawQuery("select content from MEMO where page = ?", new String[]{curPage + ""});
                    if (curPageCursor.getCount() < 1 && !text.equals("")) {
                        db.execSQL("insert into MEMO (page, content) values(?, ?)", new Object[]{curPage, text});
                    } else {
                        db.execSQL("update MEMO set content = ? where page = ?", new Object[]{text, curPage});
                    }
                    curPageCursor.close();
                }
            }
        });


        return rootView;
    }

    @Override
    public void swipeLeft() {
        editText.setText(getContentThenChangeCurPage(curPage + 1));
    }

    @Override
    public void swipeRight() {
        if (curPage == 0) {
            return;
        }
        editText.setText(getContentThenChangeCurPage(curPage - 1));
    }

    private String getContentThenChangeCurPage(int page) {
        final Cursor cursor = db.rawQuery("select content from MEMO where page = ?", new String[]{page + ""});
        String memo = "";
        if (cursor.moveToNext()) {
            memo = cursor.getString(0);
        }
        cursor.close();
        this.curPage = page;
        this.pageView.setText(page + 1 + "");
        return memo;
    }
}
