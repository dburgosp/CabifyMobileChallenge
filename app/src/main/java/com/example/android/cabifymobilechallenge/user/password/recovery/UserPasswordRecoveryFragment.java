package com.example.android.cabifymobilechallenge.user.password.recovery;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
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
import android.widget.Toast;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.user.UserBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserPasswordRecoveryFragment
        extends UserBaseFragment
        implements UserPasswordRecoveryContract.View {

    /* *************** */
    /* Layout elements */
    /* *************** */

    @BindView(R.id.user_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.user_textinputlayout)
    TextInputLayout mTextInputLayout;

    @BindView(R.id.user_edittext)
    EditText mEditText;

    @BindView(R.id.user_text)
    TextView mText;

    @BindView(R.id.user_send_again)
    TextView mSendAgain;

    @BindView(R.id.user_errormsg)
    TextView mErrorMsg;

    @BindView(R.id.user_progress)
    ProgressBar mProgress;

    @BindView(R.id.user_next)
    Button mValidateButton;

    /* ******************************* */
    /* Communication with the Activity */
    /* ******************************* */

    public interface OnInteractionListener {
        void nextStepFromPasswordRecoveryClicked(String email);
    }

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private View mRootView;
    private UserPasswordRecoveryPresenter mPresenter;
    private OnInteractionListener mListener;
    private String mEmail;

    /* ************************************* */
    /* BaseFragment class overridden methods */
    /* ************************************* */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get arguments previously added in the newInstance method.
        if (getArguments() != null) {
            mEmail = getArguments().getString(UserPasswordRecoveryContract.PARAM_EMAIL);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        mRootView = inflater.inflate(
                R.layout.fragment_user_password_recovery, container, false);
        ButterKnife.bind(this, mRootView);

        // Create the presenter and the navigator for this view.
        mPresenter = new UserPasswordRecoveryPresenter(getContext(), this);

        // Ask presenter to initialize all elements in this view.
        mPresenter.start(mEmail);

        return mRootView;
    }

    /* ************** */
    /* Public methods */
    /* ************** */

    /**
     * Required empty public constructor.
     */
    public UserPasswordRecoveryFragment() {
    }

    /**
     * Factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param email is the user e-mail passed as a parameter to this method.
     * @return a new instance of fragment UserPasswordFragment.
     */
    public static UserPasswordRecoveryFragment newInstance(String email) {
        UserPasswordRecoveryFragment fragment = new UserPasswordRecoveryFragment();
        Bundle args = new Bundle();
        args.putString(UserPasswordRecoveryContract.PARAM_EMAIL, email);
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
    /* UserPasswordRecoveryContract.View implementation */
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
     * Write an information text with instructions for the password recovery.
     *
     * @param text is the string to be written into the information text.
     */
    @Override
    public void initText(String text) {
        mText.setText(text);
    }

    /**
     * Set all required listeners for receiving user inputs.
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void setupListeners() {
        mSendAgain.setOnClickListener(v -> {
            if (getContext() != null) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.generate_code_again)
                        .setMessage(R.string.send_new_code_text)
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            // Tell Presenter that user has clicked on "I didn't receive any code" 
                            // link.
                            mPresenter.generateRecoveryCode(mEmail);
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });

        // Tell Presenter that user has clicked on the "Validate" button.
        mValidateButton.setOnClickListener(v -> mPresenter.validateClicked(
                mEditText.getText().toString()));

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Tell Presenter that user has typed something into the EditText.
                mPresenter.codeTextChanged(charSequence);
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
     * Set "Next step" button as enabled so may receive click events from now on.
     */
    @Override
    public void enableNextButton() {
        enableNavigationButton(mValidateButton);
    }

    /**
     * Set "Next step" button as disabled so may not receive click events from now on.
     */
    @Override
    public void disableNextButton() {
        disableNavigationButton(mValidateButton);
    }

    /**
     * Show the progress bar.
     */
    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the progress bar.
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
     * Tell listener that user has typed a valid recovery code and must navigate to the "New
     * password" View.
     */
    @Override
    public void validCodeEntered() {
        mListener.nextStepFromPasswordRecoveryClicked(mEmail);
    }

    /**
     * Show the "Validate" button.
     */
    @Override
    public void showNextButton() {
        mValidateButton.setVisibility(View.VISIBLE);
    }

    /**
     * A new password recovery code has been generated and sent to the user's e-mail.
     */
    @Override
    public void newCodeSent() {
        if (getContext() != null) {
            Toast.makeText(getContext(), getContext().getString(R.string.new_validation_code_sent),
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Hide the "Validate" button.
     */
    @Override
    public void hideNextButton() {
        mValidateButton.setVisibility(View.GONE);
    }
}
