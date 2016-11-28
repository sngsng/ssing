package slogup.ssing.View;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by sngjoong on 2016. 11. 28..
 */

public class LockableViewPager extends ViewPager {

    private boolean mDisableSwipe;

    public LockableViewPager(Context context) {
        super(context);
    }

    public LockableViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public boolean getDisableSwipe() {
        return mDisableSwipe;
    }

    public void setDisableSwipe(boolean disableSwipe) {
        this.mDisableSwipe = disableSwipe;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return !mDisableSwipe && super.onTouchEvent(event);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return !mDisableSwipe && super.onInterceptTouchEvent(event);
    }

    @Override
    public boolean canScrollHorizontally(int direction) {
        return !mDisableSwipe && super.canScrollHorizontally(direction);
    }

}
