package slogup.ssing.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.slogup.sgcore.CoreAPIContants;
import com.slogup.sgcore.model.User;
import com.slogup.sgcore.network.CoreError;
import com.slogup.sgcore.network.RestClient;
import com.slogup.sgcore.network.core.SessionClientHelper;
import com.slogup.sgcore.network.social.FacebookClientHelper;
import com.slogup.sgcore.network.social.SocialClientHelper;
import com.slogup.sgcore.util.Utils;

import org.json.JSONException;
import org.json.JSONObject;

import me.relex.circleindicator.CircleIndicator;
import slogup.ssing.Adapter.SignUpPagerAdapter;
import slogup.ssing.R;
import slogup.ssing.Util.CommonUtils;
import slogup.ssing.View.LockableViewPager;

/**
 * Created by sngjoong on 2016. 11. 28..
 */

public class SignUpDialogFragment extends DialogFragment {

    private static final String LOG_TAG = SignUpDialogFragment.class.getSimpleName();

    private LockableViewPager mViewPager;
    private CircleIndicator mIndicator;
    private ViewGroup mLoginContainer;
    private ViewGroup mPageControlContainer;
    private SignUpPagerAdapter mSignUpPagerAdapter;
    private int mCurrentPage = 0;
    private Button mNextButton;
    private Button mPrevButton;
    private Button mFacebookButton;
    private Button mKakaoButton;
    private SocialClientHelper mKakaoClientHelper;
    private SocialClientHelper mFacebookClientHelper;

    private String mPid;
    private String mAccessToken;
    private String mSelectedGender;
    private int mSelectedBirth;
    private String mSelectedNick;
    private User.SocialProviderType mProcessingProviderType;

    private final int[] LAYOUT_RESOURCE_IDS = {R.layout.view_sign_up_gender_input, R.layout.view_sign_up_birth_input, R.layout.view_sign_up_nick_input};

    public static SignUpDialogFragment newInstance() {

        return new SignUpDialogFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i(LOG_TAG, getActivity() + "");
        mFacebookClientHelper = new FacebookClientHelper();
    }

    @Override
    public void onStart()
    {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null)
        {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.WRAP_CONTENT;
            dialog.getWindow().setLayout(width, height);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        mFacebookClientHelper.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialog_fragment_sign_up, container, false);
        bindWidgets(rootView);
        setUpActions();
        setUpViewPager();

        return rootView;
    }

    private void bindWidgets(View rootView) {

        mViewPager = (LockableViewPager)rootView.findViewById(R.id.sign_up_viewpager);
        mIndicator = (CircleIndicator)rootView.findViewById(R.id.sign_up_indicator);
        mLoginContainer = (ViewGroup)rootView.findViewById(R.id.sign_up_login_container);
        mPageControlContainer = (ViewGroup)rootView.findViewById(R.id.sign_up_page_controller_container);
        mNextButton = (Button)rootView.findViewById(R.id.sign_up_next_button);
        mPrevButton = (Button)rootView.findViewById(R.id.sign_up_prev_button);
        mFacebookButton = (Button)rootView.findViewById(R.id.sign_up_facebook_button);
        mKakaoButton = (Button)rootView.findViewById(R.id.sign_up_kakao_button);
    }

    private void loginWithSocial(SocialClientHelper socialClientHelper) {

        SessionClientHelper.loginWithSocial(getActivity(), socialClientHelper, new SessionClientHelper.SocialCallbackListener() {
            @Override
            public void onBefore() {

            }

            @Override
            public void onSuccessLogin(Object response) {

                dismiss();
            }

            @Override
            public void onFailLogin(String pid, String accessKey) {

                mPid = pid;
                mAccessToken = accessKey;
                moveSignUp();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(CoreError error) {

                CommonUtils.showToast(getActivity(), error.errorMsg);
            }
        });
    }

    private void signUp(User.SocialProviderType providerType) {

        User user = new User(getActivity());

        JSONObject optionalParams = new JSONObject();
        try {
            optionalParams.put(CoreAPIContants.User.BIRTH_Y, mSelectedBirth);
            optionalParams.put(CoreAPIContants.User.BIRTH_M, 1);
            optionalParams.put(CoreAPIContants.User.BIRTH_D, 1);
            optionalParams.put(CoreAPIContants.User.NICK, mSelectedNick);
            optionalParams.put(CoreAPIContants.User.GENDER, mSelectedGender);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        user.addSocialUser(providerType, mPid, mAccessToken, optionalParams, new RestClient.RestListener() {
            @Override
            public void onBefore() {

                mNextButton.setEnabled(false);
            }

            @Override
            public void onSuccess(Object response) {

                CommonUtils.showToast(getActivity(), getActivity().getString(R.string.title_finish_sign_up));
                mNextButton.setEnabled(true);
                dismiss();
            }

            @Override
            public void onFail(CoreError error) {

                mNextButton.setEnabled(true);
                CommonUtils.showToast(getActivity(), error.errorMsg);
            }

            @Override
            public void onError(CoreError error) {

                mNextButton.setEnabled(true);
                CommonUtils.showToast(getActivity(), error.errorMsg);
            }
        });
    }

    private void setUpActions() {

        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (mCurrentPage) {

                    case 0:
                        toggleButtonEnabled(false);
                        mSelectedGender = mSignUpPagerAdapter.getSelectedGender();
                        mNextButton.setText(R.string.title_next);
                        mViewPager.setCurrentItem(mCurrentPage + 1, true);
                        break;
                    case 1:
                        toggleButtonEnabled(false);
                        mSelectedBirth = mSignUpPagerAdapter.getSelectedBirth();
                        mNextButton.setText(R.string.title_finish);
                        mViewPager.setCurrentItem(mCurrentPage + 1, true);
                        break;
                    case 2:
                        mSelectedNick = mSignUpPagerAdapter.getInputNickName();
                        if (mSignUpPagerAdapter.validateNick(mSelectedNick)) {

                            signUp(mProcessingProviderType);
                        }
                        break;
                }
            }
       });

        mPrevButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                toggleButtonEnabled(false);

                // 마지막
                if (mCurrentPage == 0) {

                    mNextButton.setText(R.string.title_next);
                    backToLogin();
                }
                else {

                    mNextButton.setText(R.string.title_next);
                    mViewPager.setCurrentItem(mCurrentPage - 1, true);
                }

            }
        });

        mKakaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProcessingProviderType = User.SocialProviderType.KAKAO;
            }
        });

        mFacebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mProcessingProviderType = User.SocialProviderType.FACEBOOK;
                loginWithSocial(mFacebookClientHelper);

            }
        });
    }

    private void toggleButtonEnabled(boolean enabled) {

        mNextButton.setEnabled(enabled);
        mPrevButton.setEnabled(enabled);
    }

    private void setUpViewPager() {

        mSignUpPagerAdapter = new SignUpPagerAdapter(getActivity(), LAYOUT_RESOURCE_IDS);

        mViewPager.setDisableSwipe(true);
        mViewPager.setAdapter(mSignUpPagerAdapter);
        mIndicator.setViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                mCurrentPage = position;
                toggleButtonEnabled(true);
                Log.i(LOG_TAG, "CurrentPage : " + mCurrentPage);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void moveSignUp() {

        if (getDialog() != null) getDialog().setCancelable(false);

        toggleButtonEnabled(true);
        mViewPager.setVisibility(View.VISIBLE);
        mLoginContainer.setVisibility(View.GONE);
        mPageControlContainer.setVisibility(View.VISIBLE);
    }

    private void backToLogin() {

        if (getDialog() != null) getDialog().setCancelable(true);

        toggleButtonEnabled(true);
        mViewPager.setCurrentItem(0);
        mViewPager.setVisibility(View.GONE);
        mLoginContainer.setVisibility(View.VISIBLE);
        mPageControlContainer.setVisibility(View.GONE);
    }
}
