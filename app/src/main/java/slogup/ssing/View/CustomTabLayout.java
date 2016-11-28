package slogup.ssing.View;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.ViewGroup;

/**
 * Created by sngjoong on 2016. 11. 27..
 */

public class CustomTabLayout extends TabLayout {

    private boolean mIsBoldTypeFaceEnabled = false;
    private float mFontSize = 18.0f;

    public CustomTabLayout(Context context) {
        super(context);
    }

    public CustomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setBoldTypeFaceEnabled(boolean boldTypeFaceEnabled) {
        this.mIsBoldTypeFaceEnabled = boldTypeFaceEnabled;
    }

    public void setFontSize(float fontSize) {
        mFontSize = fontSize;
    }

    @Override
    public void setupWithViewPager(@Nullable ViewPager viewPager) {
        super.setupWithViewPager(viewPager);

        if (mIsBoldTypeFaceEnabled) {

            this.removeAllTabs();

            ViewGroup slidingTabStrip = (ViewGroup) getChildAt(0);

            PagerAdapter adapter = viewPager.getAdapter();

            for (int i = 0, count = adapter.getCount(); i < count; i++) {
                Tab tab = this.newTab();
                this.addTab(tab.setText(adapter.getPageTitle(i)));
                AppCompatTextView view = (AppCompatTextView) ((ViewGroup) slidingTabStrip.getChildAt(i)).getChildAt(1);
                view.setTextSize(mFontSize);
                view.setTypeface(view.getTypeface(), Typeface.BOLD);
            }
        }
    }
}
