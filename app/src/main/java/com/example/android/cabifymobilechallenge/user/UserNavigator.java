package com.example.android.cabifymobilechallenge.user;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.example.android.cabifymobilechallenge.common.presenter.BaseNavigator;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;
import com.example.android.cabifymobilechallenge.user.conditions.UserConditionsActivity;
import com.example.android.cabifymobilechallenge.user.conditions.UserConditionsContract;
import com.example.android.cabifymobilechallenge.user.login.UserLoginFragment;
import com.example.android.cabifymobilechallenge.user.password.UserPasswordFragment;
import com.example.android.cabifymobilechallenge.user.password.create.UserPasswordCreateFragment;
import com.example.android.cabifymobilechallenge.user.password.recovery.UserPasswordRecoveryFragment;
import com.example.android.cabifymobilechallenge.user.register.UserRegisterFragment;
import com.example.android.cabifymobilechallenge.user.register.code.UserRegisterCodeFragment;
import com.example.android.cabifymobilechallenge.user.register.phone.UserRegisterPhoneFragment;
import com.example.android.cabifymobilechallenge.welcome.WelcomeActivity;
import com.example.android.cabifymobilechallenge.welcome.WelcomeContract;

public class UserNavigator
        extends BaseNavigator<UserContract.View>
        implements UserContract.Navigator {

    public UserNavigator(Context context, UserContract.View view) {
        super(context, view);
    }

    /* ************************************* */
    /* UserContract.Navigator implementation */
    /* ************************************* */

    /**
     * Load a new {@link UserLoginFragment}.
     *
     * @param fragmentManager is the {@link FragmentManager} to be used for loading fragments.
     * @param containerResId  is the layout resource identifier of the container that will hold the
     *                        new fragment.
     */
    @Override
    public void loadLoginFragment(FragmentManager fragmentManager, int containerResId) {
        UserLoginFragment fragment = new UserLoginFragment();
        fragment.attachListener((UserLoginFragment.OnInteractionListener) mView);
        loadTopFragment(fragmentManager, containerResId, fragment);
    }

    /**
     * Load a new {@link UserPasswordFragment}.
     *
     * @param fragmentManager is the {@link FragmentManager} to be used for loading fragments.
     * @param containerResId  is the layout resource identifier of the container that will hold the
     *                        new fragment.
     * @param email           is the user e-mail to be passed as a parameter to the new fragment.
     */
    @Override
    public void loadPasswordFragment(FragmentManager fragmentManager, int containerResId,
                                     String email) {
        UserPasswordFragment fragment = UserPasswordFragment.newInstance(email);
        fragment.attachListener((UserPasswordFragment.OnInteractionListener) mView);
        loadFragment(fragmentManager, containerResId, fragment);
    }

    /**
     * Load a new {@link UserPasswordRecoveryFragment}.
     *
     * @param fragmentManager is the {@link FragmentManager} to be used for loading fragments.
     * @param containerResId  is the layout resource identifier of the container that will hold the
     *                        new fragment.
     * @param email           is the user e-mail to be passed as a parameter to the new fragment.
     */
    @Override
    public void loadPasswordRecoveryFragment(FragmentManager fragmentManager, int containerResId,
                                             String email) {
        UserPasswordRecoveryFragment fragment = UserPasswordRecoveryFragment.newInstance(email);
        fragment.attachListener((UserPasswordRecoveryFragment.OnInteractionListener) mView);
        loadFragment(fragmentManager, containerResId, fragment);
    }

    /**
     * Load a new {@link UserPasswordCreateFragment}.
     *
     * @param fragmentManager is the {@link FragmentManager} to be used for loading fragments.
     * @param containerResId  is the layout resource identifier of the container that will hold the
     *                        new fragment.
     * @param email           is the user e-mail to be passed as a parameter to the new fragment.
     */
    @Override
    public void loadPasswordCreateFragment(FragmentManager fragmentManager, int containerResId,
                                           String email) {
        UserPasswordCreateFragment fragment = UserPasswordCreateFragment.newInstance(email);
        fragment.attachListener((UserPasswordCreateFragment.OnInteractionListener) mView);
        loadFragment(fragmentManager, containerResId, fragment);
    }

    /**
     * Load a new {@link UserRegisterFragment}.
     *
     * @param fragmentManager is the {@link FragmentManager} to be used for loading fragments.
     * @param containerResId  is the layout resource identifier of the container that will hold the
     *                        new fragment.
     * @param user            is the {@link User} object to be passed as a parameter to the new fragment.
     */
    @Override
    public void loadRegisterFragment(FragmentManager fragmentManager, int containerResId,
                                     User user) {
        UserRegisterFragment fragment = UserRegisterFragment.newInstance(user);
        fragment.attachListener((UserRegisterFragment.OnInteractionListener) mView);
        loadFragment(fragmentManager, containerResId, fragment);
    }

    /**
     * Load a new {@link UserRegisterPhoneFragment}.
     *
     * @param fragmentManager is the {@link FragmentManager} to be used for loading fragments.
     * @param containerResId  is the layout resource identifier of the container that will hold the
     *                        new fragment.
     * @param user            is the {@link User} object containing the new user data so far.
     */
    @Override
    public void loadUserRegisterPhoneFragment(FragmentManager fragmentManager, int containerResId,
                                              User user) {
        UserRegisterPhoneFragment fragment = UserRegisterPhoneFragment.newInstance(user);
        fragment.attachListener((UserRegisterPhoneFragment.OnInteractionListener) mView);
        loadFragment(fragmentManager, containerResId, fragment);
    }

    /**
     * Load a new {@link UserRegisterCodeFragment}.
     *
     * @param fragmentManager is the {@link FragmentManager} to be used for loading fragments.
     * @param containerResId  is the layout resource identifier of the container that will hold the
     *                        new fragment.
     * @param user            is the {@link User} object containing the new user data so far.
     * @param type            is the validation type. Allowed values are
     *                        {@link UserContract#VALIDATION_SMS},
     *                        {@link UserContract#VALIDATION_WHATSAPP}.
     */
    @Override
    public void loadUserRegisterCodeFragment(FragmentManager fragmentManager, int containerResId,
                                             User user, int type) {
        UserRegisterCodeFragment fragment = UserRegisterCodeFragment.newInstance(user, type);
        fragment.attachListener((UserRegisterCodeFragment.OnInteractionListener) mView);
        loadFragment(fragmentManager, containerResId, fragment);
    }

    /**
     * Start UserHelpActivity from UserActivity.
     *
     * @param fromActivity is the {@link Activity} that is trying to load the new one.
     * @param scheme       is the Scheme taken from the calling Intent Data, which will determine
     *                     the content to show in UserHelpActivity.
     */
    @Override
    public void startUserConditionsActivity(Activity fromActivity, String scheme) {
        Activity destinationActivity = new UserConditionsActivity();
        Bundle params = new Bundle();
        params.putString(UserConditionsContract.PARAM_SCHEME, scheme);
        loadActivity(fromActivity, destinationActivity, params);
    }

    /**
     * Start WelcomeActivity from UserActivity.
     *
     * @param fromActivity is the {@link Activity} that is trying to load the new one.
     * @param user         is the {@link User} object containing the new user data.
     */
    @Override
    public void startWelcomeActivity(Activity fromActivity, User user) {
        Activity destinationActivity = new WelcomeActivity();
        Bundle params = new Bundle();
        params.putParcelable(WelcomeContract.PARAM_USER, user);
        loadActivity(fromActivity, destinationActivity, params);
    }
}
