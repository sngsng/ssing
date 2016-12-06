package slogup.ssing.View;

import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.slogup.sgcore.manager.AccountManager;
import com.slogup.sgcore.model.User;

import slogup.ssing.Activity.MainActivity;
import slogup.ssing.R;

/**
 * Created by sngjoong on 2016. 11. 27..
 */

public class MainDrawerViewHolder {

    private MainActivity mMainActivity;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private Button mLoginButton;
    private Button mLogoutButton;
    private Button mMyActivityButton;
    private Button mAddPostButton;
    private Button mSettingsButton;

    private TextView mNickTextView;
    private ViewGroup mLoggedInContainer;
    private DrawerMenuButtonCallback mButtonCallback;



    public MainDrawerViewHolder(MainActivity  mainActivity, DrawerMenuButtonCallback buttonCallback) {

        mMainActivity = mainActivity;
        mButtonCallback = buttonCallback;

    }

    public void setButtonCallback(DrawerMenuButtonCallback buttonCallback) {
        mButtonCallback = buttonCallback;
    }

    public boolean isDrawerOpen() {

        return mDrawerLayout.isDrawerOpen(mNavigationView);
    }

    public void toggleDrawer() {

        if (mDrawerLayout.isDrawerOpen(mNavigationView))
            mDrawerLayout.closeDrawer(mNavigationView);
        else
            mDrawerLayout.openDrawer(mNavigationView);
    }

    public void initializeDrawerLayout() {

        bindWidgets();
        setUpActions();
    }

    public void updateDrawerByLoginState() {

        if (AccountManager.getInstance().isLoggedIn(mMainActivity)) {

            mLoginButton.setVisibility(View.GONE);
            mLoggedInContainer.setVisibility(View.VISIBLE);

            User curUser = AccountManager.getInstance().getUser();

            if (curUser != null) {

                mNickTextView.setText(curUser.getNick());
            }

        }
        else {

            mLoginButton.setVisibility(View.VISIBLE);
            mLoggedInContainer.setVisibility(View.GONE);
        }
    }

    private void bindWidgets() {

        mLoginButton = (Button)mMainActivity.findViewById(R.id.main_sign_up_button);
        mLogoutButton = (Button)mMainActivity.findViewById(R.id.main_logout_button);
        mMyActivityButton = (Button)mMainActivity.findViewById(R.id.main_my_activity_button);
        mAddPostButton = (Button)mMainActivity.findViewById(R.id.main_add_post_button);
        mSettingsButton = (Button)mMainActivity.findViewById(R.id.main_settings_button);


        mNickTextView = (TextView)mMainActivity.findViewById(R.id.main_nick_textview);
        mLoggedInContainer = (ViewGroup)mMainActivity.findViewById(R.id.main_logged_in_container);

        mDrawerLayout = (DrawerLayout) mMainActivity.findViewById(R.id.main_drawer_layout);
        mNavigationView = (NavigationView)mMainActivity.findViewById(R.id.main_navigation_view);

        // 백그라운드 Dim 비활성화
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);

        // navigationView half width
        int width = mMainActivity.getResources().getDisplayMetrics().widthPixels/2;
        DrawerLayout.LayoutParams params = (android.support.v4.widget.DrawerLayout.LayoutParams) mNavigationView.getLayoutParams();
        params.width = width;
        mNavigationView.setLayoutParams(params);

        // Drawer Toggle 버튼 설정
        Toolbar toolbar = (Toolbar)mMainActivity.findViewById(R.id.toolbar);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(mMainActivity, mDrawerLayout, toolbar, R.string.title_drawer_toggle_open_desc, R.string.title_drawer_toggle_close_desc);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }

    private void setUpActions() {

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mButtonCallback != null)
                    mButtonCallback.onLoginButtonClick();
            }
        });

        mLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mButtonCallback != null)
                    mButtonCallback.onLogoutButtonClick();
            }
        });

        mMyActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mButtonCallback != null)
                    mButtonCallback.onMyActivityButtonClick();
            }
        });

        mAddPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mButtonCallback != null)
                    mButtonCallback.onAddPostButtonClick();
            }
        });

        mSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mButtonCallback != null)
                    mButtonCallback.onSettingsButtonClick();
            }
        });


    }


    public interface DrawerMenuButtonCallback {

        void onLoginButtonClick();
        void onLogoutButtonClick();
        void onAddPostButtonClick();
        void onMyActivityButtonClick();
        void onSettingsButtonClick();

    }

}
