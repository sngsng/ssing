package slogup.ssing.Util;


import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sngjoong on 2016. 11. 28..
 */

public class TimeUtils {


    private static String toSimpleDateStringFormat(long milliseconds) {

        Date date = new Date();
        date.setTime(milliseconds);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd a h:mm", Locale.KOREA);
        return dateFormat.format(date);
    }

    public static String toSimplePastTimeStringFormat(long microSeconds) {

        Log.i("ss", microSeconds + "초");
        long milliseconds = microSeconds / 1000;
        long curMills = System.currentTimeMillis();
        int seconds = (int)((curMills - milliseconds) / 1000);

        int secs  = seconds % 60;
        int mins = (seconds / 60) % 60;
        int hours = (seconds / 60 / 60) % 24;
        int days =  seconds / (60 * 60 * 24);

        if (days > 0) {

            return toSimpleDateStringFormat(milliseconds);
        }
        else if (hours > 0) {
            return String.format(Locale.KOREA, "%d시간 전", hours);
        }
        else if (mins > 0) {
            return String.format(Locale.KOREA, "%d분 전", mins);
        }
        else {

            return "방금 전";
        }

    }
}
