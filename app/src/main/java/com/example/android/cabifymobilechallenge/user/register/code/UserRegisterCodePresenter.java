package com.example.android.cabifymobilechallenge.user.register.code;

import android.content.Context;
import android.os.Handler;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.presenter.BasePresenter;
import com.example.android.cabifymobilechallenge.common.presenter.Callback;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;
import com.example.android.cabifymobilechallenge.user.UserContract;
import com.example.android.cabifymobilechallenge.user.UserInteractor;

public class UserRegisterCodePresenter
        extends BasePresenter<UserRegisterCodeContract.View>
        implements UserRegisterCodeContract.Presenter {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private UserInteractor mInteractor;

    /**
     * Public constructor.
     *
     * @param context is the {@link Context} of the view that instantiates this class.
     * @param view    is the ViewType view that instantiates this class.
     */
    public UserRegisterCodePresenter(Context context, UserRegisterCodeContract.View view) {
        super(context, view);

        // Create interactor.
        mInteractor = new UserInteractor(mContext);
    }

    /* ************************************************* */
    /* UserRegisterCodeContract.Presenter implementation */
    /* ************************************************* */

    /**
     * Tell view what to do for displaying its initial state.
     *
     * @param user is the {@link User} object containing the new user data so far.
     */
    @Override
    public void start(User user) {
        mView.initToolbar(mContext.getString(R.string.user_registration_code_title));
        mView.initTitleText(mContext.getString(R.string.user_registration_code_text,
                mInteractor.getCodeLength(), user.getPhone()));
        mView.linkifyUserConditionsText(
                mContext.getString(R.string.user_registration_conditions),
                mContext.getString(R.string.user_registration_conditions_1), "fb_conditions:",
                mContext.getString(R.string.user_registration_conditions_2), "fb_privacy:",
                mContext.getString(R.string.user_registration_conditions_3), "fb_cookies:",
                mContext.getString(R.string.user_registration_conditions_4), "privacy:",
                mContext.getString(R.string.user_registration_conditions_5), "conditions:");
        initView();
        mView.disableNextButton();
        mView.setupListeners();
    }

    /**
     * User has clicked on "Send code again" button.
     *
     * @param user is the {@link User} data.
     * @param type is the validation type. Allowed values are {@link UserContract#VALIDATION_SMS},
     *             {@link UserContract#VALIDATION_WHATSAPP}.
     */
    @Override
    public void generateRegisterCode(User user, int type) {
        mView.hideToolbar();
        mView.hideTitleText();
        mView.hideCodeInput();
        mView.hideNextButton();
        mView.hideSendAgain();
        mView.hideConditionsText();
        mView.hideNextButton();
        mView.hideNextLayout();
        mView.hideCodeGenerationImage();
        mView.showCodeGenerationProgress();
        mView.showCodeGenerationMsg(mContext.getString(R.string.user_register_validating));

        switch (type) {
            case UserContract.VALIDATION_WHATSAPP:
                // Ask Interactor for the phone validation by WhatsApp. Wait for the result in a Callback
                // object.
                mInteractor.whatsAppValidation(user.getPhone(), new Callback<Boolean>() {
                    @Override
                    public void returnResult(Boolean isValid) {
                        if (isValid) {
                            // Mobile phone has been validated.
                            validationOk(user.getEmail(), user.getPassword());
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
                break;

            case UserContract.VALIDATION_SMS:
            default:
                // Ask Interactor for the phone validation by SMS. Wait for the result in a Callback
                // object.
                mInteractor.smsValidation(user.getPhone(), new Callback<Boolean>() {
                    @Override
                    public void returnResult(Boolean isValid) {
                        if (isValid) {
                            // Mobile phone has been validated.
                            validationOk(user.getEmail(), user.getPassword());
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
                break;
        }
    }

    /**
     * User has clicked on "Validate" button.
     *
     * @param user is the {@link User} data.
     * @param code is the typed password recovery code.
     */
    @Override
    public void validateClicked(User user, String code) {
        mView.showProgress();
        mView.hideNextButton();

        // Ask Interactor to check the password recovery code that user has typed, waiting for the
        // result in a Callback object.
        mInteractor.checkValidationCode(code, new Callback<Boolean>() {
            @Override
            public void returnResult(Boolean res) {
                if (res) {
                    // Tell View that the code is valid.
                    mView.hideProgress();

                    // Use Interactor to perform a login, waiting for the result in a Callback
                    // object.
                    mInteractor.userLogin(user.getEmail(), user.getPassword(), new Callback<User>() {
                        @Override
                        public void returnResult(User loggedUser) {
                            // Interactor has returned a valid session token after successful login.
                            // Ask Interactor to store session data.
                            mInteractor.saveUserId(loggedUser.getId());
                            mInteractor.saveEmail(user.getEmail());
                            mInteractor.savePassword(user.getPassword());
                            mInteractor.saveUserName(user.getName());
                            mInteractor.saveUserSurname(user.getSurname());
                            mInteractor.saveUserPhone(user.getPhone());
                            mInteractor.saveToken(loggedUser.getToken());

                            // Tell View what to do after a valid login.
                            mView.validCodeEntered();
                        }

                        @Override
                        public void returnError(String message) {
                            // Interactor has returned an error.
                            mView.hideProgress();
                            mView.showNextButton();
                            mView.showErrorMsg(message);
                        }
                    });
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
     * User has clicked on the EditText DrawableEnd image.
     */
    @Override
    public void editTextImageClicked() {
        // Tell View to clear EditText content.
        mView.clearEditText();
    }

    /* ********************** */
    /* Private helper methods */
    /* ********************** */

    /**
     * Set the initial View state.
     */
    private void initView() {
        mView.showNextLayout();
        mView.showToolbar();
        mView.showTitleText();
        mView.showCodeInput();
        mView.showSendAgain();
        mView.showConditionsText();
        mView.showNextButton();
        mView.hideProgress();
        mView.hideCodeGenerationImage();
        mView.hideCodeGenerationProgress();
        mView.hideCodeGenerationMsg();
    }

    /**
     * Actions to ask View to perform when the SMS or WhatsApp phone validation has been successful.
     */
    private void validationOk(String email, String password) {
        mView.hideCodeGenerationProgress();
        mView.showCodeGenerationMsg(mContext.getString(R.string.user_register_valid));
        mView.showCodeGenerationImage(mContext.getDrawable(R.drawable.result_ok));

        // Wait 1 second to let user to read validation result.
        Handler handler = new Handler();
        Runnable runnable = this::initView;
        handler.postDelayed(runnable, 1000);
    }

    /**
     * Actions to ask View to perform when the SMS or WhatsApp phone validation has been
     * unsuccessful.
     */
    private void validationError(String message) {
        mView.hideCodeGenerationProgress();
        mView.showCodeGenerationMsg(message);
        mView.showCodeGenerationImage(mContext.getDrawable(R.drawable.result_error));

        // Wait 1 second to let user reading validation message.
        Handler handler = new Handler();
        Runnable runnable = this::initView;
        handler.postDelayed(runnable, 1000);
    }
}
