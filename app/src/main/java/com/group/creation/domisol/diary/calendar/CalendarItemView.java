package com.group.creation.domisol.diary.calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.group.creation.domisol.diary.R;

/**
 * Created by cob on 2017. 5. 15..
 */

public class CalendarItemView extends LinearLayout {
    public CalendarItemView(Context context, CalendarItem aItem) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar_list_item, this, true);

        int date = aItem.getDate();
        String contents = aItem.getContents();

        TextView dateView = (TextView) findViewById(R.id.date);
        TextView contentsView = (TextView) findViewById(R.id.contents);

        dateView.setText(date + "");
        contentsView.setText(contents);
    }
}
