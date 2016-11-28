package slogup.ssing.View;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by sngjoong on 2016. 11. 27..
 */


public class RecyclerViewVerticalSpaceDecoration extends RecyclerView.ItemDecoration {

    private final int verticalSpaceHeight;

    public RecyclerViewVerticalSpaceDecoration(int verticalSpaceHeight) {
        this.verticalSpaceHeight = verticalSpaceHeight;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        outRect.top = verticalSpaceHeight;
    }
}
