package slogup.ssing;

import android.content.Intent;

import android.os.Bundle;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.slogup.sgcore.CoreActivity;
import com.slogup.sgcore.network.social.FacebookClientHelper;
import com.slogup.sgcore.network.social.SocialClientHelper;

public class MainActivity extends CoreActivity {


    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginWithSocial(new FacebookClientHelper(), new SocialClientHelper.SocialResponseCallback() {
            @Override
            public void onSuccess(String accessToken) {

            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(String errorMsg) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
