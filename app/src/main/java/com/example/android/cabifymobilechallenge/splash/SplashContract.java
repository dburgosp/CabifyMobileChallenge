package com.example.android.cabifymobilechallenge.splash;

import android.app.Activity;

public interface SplashContract {

    interface View {
        void showIntro();

        void setupListeners();

        void navigateToTutorialActivity();

        void navigateToStoreActivity();

        void waitForTimeout();
    }

    interface Presenter {
        void onTimeoutExpired();

        void onViewClicked();

        void start();
    }

    interface Navigator {
        void startTutorialActivity(Activity fromActivity);

        void startStoreActivity(Activity fromActivity);
    }
}
