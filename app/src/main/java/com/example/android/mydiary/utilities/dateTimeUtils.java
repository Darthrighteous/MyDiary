package com.example.android.mydiary.utilities;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Jalil on 30/06/2018.
 */

public class dateTimeUtils {

    public static long getLocalTime() {

        return System.currentTimeMillis();

    }

    public static String getDateString() {

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm 'on' dd-MM-yyyy ",
                Locale.getDefault());

        Date date = new Date(getLocalTime());

        return sdf.format(date);

    }
}
