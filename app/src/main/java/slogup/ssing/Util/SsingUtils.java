package slogup.ssing.Util;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;

import slogup.ssing.R;

/**
 * Created by sngjoong on 2016. 11. 29..
 */

public class SsingUtils {

    public static int getTagBackgroundColor(Context context, int position) {

        int[] colors = context.getResources().getIntArray(R.array.colorTagBackgroundRandoms);
        int index = position % colors.length;

        return colors[index];
    }

}
