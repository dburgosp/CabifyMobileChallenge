package com.example.android.cabifymobilechallenge.welcome;

import android.content.Context;

import com.example.android.cabifymobilechallenge.common.presenter.BaseNavigator;

class WelcomeNavigator
        extends BaseNavigator<WelcomeContract.View>
        implements WelcomeContract.Navigator {

    public WelcomeNavigator(Context context, WelcomeContract.View view) {
        super(context, view);
    }

    /* **************************************** */
    /* WelcomeContract.Navigator implementation */
    /* **************************************** */
}
