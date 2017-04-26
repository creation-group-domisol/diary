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

import com.group.creation.domisol.diary.MainActivity;
import com.group.creation.domisol.diary.R;
import com.group.creation.domisol.diary.Swipable;

/**
 * Created by daumkakao on 2017. 4. 3..
 */

public class MemoFragment extends Fragment implements Swipable {

    private static String page1 = "page1";
    private static String page2 = "page2";
    private EditText editText;
    private SQLiteDatabase db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_memo, container, false);
        editText = (EditText) rootView.findViewById(R.id.memo_content);
        editText.getBackground().clearColorFilter();
        editText.setText(page1);



        final LinearLayout backgoundLayout = (LinearLayout) rootView.findViewById(R.id.memo_backgound);

        backgoundLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backgoundLayout.requestFocus();
            }
        });

        MainActivity mainActivity = (MainActivity) getContext();
        db = mainActivity.getReadableDatabase();

        db.execSQL("insert into MEMO (content) values('default')");
        Cursor cursor = db.rawQuery("select content from MEMO", null);

        System.out.println(cursor.getCount());
        cursor.moveToNext();
        String memo = cursor.getString(1);
        editText.setText(memo);
        cursor.close();

        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);



                    String text = editText.getText().toString();

                    db.execSQL("update MEMO set content = '" + text + "' where page = 1");
                }
            }
        });



        return rootView;
    }

    @Override
    public void swipeLeft() {
        editText.setText(page2);
    }

    @Override
    public void swipeRight() {
        editText.setText(page1);
    }
}
