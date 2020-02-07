package com.example.android.cabifymobilechallenge.user.help;

public interface UserHelpContract {
    interface View {
        void initWebView(String url);

        void initToolbar(String title);
    }

    interface Presenter {
        void start();
    }
}
