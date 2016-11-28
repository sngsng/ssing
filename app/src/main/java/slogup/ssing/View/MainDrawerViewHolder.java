package slogup.ssing.View;

import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;

import slogup.ssing.Activity.MainActivity;
import slogup.ssing.R;

/**
 * Created by sngjoong on 2016. 11. 27..
 */

public class MainDrawerViewHolder {

    private MainActivity mMainActivity;
    private DrawerLayout mDrawerLayout;

    public MainDrawerViewHolder(MainActivity  mainActivity) {

        mMainActivity = mainActivity;

    }

    public void initializeDrawerLayout() {

        bindWidgets();
    }

    private void bindWidgets() {

        mDrawerLayout = (DrawerLayout) mMainActivity.findViewById(R.id.main_drawer_layout);

        // 백그라운드 Dim 비활성화
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);

        // Drawer Toggle 버튼 설정
        Toolbar toolbar = (Toolbar)mMainActivity.findViewById(R.id.toolbar);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(mMainActivity, mDrawerLayout, toolbar, R.string.title_drawer_toggle_open_desc, R.string.title_drawer_toggle_close_desc);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
    }
}
