package com.joelimyx.myapplication;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Joe on 11/28/16.
 */

public class Date {
    private String mTitle;
    private long mId,mDate;

    public Date(String title, long date, long id) {
        mTitle = title;
        mDate = date;
        mId = id;
    }

    public long getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDate() {
        DateFormat formatter = DateFormat.getDateTimeInstance(DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mDate);
        return formatter.format(calendar.getTime());
    }
}
