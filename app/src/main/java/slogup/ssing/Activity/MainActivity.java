package slogup.ssing.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


import com.slogup.sgcore.manager.AccountManager;
import com.slogup.sgcore.network.CoreError;
import com.slogup.sgcore.network.RestClient;
import com.slogup.sgcore.network.core.SessionClientHelper;

import java.util.ArrayList;
import java.util.Arrays;

import slogup.ssing.Adapter.TabPagerAdapter;
import slogup.ssing.Fragment.PostListFragment;
import slogup.ssing.Fragment.SignUpDialogFragment;
import slogup.ssing.Util.CommonUtils;
import slogup.ssing.View.CustomTabLayout;
import slogup.ssing.View.MainDrawerViewHolder;

import slogup.ssing.R;

public class MainActivity extends BaseActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static long sBackPressedTime;
    private static final int BACK_PRESS_TIME_DELAY = 2000;

    private PostListFragment mLatestOrderPostListFragment;
    private PostListFragment mDistanceOrderPostListFragment;
    private MainDrawerViewHolder mMainDrawerViewHolder;
    private SignUpDialogFragment mSignUpDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpDrawer();
        setUpTabLayout();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mMainDrawerViewHolder.updateDrawerByLoginState();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMainDrawerViewHolder.updateDrawerByLoginState();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (mSignUpDialogFragment != null)
            mSignUpDialogFragment.onActivityResult(requestCode, resultCode, data);

        mMainDrawerViewHolder.updateDrawerByLoginState();
    }


    @Override
    public void onBackPressed() {

        if (mMainDrawerViewHolder.isDrawerOpen()) {

            mMainDrawerViewHolder.toggleDrawer();
        }
        else {
            if (sBackPressedTime + BACK_PRESS_TIME_DELAY > System.currentTimeMillis()) {

                super.onBackPressed();

            } else {
                Toast.makeText(getBaseContext(), getString(R.string.title_ask_app_exit),
                        Toast.LENGTH_SHORT).show();
            }
            sBackPressedTime = System.currentTimeMillis();
        }
    }

    private void setUpDrawer() {

        mMainDrawerViewHolder = new MainDrawerViewHolder(this, new MainDrawerViewHolder.DrawerMenuButtonCallback() {
            @Override
            public void onLoginButtonClick() {

                showLogginDialog();
            }

            @Override
            public void onLogoutButtonClick() {

                logout();
            }

            @Override
            public void onAddPostButtonClick() {

                if (!isLoggedIn()) {

                }
            }

            @Override
            public void onMyActivityButtonClick() {

                if (!isLoggedIn()) {

                }
            }

            @Override
            public void onSettingsButtonClick() {

                if (!isLoggedIn()) {

                }
            }
        });
        mMainDrawerViewHolder.initializeDrawerLayout();
    }

    private void setUpTabLayout() {

        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> tabTitles = new ArrayList<>();

        mLatestOrderPostListFragment = PostListFragment.newInstance(PostListFragment.PostListOrderType.Latest);
        mDistanceOrderPostListFragment = PostListFragment.newInstance(PostListFragment.PostListOrderType.Distance);

        fragments.add(mLatestOrderPostListFragment);
        fragments.add(mDistanceOrderPostListFragment);

        String[] titles = getResources().getStringArray(R.array.titles_main_tab);
        tabTitles.addAll(Arrays.asList(titles));

        TabPagerAdapter tabPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        tabPagerAdapter.setFragments(fragments);
        tabPagerAdapter.setTitles(tabTitles);

        ViewPager pager = (ViewPager) findViewById(R.id.main_view_pager);
        pager.setAdapter(tabPagerAdapter);

        CustomTabLayout tabLayout = (CustomTabLayout) findViewById(R.id.main_tab_layout);
        tabLayout.setBoldTypeFaceEnabled(true);
        tabLayout.setupWithViewPager(pager);

    }

    private boolean isLoggedIn() {

        if (AccountManager.getInstance().isLoggedIn(this)) {
            return true;
        }
        else {

            showLogginDialog();
        }

        return false;
    }

    private void showLogginDialog() {

        mSignUpDialogFragment = SignUpDialogFragment.newInstance();
        mSignUpDialogFragment.setDismissCallback(new SignUpDialogFragment.DismissCallback() {
            @Override
            public void onDismiss() {

                mMainDrawerViewHolder.updateDrawerByLoginState();
            }
        });
        mSignUpDialogFragment.show(getSupportFragmentManager(), SignUpDialogFragment.class.getSimpleName());
    }

    private void logout() {

        SessionClientHelper.logout(this, new RestClient.RestListener() {
            @Override
            public void onBefore() {

            }

            @Override
            public void onSuccess(Object response) {

                mMainDrawerViewHolder.updateDrawerByLoginState();
            }

            @Override
            public void onFail(CoreError error) {

                CommonUtils.showToast(getApplicationContext(), error.errorMsg);
            }

            @Override
            public void onError(CoreError error) {

                CommonUtils.showToast(getApplicationContext(), error.errorMsg);
            }
        });
    }
}
