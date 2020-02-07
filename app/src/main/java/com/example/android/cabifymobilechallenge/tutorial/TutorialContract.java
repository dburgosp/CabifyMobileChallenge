package com.example.android.cabifymobilechallenge.tutorial;

import android.app.Activity;

import com.example.android.cabifymobilechallenge.data.pojo.Tutorial;

public interface TutorialContract {

    interface View {
        void showTutorial(Tutorial tutorial);

        void setSkipTextToFinish();

        void setSkipTextToSkip();

        void navigateToStoreActivity();

        void setupListeners();
    }

    interface Presenter {
        void start();

        void onSkipClicked();

        void onPageChanged(int oldElementIndex, int newElementIndex);

        void onRightOut();
    }

    interface Interactor {
        Tutorial getTutorial();
    }

    interface Navigator {
        void startStoreActivity(Activity fromActivity);
    }
}
