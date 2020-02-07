package com.example.android.cabifymobilechallenge.tutorial;

import android.app.Activity;
import android.content.Context;

import com.example.android.cabifymobilechallenge.store.StoreActivity;
import com.example.android.cabifymobilechallenge.common.presenter.BaseNavigator;

public class TutorialNavigator
        extends BaseNavigator<TutorialContract.View>
        implements TutorialContract.Navigator {

    public TutorialNavigator(Context context, TutorialContract.View view) {
        super(context, view);
    }

    /* ***************************************** */
    /* TutorialContract.Navigator implementation */
    /* ***************************************** */

    /**
     * Start StoreActivity from TutorialActivity.
     *
     * @param fromActivity is the {@link Activity} that is trying to load the new one.
     */
    @Override
    public void startStoreActivity(Activity fromActivity) {
        Activity destinationActivity = new StoreActivity();
        loadActivity(fromActivity, destinationActivity);
    }
}
