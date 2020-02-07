package com.example.android.cabifymobilechallenge.user.login;

import android.content.Context;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.presenter.BasePresenter;
import com.example.android.cabifymobilechallenge.common.presenter.Callback;
import com.example.android.cabifymobilechallenge.user.UserInteractor;

public class UserLoginPresenter
        extends BasePresenter<UserLoginContract.View>
        implements UserLoginContract.Presenter {

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
    public UserLoginPresenter(Context context, UserLoginContract.View view) {
        super(context, view);

        // Create interactor.
        mInteractor = new UserInteractor(mContext);
    }

    /* ************************************** */
    /* UserContract.Presenter implementation */
    /* ************************************** */

    /**
     * Tell view what to do for setting its initial state.
     */
    @Override
    public void start() {
        mView.initToolbar(mContext.getString(R.string.user_login_title));
        mView.linkifyUserConditionsText(
                mContext.getString(R.string.user_accept_conditions),
                mContext.getString(R.string.user_privacy_title), "privacy:",
                mContext.getString(R.string.user_conditions_title), "conditions:");
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
    public void nextStepClicked() {
        String email = mView.getEmail();
        mView.showProgress();
        mView.hideNextButton();

        // Ask Interactor if the e-mail retrieved from the View is registered or not, waiting for
        // the result in a Callback object.
        mInteractor.checkEmail(email, new Callback<Boolean>() {
            @Override
            public void returnResult(Boolean registered) {
                // Interactor has returned information about user's registration.
                mView.hideProgress();
                if (registered) {
                    // User is not registered.
                    mView.askForPassword(email);
                } else {
                    // User is not registered.
                    mView.initRegister(email);
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
     * User has typed an e-mail. Validate if that e-mail is correct.
     *
     * @param text is the typed e-mail.
     */
    @Override
    public void mailTextChanged(CharSequence text) {
        if (text != null && text.length() > 0) {
            // Tell View to show the "Delete" button into the EditText.
            mView.showDeleteButton(R.drawable.baseline_highlight_off_24);
            if (android.util.Patterns.EMAIL_ADDRESS.matcher(text).matches()) {
                // E-mail is valid. Tell View to enable "Next step" button, so user may go to the
                // next step.
                mView.enableNextButton();
            } else {
                // E-mail is not valid. Tell View to disable "Next step" button, so user can not go
                // to the next step.
                mView.disableNextButton();
            }
        } else {
            // Not enough characters. Tell View to hide the "Delete" button into the EditText and to
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
}
