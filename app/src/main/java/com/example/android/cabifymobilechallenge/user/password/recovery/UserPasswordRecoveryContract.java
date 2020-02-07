package com.example.android.cabifymobilechallenge.user.password.recovery;

public interface UserPasswordRecoveryContract {
    String PARAM_EMAIL = "PARAM_USER";

    interface View {
        void initToolbar(String title);

        void initText(String text);

        void setupListeners();

        void clearEditText();

        void showDeleteButton(int drawableResId);

        void enableNextButton();

        void hideDeleteButton();

        void disableNextButton();

        void showProgress();

        void hideProgress();

        void validCodeEntered();

        void showErrorMsg(String message);

        void hideErrorMsg();

        void hideNextButton();

        void showNextButton();

        void newCodeSent();
    }

    interface Presenter {
        void start(String email);

        void editTextImageClicked();

        void codeTextChanged(CharSequence text);

        void generateRecoveryCode(String email);

        void validateClicked(String code);
    }
}
