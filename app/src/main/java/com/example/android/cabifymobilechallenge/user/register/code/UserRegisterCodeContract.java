package com.example.android.cabifymobilechallenge.user.register.code;

import android.graphics.drawable.Drawable;

import com.example.android.cabifymobilechallenge.data.pojo.user.User;

public interface UserRegisterCodeContract {
    interface View {
        void initToolbar(String text);

        void initTitleText(String text);

        void linkifyUserConditionsText(String conditions, String text1, String scheme1,
                                       String text2, String scheme2, String text3,
                                       String scheme3, String text4, String scheme4,
                                       String text5, String scheme5);

        void setupListeners();

        void showToolbar();

        void showTitleText();

        void showCodeInput();

        void showSendAgain();

        void showConditionsText();

        void showNextButton();

        void hideCodeGenerationImage();

        void hideCodeGenerationProgress();

        void hideCodeGenerationMsg();

        void disableNextButton();

        void hideToolbar();

        void hideTitleText();

        void hideCodeInput();

        void hideSendAgain();

        void hideConditionsText();

        void hideNextButton();

        void showCodeGenerationImage(Drawable drawable);

        void showCodeGenerationProgress();

        void showCodeGenerationMsg(String text);

        void enableNextButton();

        void showProgress();

        void hideProgress();

        void showErrorMsg(String text);

        void hideErrorMsg();

        void validCodeEntered();

        void showDeleteButton(int drawableResId);

        void hideDeleteButton();

        void clearEditText();

        void hideNextLayout();

        void showNextLayout();
    }

    interface Presenter {
        void start(User user);

        void generateRegisterCode(User user, int type);

        void validateClicked(User user, String code);

        void codeTextChanged(CharSequence charSequence);

        void editTextImageClicked();
    }
}
