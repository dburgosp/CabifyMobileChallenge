package com.example.android.cabifymobilechallenge.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.view.BaseActivity;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;
import com.example.android.cabifymobilechallenge.user.login.UserLoginFragment;
import com.example.android.cabifymobilechallenge.user.password.UserPasswordFragment;
import com.example.android.cabifymobilechallenge.user.password.create.UserPasswordCreateFragment;
import com.example.android.cabifymobilechallenge.user.password.recovery.UserPasswordRecoveryFragment;
import com.example.android.cabifymobilechallenge.user.register.UserRegisterFragment;
import com.example.android.cabifymobilechallenge.user.register.code.UserRegisterCodeFragment;
import com.example.android.cabifymobilechallenge.user.register.phone.UserRegisterPhoneFragment;
import com.example.android.cabifymobilechallenge.welcome.WelcomeActivity;

public class UserActivity extends BaseActivity
        implements UserContract.View, UserLoginFragment.OnInteractionListener,
        UserPasswordFragment.OnInteractionListener,
        UserPasswordRecoveryFragment.OnInteractionListener,
        UserPasswordCreateFragment.OnInteractionListener,
        UserRegisterFragment.OnInteractionListener,
        UserRegisterPhoneFragment.OnInteractionListener,
        UserRegisterCodeFragment.OnInteractionListener {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private UserPresenter mPresenter;
    private UserNavigator mNavigator;

    /* ******************************* */
    /* BaseActivity overridden methods */
    /* ******************************* */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        // Create the presenter and the navigator for this view.
        mPresenter = new UserPresenter(this, this);
        mNavigator = new UserNavigator(this, this);

        // Ask presenter to initialize all elements in this Activity.
        mPresenter.start();
    }

    @Override
    public void startActivity(Intent intent) {
        if (TextUtils.equals(intent.getAction(), Intent.ACTION_VIEW)) {
            if (intent.getData() != null && intent.getData().getScheme() != null &&
                    mNavigator != null) {
                String scheme = intent.getData().getScheme();
                mNavigator.startUserConditionsActivity(this, scheme);
            }
        } else {
            super.startActivity(intent);
        }
    }

    /* ******************************** */
    /* UserContract.View implementation */
    /* ******************************** */

    /**
     * Load {@link UserLoginFragment}.
     */
    @Override
    public void gotoLogin() {
        mNavigator.loadLoginFragment(getSupportFragmentManager(), R.id.user_container);
    }

    /* ****************************************************** */
    /* UserLoginFragment.OnInteractionListener implementation */
    /* ****************************************************** */

    /**
     * Load {@link UserPasswordFragment}.
     *
     * @param email is the typed user e-mail.
     */
    @Override
    public void gotoPassword(String email) {
        mNavigator.loadPasswordFragment(getSupportFragmentManager(), R.id.user_container, email);
    }

    /**
     * Create a new empty {@link User} object and load {@link UserRegisterFragment} to begin the
     * registration process.
     *
     * @param email is the typed user e-mail.
     */
    @Override
    public void gotoRegister(String email) {
        User user = new User(email);
        mNavigator.loadRegisterFragment(getSupportFragmentManager(), R.id.user_container, user);
    }

    /* ********************************************************* */
    /* UserPasswordFragment.OnInteractionListener implementation */
    /* ********************************************************* */

    /**
     * Load {@link UserPasswordRecoveryFragment}.
     *
     * @param email is the typed user e-mail.
     */
    @Override
    public void forgottenPasswordClicked(String email) {
        mNavigator.loadPasswordRecoveryFragment(getSupportFragmentManager(), R.id.user_container,
                email);
    }

    /**
     * User has logged in. Close this activity.
     *
     * @param user is the {@link User} object containing all the data of the logged user.
     */
    @Override
    public void loginCorrect(User user) {
        Toast.makeText(this, getString(R.string.user_login_welcome, user.getName()),
                Toast.LENGTH_SHORT).show();

        // Tell calling activity that everything is ok before finishing.
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    /* ***************************************************************** */
    /* UserPasswordRecoveryFragment.OnInteractionListener implementation */
    /* ***************************************************************** */

    /**
     * A valid recovery code has been entered. Load UserPasswordCreateFragment.
     *
     * @param email is the user e-mail.
     */
    @Override
    public void nextStepFromPasswordRecoveryClicked(String email) {
        mNavigator.loadPasswordCreateFragment(getSupportFragmentManager(), R.id.user_container,
                email);
    }

    /* ********************************************************* */
    /* UserRegisterFragment.OnInteractionListener implementation */
    /* ********************************************************* */

    /**
     * Load {@link UserRegisterPhoneFragment}.
     *
     * @param user is the {@link User} object containing the new user data so far.
     */
    @Override
    public void nextStepFromRegisterClicked(User user) {
        mNavigator.loadUserRegisterPhoneFragment(getSupportFragmentManager(), R.id.user_container,
                user);
    }

    /* ************************************************************** */
    /* UserRegisterPhoneFragment.OnInteractionListener implementation */
    /* ************************************************************** */

    /**
     * Load {@link UserRegisterCodeFragment}.
     *
     * @param user is the {@link User} object containing the new user data so far.
     * @param type is the validation type. Allowed values are {@link UserContract#VALIDATION_SMS},
     *             {@link UserContract#VALIDATION_WHATSAPP}.
     */
    @Override
    public void gotoNextStepFromPhoneValidation(User user, int type) {
        mNavigator.loadUserRegisterCodeFragment(getSupportFragmentManager(),
                R.id.user_container, user, type);
    }

    /* ************************************************************* */
    /* UserRegisterCodeFragment.OnInteractionListener implementation */
    /* ************************************************************* */

    /**
     * Load {@link WelcomeActivity} and close this one.
     *
     * @param user is the {@link User} object containing all the new user data.
     */
    @Override
    public void nextStepFromRegisterCodeClicked(User user) {
        mNavigator.startWelcomeActivity(this, user);
        finish();
    }
}
