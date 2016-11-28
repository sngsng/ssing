package slogup.ssing.Util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by sngjoong on 2016. 11. 29..
 */

public class CommonUtils {


    public static void showToast(Context context, String msg) {

        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
