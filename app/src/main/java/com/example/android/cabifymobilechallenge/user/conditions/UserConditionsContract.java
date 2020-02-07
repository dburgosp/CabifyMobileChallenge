package com.example.android.cabifymobilechallenge.user.conditions;

public interface UserConditionsContract {
    String PARAM_SCHEME = "PARAM_SCHEME";

    interface View {
        void initWebView(String url);

        void initToolbar(String title);
    }

    interface Presenter {
        void start(String scheme);
    }
}
