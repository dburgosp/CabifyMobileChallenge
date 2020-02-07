package com.example.android.cabifymobilechallenge.user.register;

import android.content.Context;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.presenter.BasePresenter;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;
import com.example.android.cabifymobilechallenge.user.UserInteractor;

public class UserRegisterPresenter
        extends BasePresenter<UserRegisterContract.View>
        implements UserRegisterContract.Presenter {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private UserInteractor mInteractor;
    private boolean mNameOk = false, mSurnameOk = false, mPasswordOk = false;

    /* **************************** */
    /* BasePresenter implementation */
    /* **************************** */

    /**
     * Public constructor.
     *
     * @param context is the {@link Context} of the view that instantiates this class.
     * @param view    is the ViewType view that instantiates this class.
     */
    public UserRegisterPresenter(Context context, UserRegisterContract.View view) {
        super(context, view);

        // Create interactor.
        mInteractor = new UserInteractor(mContext);
    }

    /* ********************************************* */
    /* UserRegisterContract.Presenter implementation */
    /* ********************************************* */

    /**
     * Tell view what to do for displaying its initial state.
     */
    @Override
    public void start() {
        mView.initToolbar(mContext.getString(R.string.user_register_title));
        mView.disableNextButton();
        mView.hideNameDeleteButton();
        mView.hideSurnameDeleteButton();
        mView.hidePasswordDeleteButton();
        mView.setupListeners();
    }

    /**
     * User has typed a name.
     *
     * @param text is the typed name.
     */
    @Override
    public void nameEditTextChanged(CharSequence text) {
        if (text != null && text.length() > 0) {
            // Typed name has one character at least. Tell View to show the "Delete" button into
            // the corresponding EditText.
            mView.showNameDeleteButton(R.drawable.baseline_highlight_off_24);
            mNameOk = true;

            // Check if "Next Step" button should be enabled.
            if (mSurnameOk && mPasswordOk) {
                mView.enableNextButton();
            }
        } else {
            // There is no typed name. Tell View to hide the "Delete" button into the
            // corresponding EditText, because there are no characters to delete.
            mView.hideNameDeleteButton();
            mView.disableNextButton();
            mNameOk = false;
        }
    }

    /**
     * User has typed a surname.
     *
     * @param text is the typed surname.
     */
    @Override
    public void surnameEditTextChanged(CharSequence text) {
        if (text != null && text.length() > 0) {
            // Typed surname has one character at least. Tell View to show the "Delete" button into
            // the corresponding EditText.
            mView.showSurnameDeleteButton(R.drawable.baseline_highlight_off_24);
            mSurnameOk = true;

            // Check if "Next Step" button should be enabled.
            if (mNameOk && mPasswordOk) {
                mView.enableNextButton();
            }
        } else {
            // There is no typed surname. Tell View to hide the "Delete" button into the
            // corresponding EditText, because there are no characters to delete.
            mView.hideSurnameDeleteButton();
            mView.disableNextButton();
            mSurnameOk = false;
        }
    }

    /**
     * User has typed a password.
     *
     * @param text is the typed password.
     */
    @Override
    public void passwordEditTextChanged(CharSequence text) {
        if (text != null && text.length() > 0) {
            // Typed password has one character at least. Tell View to show the "Delete" button into
            // the corresponding EditText.
            mView.showPasswordDeleteButton(R.drawable.baseline_highlight_off_24);

            if (text.length() >= mInteractor.getMinPasswordLength()) {
                // Password has the required length. Tell View to hide error in the corresponding
                // TextInputLayout.
                mView.hidePasswordTooShortError();
                mPasswordOk = true;

                // Check if "Next Step" button should be enabled.
                if (mNameOk && mSurnameOk) {
                    mView.enableNextButton();
                }
            } else {
                // Password has not the required length. Tell View to show error in the
                // corresponding TextInputLayout.
                mView.showPasswordTooShortError(mInteractor.getMinPasswordLength());
                mView.disableNextButton();
                mPasswordOk = false;
            }
        } else {
            // There is no typed password. Tell View to hide the "Delete" button into the
            // corresponding EditText, because there are no characters to delete.
            mView.hidePasswordDeleteButton();
            mView.disableNextButton();
            mPasswordOk = false;
        }
    }

    /**
     * User has clicked on the "Name" EditText DrawableEnd image.
     */
    @Override
    public void nameDeleteClicked() {
        // Tell View to clear EditText content.
        mView.clearNameEditText();
    }

    /**
     * User has clicked on the "Surname" EditText DrawableEnd image.
     */
    @Override
    public void surnameDeleteClicked() {
        // Tell View to clear EditText content.
        mView.clearSurnameEditText();
    }

    /**
     * User has clicked on the "Password" EditText DrawableEnd image.
     */
    @Override
    public void passwordDeleteClicked() {
        // Tell View to clear EditText content.
        mView.clearPasswordEditText();
    }

    /**
     * User has clicked on "Next step" button. Get typed user name, surname and password from View
     * and tell View to update its local {@link User} object with this info and go to the next
     * registration step.
     */
    @Override
    public void nextStepClicked() {
        mView.updateUser(mView.getName(), mView.getSurname(), mView.getPassword());
        mView.gotoNextStep();
    }
}
