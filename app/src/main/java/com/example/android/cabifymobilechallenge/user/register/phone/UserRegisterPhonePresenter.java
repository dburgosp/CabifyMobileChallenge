package com.example.android.cabifymobilechallenge.user.register.phone;

import android.content.Context;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.presenter.BasePresenter;
import com.example.android.cabifymobilechallenge.common.presenter.Callback;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;
import com.example.android.cabifymobilechallenge.user.UserContract;
import com.example.android.cabifymobilechallenge.user.UserInteractor;

public class UserRegisterPhonePresenter
        extends BasePresenter<UserRegisterPhoneContract.View>
        implements UserRegisterPhoneContract.Presenter {

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
    public UserRegisterPhonePresenter(Context context, UserRegisterPhoneContract.View view) {
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
        mView.initToolbar(mContext.getString(R.string.user_register_phone_title));
        mView.showToolbar();
        mView.showPhoneInput();
        mView.showWhatsAppButton();
        mView.showSmsButton();
        mView.showAccountKit();
        mView.hideCheck();
        mView.hideProgress();
        mView.hideMsg();
        mView.hideEditTextDeleteButton();
        mView.disableWhatsAppButton();
        mView.disableSmsButton();
        mView.setupListeners();
        mView.linkifyAccountKitText(mContext.getString(R.string.user_register_account_kit),
                mContext.getString(R.string.user_register_account_kit_link), "account_kit:");
    }

    /**
     * User has typed a name.
     *
     * @param text is the typed name.
     */
    @Override
    public void editTextChanged(CharSequence text) {
        if (text != null && text.length() > 0) {
            // Typed name has one character at least. Tell View to show the "Delete" button into
            // the corresponding EditText.
            mView.showEditTextDeleteButton(R.drawable.baseline_highlight_off_24);

            // Check if typed phone number is valid.
            if (android.util.Patterns.PHONE.matcher(text).matches()) {
                mView.enableWhatsAppButton();
                mView.enableSmsAppButton();
            } else {
                mView.disableWhatsAppButton();
                mView.disableSmsButton();
            }
        } else {
            // There is no typed name. Tell View to hide the "Delete" button into the
            // corresponding EditText, because there are no characters to delete.
            mView.hideEditTextDeleteButton();
            mView.disableWhatsAppButton();
            mView.disableSmsButton();
        }
    }

    /**
     * User has clicked on the "Phone" EditText DrawableEnd image.
     */
    @Override
    public void phoneDeleteClicked() {
        // Tell View to clear EditText content.
        mView.clearEditText();
    }

    /**
     * User has clicked on "Use WhatsApp" button. Get typed phone from View and tell View to update
     * its local {@link User} object with this info and go to the next registration step.
     */
    @Override
    public void useWhatsappClicked() {
        validationInProgress();

        // Ask Interactor for the phone validation by WhatsApp. Wait for the result in a Callback
        // object.
        mInteractor.whatsAppValidation(mView.getUserPhone(), new Callback<Boolean>() {
            @Override
            public void returnResult(Boolean isValid) {
                if (isValid) {
                    // Mobile phone has been validated.
                    validationOk(UserContract.VALIDATION_WHATSAPP);
                } else {
                    // Mobile phone has not been validated.
                    validationError(mContext.getString(R.string.user_register_not_valid));
                }
            }

            @Override
            public void returnError(String message) {
                // Interactor has returned an error.
                validationError(message);
            }
        });
    }

    /**
     * User has clicked on "Use SMS" button. Get typed phone from View and tell View to update
     * its local {@link User} object with this info and go to the next registration step.
     */
    @Override
    public void useSmsClicked() {
        validationInProgress();

        // Ask Interactor for the phone validation by SMS. Wait for the result in a Callback
        // object.
        mInteractor.smsValidation(mView.getUserPhone(), new Callback<Boolean>() {
            @Override
            public void returnResult(Boolean isValid) {
                if (isValid) {
                    // Mobile phone has been validated.
                    validationOk(UserContract.VALIDATION_SMS);
                } else {
                    // Mobile phone has not been validated.
                    validationError(mContext.getString(R.string.user_register_not_valid));
                }
            }

            @Override
            public void returnError(String message) {
                // Interactor has returned an error.
                validationError(message);
            }
        });
    }

    /* ********************** */
    /* Private helper methods */
    /* ********************** */

    /**
     * Actions to ask View to perform when the SMS or WhatsApp buttons have been clicked.
     */
    private void validationInProgress() {
        mView.hideToolbar();
        mView.hidePhoneInput();
        mView.hideWhatsAppButton();
        mView.hideSmsButton();
        mView.hideAccountKit();
        mView.hideCheck();
        mView.showProgress();
        mView.showMsg(mContext.getString(R.string.user_register_validating));
    }

    /**
     * Actions to ask View to perform when the SMS or WhatsApp phone validation has been successful.
     *
     * @param type is the validation type. Allowed values are {@link UserContract#VALIDATION_SMS},
     *             {@link UserContract#VALIDATION_WHATSAPP}.
     */
    private void validationOk(int type) {
        mView.hideProgress();
        mView.updateUser(mView.getUserPhone());
        mView.showMsg(mContext.getString(R.string.user_register_valid));
        mView.showCheck(mContext.getDrawable(R.drawable.result_ok));
        mView.gotoNextStep(type);
    }

    /**
     * Actions to ask View to perform when the SMS or WhatsApp phone validation has been
     * unsuccessful.
     */
    private void validationError(String message) {
        mView.hideProgress();
        mView.showMsg(message);
        mView.showCheck(mContext.getDrawable(R.drawable.result_error));
    }
}
