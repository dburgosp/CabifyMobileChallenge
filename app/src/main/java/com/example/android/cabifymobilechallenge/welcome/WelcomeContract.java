package com.example.android.cabifymobilechallenge.welcome;

import com.example.android.cabifymobilechallenge.data.pojo.user.User;

public interface WelcomeContract {
    String PARAM_USER = "PARAM_USER";

    interface View {
        void setWelcomeText(String text);

        void setListeners();

        void closeActivity();
    }

    interface Presenter {
        void start(User user);

        void skipClicked();

        void newAddressClicked();
    }

    interface Navigator {
    }
}
