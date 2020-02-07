package com.example.android.cabifymobilechallenge.user.password.create;

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

public class UserPasswordCreateFragment
        extends UserBaseFragment
        implements UserPasswordCreateContract.View {

    /* *************** */
    /* Layout elements */
    /* *************** */

    @BindView(R.id.user_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.user_textinputlayout_1)
    TextInputLayout mTextInputLayout1;

    @BindView(R.id.user_edittext_1)
    EditText mEditText1;

    @BindView(R.id.user_textinputlayout_2)
    TextInputLayout mTextInputLayout2;

    @BindView(R.id.user_edittext_2)
    EditText mEditText2;

    @BindView(R.id.user_progress)
    ProgressBar mProgress;

    @BindView(R.id.user_errormsg)
    TextView mErrorMsg;

    @BindView(R.id.user_next)
    Button mCreateButton;

    /* ******************************* */
    /* Communication with the Activity */
    /* ******************************* */

    public interface OnInteractionListener {
        void loginCorrect(User user);
    }

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private View mRootView;
    private UserPasswordCreatePresenter mPresenter;
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
            mEmail = getArguments().getString(UserPasswordCreateContract.PARAM_EMAIL);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        mRootView = inflater.inflate(R.layout.fragment_user_password_create, container, false);
        ButterKnife.bind(this, mRootView);

        // Create the presenter and the navigator for this view.
        mPresenter = new UserPasswordCreatePresenter(getContext(), this);

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
    public UserPasswordCreateFragment() {
    }

    /**
     * Factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param email is the user e-mail passed as a parameter to this method.
     * @return a new instance of fragment UserPasswordFragment.
     */
    public static UserPasswordCreateFragment newInstance(String email) {
        UserPasswordCreateFragment fragment = new UserPasswordCreateFragment();
        Bundle args = new Bundle();
        args.putString(UserPasswordCreateContract.PARAM_EMAIL, email);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * @param listener is the Activity that is listening for user interactions on this fragment.
     */
    public void attachListener(OnInteractionListener listener) {
        mListener = listener;
    }

    /* ************************************************ */
    /* UserPasswordCreateContract.View implementation */
    /* ************************************************ */

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
        // Tell Presenter that user has clicked on the "Enter" button.
        mCreateButton.setOnClickListener(v ->
                mPresenter.enterClicked(mEmail, mEditText1.getText().toString()));

        mEditText1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Tell Presenter that user has typed something into the "new password" EditText.
                mPresenter.newPasswordTextChanged(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEditText2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Tell Presenter that user has typed something into the "Repeat password" EditText.
                // Pass the "New password" content too, so Presenter can check if both passwords are
                // the same.
                mPresenter.repeatPasswordTextChanged(charSequence, mEditText1.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        mEditText1.setOnTouchListener((v, event) -> {
            if (mEditText1.getCompoundDrawables()[2] != null) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (mEditText1.getRight() -
                            mEditText1.getCompoundDrawables()[2].getBounds().width())) {
                        // Tell Presenter that user has clicked on the "new password" EditText
                        // DrawableEnd image.
                        mPresenter.newPasswordEditTextImageClicked();
                        return true;
                    }
                }
            }
            return false;
        });

        mEditText2.setOnTouchListener((v, event) -> {
            if (mEditText2.getCompoundDrawables()[2] != null) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (mEditText2.getRight() -
                            mEditText2.getCompoundDrawables()[2].getBounds().width())) {
                        // Tell Presenter that user has clicked on the "repeat password" EditText
                        // DrawableEnd image.
                        mPresenter.repeatPasswordEditTextImageClicked();
                        return true;
                    }
                }
            }
            return false;
        });
    }

    /**
     * Clear "New password" EditText content.
     */
    @Override
    public void clearNewPasswordEditText() {
        mEditText1.setText("");
    }

    /**
     * Clear "Repeat password" EditText content.
     */
    @Override
    public void clearRepeatPasswordEditText() {
        mEditText2.setText("");
    }

    /**
     * Show the delete button into the "New password" EditText.
     *
     * @param drawableResId is the {@link Drawable} resource identifier of the delete button.
     */
    @Override
    public void showNewPasswordDeleteButton(int drawableResId) {
        showDeleteInEditText(mEditText1, drawableResId);
    }

    /**
     * Show the delete button into the "Repeat password" EditText.
     *
     * @param drawableResId is the {@link Drawable} resource identifier of the delete button.
     */
    @Override
    public void showRepeatPasswordDeleteButton(int drawableResId) {
        showDeleteInEditText(mEditText2, drawableResId);
    }

    /**
     * Hide the delete button into the "New password" EditText.
     */
    @Override
    public void hideNewPasswordDeleteButton() {
        hideDeleteInEditText(mEditText1);
    }

    /**
     * Hide the delete button into the "Repeat password" EditText.
     */
    @Override
    public void hideRepeatPasswordDeleteButton() {
        hideDeleteInEditText(mEditText2);
    }

    /**
     * Set "Next step" button as enabled so may receive click events from now on.
     */
    @Override
    public void enableNextButton() {
        enableNavigationButton(mCreateButton);
    }

    /**
     * Set "Next step" button as disabled so may not receive click events from now on.
     */
    @Override
    public void disableNextButton() {
        disableNavigationButton(mCreateButton);
    }

    /**
     * Show the ProgressBar.
     */
    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the ProgressBar.
     */
    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    /**
     * Show an error message.
     *
     * @param msg is the text error message to be shown.
     */
    @Override
    public void showErrorMsg(String msg) {
        mErrorMsg.setText(msg);
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
     * Typed password is too short. Show a message warning about this.
     *
     * @param passLength is the minimum number of characters that a password must have.
     */
    @Override
    public void showNewPasswordTooShortError(int passLength) {
        mTextInputLayout1.setError(getString(R.string.password_too_short, passLength));
    }

    /**
     * Show the "Create password" button.
     */
    @Override
    public void showNextButton() {
        mCreateButton.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the "Create password" button.
     */
    @Override
    public void hideNextButton() {
        mCreateButton.setVisibility(View.GONE);
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
     * Typed repeated password is too short. Show a message warning about this.
     *
     * @param passLength is the minimum number of characters that a password must have.
     */
    @Override
    public void showRepeatPasswordTooShortError(int passLength) {
        mTextInputLayout2.setError(getString(R.string.password_too_short, passLength));
    }

    /**
     * Both typed passwords are not the same. Show a message warning about this.
     */
    @Override
    public void showRepeatPasswordNotTheSameError() {
        mTextInputLayout2.setError(getString(R.string.password_dont_match));
    }

    /**
     * No errors on new password EditText. Hide error message.
     */
    @Override
    public void hideNewPasswordTooShortError() {
        mTextInputLayout1.setError(null);
    }

    /**
     * No errors on repeated password EditText. Hide error message.
     */
    @Override
    public void hideRepeatPasswordNotTheSameError() {
        mTextInputLayout2.setError(null);
    }

    /**
     * No errors on new password EditText. Hide error message.
     */
    @Override
    public void hideRepeatPasswordTooShortError() {
        mTextInputLayout2.setError(null);
    }
}
