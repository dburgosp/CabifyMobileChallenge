package com.example.android.cabifymobilechallenge.user.password.recovery;

import android.content.Context;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.presenter.BasePresenter;
import com.example.android.cabifymobilechallenge.common.presenter.Callback;
import com.example.android.cabifymobilechallenge.user.UserInteractor;

public class UserPasswordRecoveryPresenter
        extends BasePresenter<UserPasswordRecoveryContract.View>
        implements UserPasswordRecoveryContract.Presenter {

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
    public UserPasswordRecoveryPresenter(Context context, UserPasswordRecoveryContract.View view) {
        super(context, view);

        // Create interactor.
        mInteractor = new UserInteractor(mContext);
    }

    /* ***************************************************** */
    /* UserPasswordRecoveryContract.Presenter implementation */
    /* ***************************************************** */

    /**
     * Tell view what to do for displaying its initial state.
     *
     * @param email is the user email where the recovery code has been sent.
     */
    @Override
    public void start(String email) {
        mView.initToolbar(mContext.getString(R.string.user_password_recovery_title));
        mView.initText(mContext.getString(R.string.user_recovery_password_text,
                mInteractor.getCodeLength(), email));
        mView.setupListeners();
        mView.hideProgress();
        mView.hideErrorMsg();
        mView.disableNextButton();
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
     * User has typed a code.
     *
     * @param text is the typed code.
     */
    @Override
    public void codeTextChanged(CharSequence text) {
        if (text != null && text.length() > 0) {
            // Tell View to show the "Delete" button into the EditText.
            mView.showDeleteButton(R.drawable.baseline_highlight_off_24);

            if (text.length() == mInteractor.getCodeLength()) {
                // Code has the required length. Tell View to enable "Next step" button, so user
                // may go to the next step.
                mView.enableNextButton();
            }
        } else {
            // Password is not valid. Tell View to hide the "Delete" button into the EditText and to
            // disable "Next step" button.
            mView.hideDeleteButton();
            mView.disableNextButton();
        }
    }

    /**
     * User has clicked on "I have not received my recovery code", so a new code must be generated.
     *
     * @param email is the e-mail where the new password recovery code is going to be sent.
     */
    @Override
    public void generateRecoveryCode(String email) {
        mView.showProgress();
        mView.hideNextButton();

        // Ask Interactor to create a new password recovery code, waiting for the result in a
        // Callback object.
        mInteractor.generateRecoveryCode(email, new Callback<Boolean>() {
            @Override
            public void returnResult(Boolean res) {
                if (res) {
                    // Tell View that a new valid code has been generated and sent to the user's
                    // e-mail.
                    mView.hideProgress();
                    mView.newCodeSent();
                    mView.showNextButton();
                } else {
                    // Interactor has returned some error while trying to generate the code.
                    mView.hideProgress();
                    mView.showNextButton();
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

    /**
     * User has clicked on "Validate" button.
     *
     * @param code is the typed password recovery code.
     */
    @Override
    public void validateClicked(String code) {
        mView.showProgress();
        mView.hideNextButton();

        // Ask Interactor to check the password recovery code that user has typed, waiting for the
        // result in a Callback object.
        mInteractor.checkRecoveryCode(code, new Callback<Boolean>() {
            @Override
            public void returnResult(Boolean res) {
                if (res) {
                    // Tell View that the code is valid.
                    mView.hideProgress();
                    mView.validCodeEntered();
                } else {
                    // Interactor has returned some error while trying to check the code.
                    mView.hideProgress();
                    mView.showNextButton();
                    mView.enableNextButton();
                    mView.showErrorMsg(
                            mContext.getString(R.string.recovery_password_check_error));
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
