package com.example.android.mydiary.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by Jalil on 30/06/2018.
 */

public class dateTimeUtils {

    public static long getLocalTime() {
        long nowMillis = System.currentTimeMillis();

        TimeZone timeZone = TimeZone.getDefault();

        int offset = timeZone.getOffset(nowMillis);

        return nowMillis + offset;

    }

    public static String getDateString() {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm 'on' dd-MM-yyyy ");

        Date date = new Date(getLocalTime());

        return sdf.format(date);

    }
}
