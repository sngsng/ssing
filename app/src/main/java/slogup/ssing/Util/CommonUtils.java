package slogup.ssing.Util;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by sngjoong on 2016. 11. 29..
 */

public class CommonUtils {


    public static void showToast(Context context, String msg) {

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static String arrayParamToString(ArrayList<String> strings) {

        String arrayToStringParam = "";

        if (strings != null && !strings.isEmpty()) {

            for (int i = 0; i < strings.size(); i++) {

                String param = strings.get(i);

                if (i == 0) arrayToStringParam += param;
                else arrayToStringParam += "," + param;

            }
        }

        return arrayToStringParam;
    }
}
