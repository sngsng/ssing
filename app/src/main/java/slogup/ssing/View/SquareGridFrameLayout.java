package slogup.ssing.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class SquareGridFrameLayout extends FrameLayout {

    public SquareGridFrameLayout(Context context) {
        super(context);
    }

    public SquareGridFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareGridFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec); // This is the key that will make the height equivalent to its width
    }
}