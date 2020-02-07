package com.example.android.cabifymobilechallenge.user.password;

import android.content.Context;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.presenter.BasePresenter;
import com.example.android.cabifymobilechallenge.common.presenter.Callback;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;
import com.example.android.cabifymobilechallenge.user.UserInteractor;

public class UserPasswordPresenter
        extends BasePresenter<UserPasswordContract.View>
        implements UserPasswordContract.Presenter {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private UserInteractor mInteractor;

    /* **************************** */
    /* BasePresenter implementation */
    /* **************************** */

    /**
     * Public constructor.
     *
     * @param context is the {@link Context} of the view that instantiates this class.
     * @param view    is the ViewType view that instantiates this class.
     */
    public UserPasswordPresenter(Context context, UserPasswordContract.View view) {
        super(context, view);

        // Create interactor.
        mInteractor = new UserInteractor(mContext);
    }

    /* ************************************** */
    /* UserContract.Presenter implementation */
    /* ************************************** */

    /**
     * Tell view what to do for displaying its initial state.
     */
    @Override
    public void start() {
        mView.initToolbar(mContext.getString(R.string.user_password_title));
        mView.disableNextButton();
        mView.hideProgress();
        mView.hideErrorMsg();
        mView.hideDeleteButton();
        mView.setupListeners();
    }

    /**
     * Tell View to ask user for the password associated with the typed e-mail.
     */
    @Override
    public void enterClicked(String email) {
        String password = mView.getPassword();
        mView.showProgress();
        mView.hideNextButton();

        // Ask Interactor if the password retrieved from the View corresponds to the user's e-mail
        // or not, waiting for the result in a Callback object.
        mInteractor.userLogin(email, password, new Callback<User>() {
            @Override
            public void returnResult(User user) {
                mView.hideProgress();
                if (!user.getToken().equals("")) {
                    // Interactor has returned a valid session token after successful login. Ask
                    // Interactor to store session data.
                    mInteractor.saveUserId(user.getId());
                    mInteractor.saveEmail(email);
                    mInteractor.savePassword(password);
                    mInteractor.saveToken(user.getToken());

                    // Tell View what to do after a valid login.
                    mView.loginCorrect(user);
                } else {
                    // The returned session token is not valid because password is not correct.
                    mView.showErrorMsg(mContext.getString(R.string.incorrect_password));
                }
            }

            @Override
            public void returnError(String message) {
                // Interactor has returned an error.
                mView.hideProgress();
                mView.showNextButton();
                mView.showErrorMsg(message);
            }
        });
    }

    /**
     * User has typed a password.
     *
     * @param text is the typed password.
     */
    @Override
    public void passwordTextChanged(CharSequence text) {
        if (text != null && text.length() > 0) {
            // Tell View to show the "Delete" button into the EditText.
            mView.showDeleteButton(R.drawable.baseline_highlight_off_24);

            // Password has one character at least. Tell View to enable "Next step" button, so user
            // may go to the next step.
            mView.enableNextButton();
        } else {
            // Password is not valid. Tell View to hide the "Delete" button into the EditText and to
            // disable "Next step" button.
            mView.hideDeleteButton();
            mView.disableNextButton();
        }
    }

    /**
     * User has clicked on the EditText DrawableEnd image.
     */
    @Override
    public void editTextImageClicked() {
        // Tell View to clear EditText content.
        mView.clearEditText();
    }

    /**
     * User has clicked on "I have not received my recovery code", so a new code must be generated.
     *
     * @param email is the e-mail where the new password recovery code is going to be sent.
     */
    @Override
    public void forgottenPasswordClicked(String email) {
        mView.showProgress();
        mView.hideNextButton();

        // Ask Interactor to create a new password recovery code, waiting for the result in a
        // Callback object.
        mInteractor.generateRecoveryCode(email, new Callback<Boolean>() {
            @Override
            public void returnResult(Boolean res) {
                if (res) {
                    // Tell View that a new password recovery code has been generated.
                    mView.hideProgress();
                    mView.showNextButton();
                    mView.recoveryCodeGenerated();
                } else {
                    // Interactor has returned some error while trying to generate the code.
                    mView.hideProgress();
                    mView.enableNextButton();
                    mView.showErrorMsg(
                            mContext.getString(R.string.recovery_password_generation_error));
                }
            }

            @Override
            public void returnError(String message) {
                // Interactor has returned an error.
                mView.hideProgress();
                mView.showNextButton();
                mView.enableNextButton();
                mView.showErrorMsg(message);
            }
        });
    }
}
