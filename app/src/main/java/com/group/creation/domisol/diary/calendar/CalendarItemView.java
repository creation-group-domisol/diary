package com.group.creation.domisol.diary.calendar;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.group.creation.domisol.diary.R;

/**
 * Created by cob on 2017. 5. 15..
 */

public class CalendarItemView extends LinearLayout {
    private CalendarItem aItem;
    private final TextView contentsView;

    public CalendarItemView(Context context, final CalendarItem aItem) {
        super(context);
        this.aItem = aItem;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar_list_item, this, true);

        String date = aItem.getDayOfWeek();
        String contents = aItem.getContents();

        TextView dateView = (TextView) findViewById(R.id.dayOfWeek);
        contentsView = (TextView) findViewById(R.id.contents);

        contentsView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                aItem.setContents(s.toString());
            }
        });

        dateView.setText(date + "");
        setContents(contents);
    }

    public void setContents(String contents) {
        contentsView.setText(contents);
    }
}
