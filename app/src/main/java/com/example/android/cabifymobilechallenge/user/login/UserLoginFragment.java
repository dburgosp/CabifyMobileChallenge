package com.example.android.cabifymobilechallenge.user.login;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.user.UserBaseFragment;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserLoginFragment extends UserBaseFragment implements UserLoginContract.View {

    /* *************** */
    /* Layout elements */
    /* *************** */

    @BindView(R.id.user_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.user_textinputlayout)
    TextInputLayout mTextInputLayout;

    @BindView(R.id.user_edittext)
    EditText mEditText;

    @BindView(R.id.user_privacy)
    TextView mPrivacyText;

    @BindView(R.id.user_next)
    Button mNextButton;

    @BindView(R.id.user_progress)
    ProgressBar mProgress;

    @BindView(R.id.user_errormsg)
    TextView mErrorMsg;

    /* ******************************* */
    /* Communication with the Activity */
    /* ******************************* */

    public interface OnInteractionListener {
        void gotoPassword(String email);

        void gotoRegister(String email);
    }

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private View mRootView;
    private UserLoginPresenter mPresenter;
    private OnInteractionListener mListener;

    /* ************************************* */
    /* BaseFragment class overridden methods */
    /* ************************************* */

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        mRootView = inflater.inflate(
                R.layout.fragment_user_login, container, false);
        ButterKnife.bind(this, mRootView);

        // Create the presenter and the navigator for this view.
        mPresenter = new UserLoginPresenter(getContext(), this);

        // Ask presenter to initialize all elements in this view.
        mPresenter.start();

        return mRootView;
    }

    /* ************** */
    /* Public methods */
    /* ************** */

    /**
     * Required empty public constructor.
     */
    public UserLoginFragment() {
    }

    /**
     * @param listener is the Activity that is listening for user interactions on this fragment.
     */
    public void attachListener(OnInteractionListener listener) {
        mListener = listener;
    }

    /* ************************************* */
    /* UserLoginContract.View implementation */
    /* ************************************* */

    /**
     * Initializes Toolbar setting a title and enabling the back arrow.
     *
     * @param title is the text string to be set as the Toolbar title.
     */
    @Override
    public void initToolbar(String title) {
        setupToolbar(mToolbar, title, true);
    }

    /**
     * Set all required listeners for receiving user interactions.
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void setupListeners() {
        // Tell Presenter that user has clicked on the "Next step" button.
        mNextButton.setOnClickListener(view -> mPresenter.nextStepClicked());

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Tell Presenter that user has typed something into the EditText.
                mPresenter.mailTextChanged(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEditText.setOnTouchListener((v, event) -> {
            if (mEditText.getCompoundDrawables()[2] != null) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (mEditText.getRight() -
                            mEditText.getCompoundDrawables()[2].getBounds().width())) {
                        // Tell Presenter that user has clicked on the EditText DrawableEnd image.
                        mPresenter.editTextImageClicked();
                        return true;
                    }
                }
            }
            return false;
        });
    }

    /**
     * Setup Privacy Policy and User General Conditions text and place links to navigate to the
     * corresponding Activities.
     *
     * @param content is the whole Privacy Policy and User General Conditions text.
     * @param text1   is the "Privacy Policy" text pattern.
     * @param scheme1 is the Scheme for identifying the "Privacy Policy" linked text.
     * @param text2   is the "User General Conditions" text pattern.
     * @param scheme2 is the Scheme for identifying the "User General Conditions" linked text.
     */
    @Override
    public void linkifyUserConditionsText(String content, String text1, String scheme1,
                                          String text2, String scheme2) {
        mPrivacyText.setText(content);
        mPrivacyText.setMovementMethod(LinkMovementMethod.getInstance());

        Pattern privacyPolicyMatcher = Pattern.compile(text1);
        Linkify.addLinks(mPrivacyText, privacyPolicyMatcher, scheme1);

        Pattern termsAndConditionsMatcher = Pattern.compile(text2);
        Linkify.addLinks(mPrivacyText, termsAndConditionsMatcher, scheme2);
    }

    /**
     * "Next step" button is enabled and may receive click events from now on.
     */
    @Override
    public void enableNextButton() {
        enableNavigationButton(mNextButton);
    }

    /**
     * "Next step" button is disabled and may not receive click events from now on.
     */
    @Override
    public void disableNextButton() {
        disableNavigationButton(mNextButton);
    }

    /**
     * @return the typed e-mail string.
     */
    @Override
    public String getEmail() {
        return mEditText.getText().toString();
    }

    /**
     * Tell listener that user has clicked on the "Next step" button.
     *
     * @param email is the typed e-mail.
     */
    @Override
    public void askForPassword(String email) {
        mListener.gotoPassword(email);
    }

    /**
     * Tell listener that e-mail is not registered, so we must go to the Register View.
     *
     * @param email is the typed e-mail.
     */
    @Override
    public void initRegister(String email) {
        mListener.gotoRegister(email);
    }

    /**
     * Enable ProgressBar animation.
     */
    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    /**
     * Hide ProgressBar.
     */
    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    /**
     * Show the error message.
     *
     * @param message is the string error message to be shown.
     */
    @Override
    public void showErrorMsg(String message) {
        mErrorMsg.setText(message);
        mErrorMsg.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the error message.
     */
    @Override
    public void hideErrorMsg() {
        mErrorMsg.setText("");
        mErrorMsg.setVisibility(View.GONE);
    }

    /**
     * Show the "Next step" button.
     */
    @Override
    public void showNextButton() {
        mNextButton.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the "Next step" button.
     */
    @Override
    public void hideNextButton() {
        mNextButton.setVisibility(View.GONE);
    }

    /**
     * Clear EditText content.
     */
    @Override
    public void clearEditText() {
        mEditText.setText("");
    }

    /**
     * Show the delete button into the EditText.
     *
     * @param drawableResId is the {@link Drawable} resource identifier of the delete button.
     */
    @Override
    public void showDeleteButton(int drawableResId) {
        showDeleteInEditText(mEditText, drawableResId);
    }

    /**
     * Hide the delete button into the EditText.
     */
    @Override
    public void hideDeleteButton() {
        hideDeleteInEditText(mEditText);
    }
}
