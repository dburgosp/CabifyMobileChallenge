package com.example.android.cabifymobilechallenge.user.password.create;

import android.content.Context;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.presenter.BasePresenter;
import com.example.android.cabifymobilechallenge.common.presenter.Callback;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;
import com.example.android.cabifymobilechallenge.user.UserInteractor;

public class UserPasswordCreatePresenter
        extends BasePresenter<UserPasswordCreateContract.View>
        implements UserPasswordCreateContract.Presenter {

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
    public UserPasswordCreatePresenter(Context context, UserPasswordCreateContract.View view) {
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
        mView.initToolbar(mContext.getString(R.string.user_create_password));
        mView.hideProgress();
        mView.hideErrorMsg();
        mView.setupListeners();
        mView.disableNextButton();
    }

    /**
     * User has clicked on the "New password" EditText DrawableEnd image.
     */
    @Override
    public void newPasswordEditTextImageClicked() {
        // Tell View to clear EditText content.
        mView.clearNewPasswordEditText();
    }

    /**
     * User has clicked on the "Repeat password" EditText DrawableEnd image.
     */
    @Override
    public void repeatPasswordEditTextImageClicked() {
        // Tell View to clear EditText content.
        mView.clearRepeatPasswordEditText();
    }

    /**
     * User has clicked "Enter" to change the password.
     *
     * @param email    is the user e-mail.
     * @param password is the user password.
     */
    @Override
    public void enterClicked(String email, String password) {
        mView.showProgress();
        mView.hideNextButton();

        // Use Interactor to perform a login, waiting for the result in a Callback object.
        mInteractor.userLogin(email, password, new Callback<User>() {
            @Override
            public void returnResult(User user) {
                // Interactor has returned a valid session token after successful login. Ask
                // Interactor to store session data.
                mInteractor.saveUserId(user.getId());
                mInteractor.saveEmail(email);
                mInteractor.savePassword(password);
                mInteractor.saveToken(user.getToken());

                // Tell View what to do after a valid login.
                mView.hideProgress();
                mView.loginCorrect(user);
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
     * User has typed a new password.
     *
     * @param text is the typed new password.
     */
    @Override
    public void newPasswordTextChanged(CharSequence text) {
        if (text != null && text.length() > 0) {
            // Typed password has one character at least. Tell View to show the "Delete" button into
            // the corresponding EditText.
            mView.showNewPasswordDeleteButton(R.drawable.baseline_highlight_off_24);

            if (text.length() >= mInteractor.getMinPasswordLength()) {
                // Password has the required length. Tell View to hide error in the corresponding
                // TextInputLayout.
                mView.hideNewPasswordTooShortError();
            } else {
                // Password has not the required length. Tell View to show error in the
                // corresponding TextInputLayout.
                mView.showNewPasswordTooShortError(mInteractor.getMinPasswordLength());
                mView.disableNextButton();
            }
        } else {
            // There is no typed password. Tell View to hide the "Delete" button into the
            // corresponding EditText, because there are no characters to delete.
            mView.hideNewPasswordDeleteButton();
            mView.disableNextButton();
        }
    }

    /**
     * User has typed a password into the "Repeat password" EditText.
     *
     * @param repeatPassword is the password into the "Repeat password" EditText.
     * @param newPassword    is the password into the "New password" EditText.
     */
    @Override
    public void repeatPasswordTextChanged(CharSequence repeatPassword, String newPassword) {
        if (repeatPassword != null && repeatPassword.length() > 0) {
            // Typed password has one character at least. Tell View to show the "Delete" button into
            // the corresponding EditText.
            mView.showRepeatPasswordDeleteButton(R.drawable.baseline_highlight_off_24);

            if (repeatPassword.length() >= mInteractor.getMinPasswordLength()) {
                // Password has the required length. Tell View to hide error in the corresponding
                // TextInputLayout.
                mView.hideRepeatPasswordTooShortError();

                if (repeatPassword.toString().equals(newPassword)) {
                    // Both passwords are the same. Hide errors and enable "Next step" button.
                    mView.hideRepeatPasswordNotTheSameError();
                    mView.enableNextButton();
                } else {
                    // Both passwords are not the same. Show errors and disable "Next step" button.
                    mView.showRepeatPasswordNotTheSameError();
                    mView.disableNextButton();
                }
            } else {
                // Password has not the required length. Tell View to show error in the
                // corresponding TextInputLayout.
                mView.showRepeatPasswordTooShortError(mInteractor.getMinPasswordLength());
                mView.disableNextButton();
            }
        } else {
            // There is no typed password. Tell View to hide the "Delete" button into the
            // corresponding EditText, because there are no characters to delete.
            mView.hideRepeatPasswordDeleteButton();
            mView.disableNextButton();
        }
    }
}
