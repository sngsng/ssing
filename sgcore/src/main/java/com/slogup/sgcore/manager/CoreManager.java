package com.slogup.sgcore.manager;

import android.content.Context;

import com.slogup.sgcore.model.Meta;
import com.slogup.sgcore.model.User;

/**
 * Created by sngjoong on 16. 8. 22..
 */
public class CoreManager {

    private static CoreManager sCoreManager = new CoreManager();

    private Meta mMeta;


    public CoreManager() {
    }

    public static CoreManager getInstance() {

        return sCoreManager;
    }

    // Getter & Setter

    public Meta getMeta() {
        return mMeta;
    }

    public void setMeta(Meta meta) {
        mMeta = meta;
    }

    public User getUser() {

        return AccountManager.getInstance().getUser();
    }

}
