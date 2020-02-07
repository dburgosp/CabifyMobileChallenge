package com.example.android.cabifymobilechallenge.user.password.create;

import com.example.android.cabifymobilechallenge.data.pojo.user.User;

public interface UserPasswordCreateContract {
    String PARAM_EMAIL = "PARAM_USER";

    interface View {
        void initToolbar(String title);

        void setupListeners();

        void clearNewPasswordEditText();

        void showNewPasswordDeleteButton(int drawableResId);

        void hideNewPasswordDeleteButton();

        void clearRepeatPasswordEditText();

        void showRepeatPasswordDeleteButton(int drawableResId);

        void hideRepeatPasswordDeleteButton();

        void enableNextButton();

        void disableNextButton();

        void showProgress();

        void showErrorMsg(String msg);

        void hideProgress();

        void hideErrorMsg();

        void hideRepeatPasswordTooShortError();

        void hideRepeatPasswordNotTheSameError();

        void showRepeatPasswordNotTheSameError();

        void showRepeatPasswordTooShortError(int passLength);

        void hideNewPasswordTooShortError();

        void showNewPasswordTooShortError(int passLength);

        void hideNextButton();

        void showNextButton();

        void loginCorrect(User user);
    }

    interface Presenter {
        void start();

        void newPasswordTextChanged(CharSequence text);

        void newPasswordEditTextImageClicked();

        void repeatPasswordTextChanged(CharSequence repeatPassword, String newPassword);

        void repeatPasswordEditTextImageClicked();

        void enterClicked(String email, String password);
    }
}
