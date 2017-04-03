package com.group.creation.domisol.diary.memo;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.group.creation.domisol.diary.R;
import com.group.creation.domisol.diary.Swipable;

/**
 * Created by daumkakao on 2017. 4. 3..
 */

public class MemoFragment extends Fragment implements Swipable {

    private static String page1 = "page1";
    private static String page2 = "page2";
    private EditText editText;

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


        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        });



        return rootView;
    }

    @Override
    public void swipeLeft() {
        editText.setText(page1);
    }

    @Override
    public void swipeRight() {
        editText.setText(page2);
    }
}
