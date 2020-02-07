package com.example.android.cabifymobilechallenge.user.password;

import com.example.android.cabifymobilechallenge.data.pojo.user.User;

public interface UserPasswordContract {
    String PARAM_EMAIL = "PARAM_USER";

    interface View {
        void initToolbar(String title);

        void setupListeners();

        void enableNextButton();

        void disableNextButton();

        String getPassword();

        void showProgress();

        void hideNextButton();

        void hideProgress();

        void showNextButton();

        void hideErrorMsg();

        void showErrorMsg(String message);

        void loginCorrect(User user);

        void clearEditText();

        void showDeleteButton(int drawableResId);

        void hideDeleteButton();

        void recoveryCodeGenerated();
    }

    interface Presenter {
        void start();

        void enterClicked(String email);

        void passwordTextChanged(CharSequence text);

        void editTextImageClicked();

        void forgottenPasswordClicked(String mEmail);
    }
}
