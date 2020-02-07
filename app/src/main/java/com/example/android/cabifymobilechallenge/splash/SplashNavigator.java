package com.example.android.cabifymobilechallenge.splash;

import android.app.Activity;
import android.content.Context;

import com.example.android.cabifymobilechallenge.store.StoreActivity;
import com.example.android.cabifymobilechallenge.common.presenter.BaseNavigator;
import com.example.android.cabifymobilechallenge.tutorial.TutorialActivity;

public class SplashNavigator
        extends BaseNavigator<SplashContract.View>
        implements SplashContract.Navigator {

    public SplashNavigator(Context context, SplashContract.View view) {
        super(context, view);
    }

    /* ***************************************** */
    /* SplashContract.Navigator implementation */
    /* ***************************************** */

    /**
     * Start TutorialActivity from SplashActivity.
     *
     * @param fromActivity is the {@link Activity} that is trying to load the new one.
     */
    @Override
    public void startTutorialActivity(Activity fromActivity) {
        Activity destinationActivity = new TutorialActivity();
        loadActivity(fromActivity, destinationActivity);
    }

    /**
     * Start StoreActivity from SplashActivity.
     *
     * @param fromActivity is the {@link Activity} that is trying to load the new one.
     */
    @Override
    public void startStoreActivity(Activity fromActivity) {
        Activity destinationActivity = new StoreActivity();
        loadActivity(fromActivity, destinationActivity);
    }
}
