package com.example.android.cabifymobilechallenge.user;

import android.app.Activity;
import android.support.v4.app.FragmentManager;

import com.example.android.cabifymobilechallenge.common.presenter.Callback;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;

public interface UserContract {
    int VALIDATION_WHATSAPP = 1;
    int VALIDATION_SMS = 2;

    interface View {
        void gotoLogin();
    }

    interface Presenter {
        void start();
    }

    interface Navigator {
        void startUserConditionsActivity(Activity fromActivity, String scheme);

        void loadLoginFragment(FragmentManager fragmentManager, int containerResId);

        void loadPasswordFragment(FragmentManager fragmentManager, int containerResId,
                                  String email);

        void loadPasswordRecoveryFragment(FragmentManager fragmentManager, int containerResId,
                                          String email);

        void loadPasswordCreateFragment(FragmentManager fragmentManager, int containerResId,
                                        String email);

        void loadRegisterFragment(FragmentManager fragmentManager, int containerResId,
                                  User user);

        void loadUserRegisterPhoneFragment(FragmentManager fragmentManager, int containerResId,
                                           User user);

        void loadUserRegisterCodeFragment(FragmentManager fragmentManager, int containerResId,
                                          User user, int type);

        void startWelcomeActivity(Activity fromActivity, User user);
    }

    interface Interactor {
        void checkEmail(String email, Callback<Boolean> callback);

        void userLogin(String email, String password, Callback<User> callback);

        void generateRecoveryCode(String email, Callback<Boolean> callback);

        int getMinPasswordLength();

        int getCodeLength();

        void checkRecoveryCode(String code, Callback<Boolean> callback);

        void whatsAppValidation(String phone, Callback<Boolean> callback);

        void smsValidation(String phone, Callback<Boolean> callback);

        void checkValidationCode(String code, Callback<Boolean> callback);
    }
}
