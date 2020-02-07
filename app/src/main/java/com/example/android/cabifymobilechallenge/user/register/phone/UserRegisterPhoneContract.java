package com.example.android.cabifymobilechallenge.user.register.phone;

import android.graphics.drawable.Drawable;

public interface UserRegisterPhoneContract {
    interface View {
        void initToolbar(String string);

        void enableWhatsAppButton();

        void enableSmsAppButton();

        void disableWhatsAppButton();

        void disableSmsButton();

        void setupListeners();

        void showEditTextDeleteButton(int drawableResId);

        void hideEditTextDeleteButton();

        void clearEditText();

        String getUserPhone();

        void updateUser(String phone);

        void linkifyAccountKitText(String content, String text, String scheme);

        void showCheck(Drawable drawable);

        void hideCheck();

        void showProgress();

        void hideProgress();

        void showMsg(String text);

        void hideMsg();

        void showToolbar();

        void showPhoneInput();

        void showWhatsAppButton();

        void showSmsButton();

        void showAccountKit();

        void hideToolbar();

        void hidePhoneInput();

        void hideWhatsAppButton();

        void hideSmsButton();

        void hideAccountKit();

        void gotoNextStep(int validationType);
    }

    interface Presenter {
        void start();

        void useWhatsappClicked();

        void useSmsClicked();

        void editTextChanged(CharSequence text);

        void phoneDeleteClicked();
    }
}
