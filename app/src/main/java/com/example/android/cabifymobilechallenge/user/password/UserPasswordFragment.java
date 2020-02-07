package com.example.android.cabifymobilechallenge.user.password;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;
import com.example.android.cabifymobilechallenge.user.UserBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserPasswordFragment
        extends UserBaseFragment
        implements UserPasswordContract.View {

    /* *************** */
    /* Layout elements */
    /* *************** */

    @BindView(R.id.user_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.user_textinputlayout)
    TextInputLayout mTextInputLayout;

    @BindView(R.id.user_edittext)
    EditText mEditText;

    @BindView(R.id.user_enter)
    Button mEnterButton;

    @BindView(R.id.user_progress)
    ProgressBar mProgress;

    @BindView(R.id.user_errormsg)
    TextView mErrorMsg;

    @BindView(R.id.user_forgotten)
    TextView mForgottenPassword;

    /* ******************************* */
    /* Communication with the Activity */
    /* ******************************* */

    public interface OnInteractionListener {
        void forgottenPasswordClicked(String email);

        void loginCorrect(User user);
    }

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private View mRootView;
    private UserPasswordPresenter mPresenter;
    private OnInteractionListener mListener;
    private String mEmail;

    /* ***************************************** */
    /* UserBaseFragment class overridden methods */
    /* ***************************************** */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get arguments previously added in the newInstance method.
        if (getArguments() != null) {
            mEmail = getArguments().getString(UserPasswordContract.PARAM_EMAIL);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        mRootView = inflater.inflate(
                R.layout.fragment_user_password, container, false);
        ButterKnife.bind(this, mRootView);

        // Create the presenter and the navigator for this view.
        mPresenter = new UserPasswordPresenter(getContext(), this);

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
    public UserPasswordFragment() {
    }

    /**
     * Factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param email is the user e-mail passed as a parameter to this method.
     * @return a new instance of fragment UserPasswordFragment.
     */
    public static UserPasswordFragment newInstance(String email) {
        UserPasswordFragment fragment = new UserPasswordFragment();
        Bundle args = new Bundle();
        args.putString(UserPasswordContract.PARAM_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * @param listener is the Activity that is listening for user interactions on this fragment.
     */
    public void attachListener(OnInteractionListener listener) {
        mListener = listener;
    }

    /* **************************************** */
    /* UserPasswordContract.View implementation */
    /* **************************************** */

    /**
     * Initializes Toolbar setting a title and enabling the back arrow.
     *
     * @param title is the text string to be set as the Toolbar title.
     */
    @Override
    public void initToolbar(String title) {
        setupToolbar(mToolbar, title, true);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void setupListeners() {
        // Tell Presenter that user has clicked on "I have forgot my password" link.
        mForgottenPassword.setOnClickListener(v -> mPresenter.forgottenPasswordClicked(mEmail));

        // Tell Presenter that user has clicked on the "Enter" button.
        mEnterButton.setOnClickListener(v -> mPresenter.enterClicked(mEmail));

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Tell Presenter that user has typed something into the EditText.
                mPresenter.passwordTextChanged(charSequence);
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
     * "Next step" button is enabled and may receive click events from now on.
     */
    @Override
    public void enableNextButton() {
        enableNavigationButton(mEnterButton);
    }

    /**
     * "Next step" button is disabled and may not receive click events from now on.
     */
    @Override
    public void disableNextButton() {
        disableNavigationButton(mEnterButton);
    }

    /**
     * @return the typed password string.
     */
    @Override
    public String getPassword() {
        return mEditText.getText().toString();
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
        mEnterButton.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the "Next step" button.
     */
    @Override
    public void hideNextButton() {
        mEnterButton.setVisibility(View.GONE);
    }

    /**
     * Presenter has told that e-mail and password where correct, user has logged in and we've got
     * a valid {@link User} object. Tell listener that login was successful.
     */
    @Override
    public void loginCorrect(User user) {
        mListener.loginCorrect(user);
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

    /**
     * Tell listener that user has clicked on "I have forgotten my password" and a new password
     * recovery code has been generated and sent to the user's email.
     */
    @Override
    public void recoveryCodeGenerated() {
        mListener.forgottenPasswordClicked(mEmail);
    }
}
