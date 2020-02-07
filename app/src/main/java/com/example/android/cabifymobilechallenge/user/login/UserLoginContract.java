package com.example.android.cabifymobilechallenge.user.login;

public interface UserLoginContract {
    interface View {
        void initToolbar(String title);

        void setupListeners();

        void linkifyUserConditionsText(String content, String text1, String scheme1, String text2,
                                       String scheme2);

        void enableNextButton();

        void disableNextButton();

        String getEmail();

        void askForPassword(String email);

        void showProgress();

        void hideProgress();

        void showErrorMsg(String message);

        void hideErrorMsg();

        void showNextButton();

        void hideNextButton();

        void clearEditText();

        void showDeleteButton(int drawableResId);

        void hideDeleteButton();

        void initRegister(String email);
    }

    interface Presenter {
        void start();

        void nextStepClicked();

        void mailTextChanged(CharSequence charSequence);

        void editTextImageClicked();
    }
}
