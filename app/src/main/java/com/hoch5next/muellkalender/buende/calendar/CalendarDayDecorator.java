package com.hoch5next.muellkalender.buende.calendar;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;

import com.hoch5next.muellkalender.buende.R;
import com.squareup.timessquare.CalendarCellDecorator;
import com.squareup.timessquare.CalendarCellView;

import java.util.Date;

/**
 * Created by kekiel on 27.10.15.
 */

public class CalendarDayDecorator implements CalendarCellDecorator {
    @Override
    public void decorate(CalendarCellView cellView, Date date) {
        String dateString = Integer.toString(date.getDate());
        SpannableString string = new SpannableString(dateString + "\nkeks");
        string.setSpan(new RelativeSizeSpan(0.5f), 0, dateString.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        cellView.setText(string);
        if ((date.getDay() % 3 == 0)) {
            cellView.setBackgroundColor(Color.BLUE);
        }
    }
}
