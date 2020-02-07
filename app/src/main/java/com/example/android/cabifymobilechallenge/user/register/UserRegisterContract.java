package com.example.android.cabifymobilechallenge.user.register;

public interface UserRegisterContract {
    interface View {
        void initToolbar(String string);

        void enableNextButton();

        void disableNextButton();

        void setupListeners();

        void showNameDeleteButton(int drawableResId);

        void hideNameDeleteButton();

        void showSurnameDeleteButton(int drawableResId);

        void hideSurnameDeleteButton();

        void showPasswordDeleteButton(int drawableResId);

        void hidePasswordTooShortError();

        void showPasswordTooShortError(int minPasswordLength);

        void hidePasswordDeleteButton();

        void clearNameEditText();

        void clearSurnameEditText();

        void clearPasswordEditText();

        String getName();

        String getSurname();

        String getPassword();

        void updateUser(String name, String surname, String password);

        void gotoNextStep();
    }

    interface Presenter {
        void start();

        void nameEditTextChanged(CharSequence text);

        void surnameEditTextChanged(CharSequence text);

        void passwordEditTextChanged(CharSequence text);

        void nameDeleteClicked();

        void surnameDeleteClicked();

        void passwordDeleteClicked();

        void nextStepClicked();
    }
}
