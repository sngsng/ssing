package slogup.ssing.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.slogup.sgcore.model.Meta;
import com.slogup.sgcore.network.CoreError;
import com.slogup.sgcore.network.RestClient;
import com.slogup.sgcore.network.core.SessionClientHelper;

import slogup.ssing.R;

public class SplashActivity extends AppCompatActivity {

    private static final String LOG_TAG = SplashActivity.class.getSimpleName();
    private ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        bindWidget();
        initializeApp();
    }

    private void bindWidget() {

        mProgressBar = (ProgressBar)findViewById(R.id.splash_progress_bar);
    }

    private void startLoading() {

        mProgressBar.setVisibility(View.VISIBLE);
    }

    private void stopLoading() {

        mProgressBar.setVisibility(View.GONE);
    }

    private void initializeApp() {

        // 메타데이터 로드
        Meta.findOne(SplashActivity.this, new RestClient.RestListener() {
            @Override
            public void onBefore() {

                startLoading();
            }

            @Override
            public void onSuccess(Object response) {


                //세션 확인 (자동 로그인)
                SessionClientHelper.checkLogin(SplashActivity.this, new RestClient.RestListener() {
                    @Override
                    public void onBefore() {

                    }

                    @Override
                    public void onSuccess(Object response) {

                        Log.i(LOG_TAG, "로그인 되어있음");
                        stopLoading();
                        startApp();
                    }

                    @Override
                    public void onFail(CoreError error) {

                        Log.i(LOG_TAG, "로그인 되어있지않음");
                        stopLoading();
                        startApp();
                    }

                    @Override
                    public void onError(CoreError error) {

                        stopLoading();
                    }
                });
            }

            @Override
            public void onFail(CoreError error) {

                stopLoading();
            }

            @Override
            public void onError(CoreError error) {

                stopLoading();
            }
        });
    }

    private void startApp() {

        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
    }
}
