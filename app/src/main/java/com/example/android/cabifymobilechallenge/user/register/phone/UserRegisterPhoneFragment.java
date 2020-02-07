package com.example.android.cabifymobilechallenge.user.register.phone;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;
import com.example.android.cabifymobilechallenge.user.UserBaseFragment;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserRegisterPhoneFragment
        extends UserBaseFragment
        implements UserRegisterPhoneContract.View {

    private static final String PARAM_USER = "PARAM_USER";

    /* *************** */
    /* Layout elements */
    /* *************** */

    @BindView(R.id.user_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.user_textinputlayout)
    TextInputLayout mTextInputLayout;

    @BindView(R.id.user_edittext)
    EditText mEditText;

    @BindView(R.id.user_account_kit)
    TextView mTextView;

    @BindView(R.id.user_whatsapp)
    FrameLayout mWhatsappButton;

    @BindView(R.id.user_whatsapp_button)
    Button mWhatsapp;

    @BindView(R.id.user_sms)
    Button mSmsButton;

    @BindView(R.id.user_progress)
    ProgressBar mProgressBar;

    @BindView(R.id.user_check)
    ImageView mImageView;

    @BindView(R.id.user_msg)
    TextView mMessage;

    /* ******************************* */
    /* Communication with the Activity */
    /* ******************************* */

    public interface OnInteractionListener {
        void gotoNextStepFromPhoneValidation(User user, int validationType);
    }

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private View mRootView;
    private UserRegisterPhonePresenter mPresenter;
    private OnInteractionListener mListener;
    private User mUser;

    /* ***************************************** */
    /* UserBaseFragment class overridden methods */
    /* ***************************************** */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get arguments previously added in the newInstance method.
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(PARAM_USER);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        mRootView = inflater.inflate(
                R.layout.fragment_user_register_phone, container, false);
        ButterKnife.bind(this, mRootView);

        // Create the presenter and the navigator for this view.
        mPresenter = new UserRegisterPhonePresenter(getContext(), this);

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
    public UserRegisterPhoneFragment() {
    }

    /**
     * Factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param user is the {@link User} object passed as a parameter to this method.
     * @return a new instance of fragment UserRegisterPhoneFragment.
     */
    public static UserRegisterPhoneFragment newInstance(User user) {
        UserRegisterPhoneFragment fragment = new UserRegisterPhoneFragment();
        Bundle args = new Bundle();
        args.putParcelable(PARAM_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * @param listener is the Activity that is listening for user interactions on this fragment.
     */
    public void attachListener(OnInteractionListener listener) {
        mListener = listener;
    }

    /* ********************************************* */
    /* UserRegisterPhoneContract.View implementation */
    /* ********************************************* */

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
        // Tell Presenter that user has clicked on the "Use WhatsApp" or "Use SMS" buttons.
        mWhatsappButton.setOnClickListener(v -> {
            hideKeyboard();
            mPresenter.useWhatsappClicked();
        });
        mWhatsapp.setOnClickListener(v -> {
            hideKeyboard();
            mPresenter.useWhatsappClicked();
        });
        mSmsButton.setOnClickListener(v -> {
            hideKeyboard();
            mPresenter.useSmsClicked();
        });

        // Listen for text changes in the EditText.
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Tell Presenter that user has typed something into the EditText.
                mPresenter.editTextChanged(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Listen for click events on end drawable ("Delete" button).
        mEditText.setOnTouchListener((v, event) -> {
            if (mEditText.getCompoundDrawables()[2] != null) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (mEditText.getRight() -
                            mEditText.getCompoundDrawables()[2].getBounds().width())) {
                        // Tell Presenter that user has clicked on the EditText DrawableEnd image.
                        mPresenter.phoneDeleteClicked();
                        return true;
                    }
                }
            }
            return false;
        });
    }

    /**
     * Setup Account Kit text and place link for navigating to the corresponding Activity.
     *
     * @param content is the whole Account Kit text.
     * @param text    is the "Account Kit" text pattern.
     * @param scheme  is the Scheme for identifying the "Account Kit" linked text.
     */
    @Override
    public void linkifyAccountKitText(String content, String text, String scheme) {
        mTextView.setText(content);
        mTextView.setMovementMethod(LinkMovementMethod.getInstance());

        Pattern accountKitMatcher = Pattern.compile(text);
        Linkify.addLinks(mTextView, accountKitMatcher, scheme);
    }

    /**
     * Show the delete button into the EditText.
     *
     * @param drawableResId is the {@link Drawable} resource identifier of the delete button.
     */
    @Override
    public void showEditTextDeleteButton(int drawableResId) {
        showDeleteInEditText(mEditText, drawableResId);
    }

    /**
     * Hide the delete button into the EditText.
     */
    @Override
    public void hideEditTextDeleteButton() {
        hideDeleteInEditText(mEditText);
    }

    /**
     * Clear EditText content.
     */
    @Override
    public void clearEditText() {
        mEditText.setText("");
    }

    /**
     * @return the content of the EditText.
     */
    @Override
    public String getUserPhone() {
        return mEditText.getText().toString();
    }

    /**
     * "Use WhatsApp" button is enabled and may receive click events from now on.
     */
    @Override
    public void enableWhatsAppButton() {
        mWhatsappButton.setEnabled(true);
        mWhatsappButton.setAlpha(1f);
        mWhatsappButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    /**
     * "Use SMS" button is enabled and may receive click events from now on.
     */
    @Override
    public void enableSmsAppButton() {
        mSmsButton.setEnabled(true);
        mSmsButton.setAlpha(1f);
        mSmsButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    /**
     * "Use WhatsApp" button is disabled and may not receive click events from now on.
     */
    @Override
    public void disableWhatsAppButton() {
        mWhatsappButton.setEnabled(false);
        mWhatsappButton.setAlpha(0.5f);
        mWhatsappButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
    }

    /**
     * "Use SMS" button is disabled and may not receive click events from now on.
     */
    @Override
    public void disableSmsButton() {
        mSmsButton.setEnabled(false);
        mSmsButton.setAlpha(0.5f);
        mSmsButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
    }

    /**
     * Update the local {@link User} object.
     *
     * @param phone is the user mobile phone number.
     */
    @Override
    public void updateUser(String phone) {
        mUser.setPhone(phone);
    }

    /**
     * Show validation image.
     *
     * @param drawable is the {@link Drawable} to be shown into the ImageView.
     */
    @Override
    public void showCheck(Drawable drawable) {
        mImageView.setImageDrawable(drawable);
        mImageView.setVisibility(View.VISIBLE);
    }

    /**
     * Hide check image.
     */
    @Override
    public void hideCheck() {
        mImageView.setVisibility(View.GONE);
    }

    /**
     * Show progress bar.
     */
    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Hide progress bar.
     */
    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * Display a message.
     *
     * @param text is the message to be displayed.
     */
    @Override
    public void showMsg(String text) {
        mMessage.setText(text);
        mMessage.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the message.
     */
    @Override
    public void hideMsg() {
        mMessage.setVisibility(View.GONE);
    }

    @Override
    public void showToolbar() {
        mToolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPhoneInput() {
        mTextInputLayout.setVisibility(View.VISIBLE);
        mEditText.setVisibility(View.VISIBLE);
    }

    @Override
    public void showWhatsAppButton() {
        mWhatsappButton.setVisibility(View.VISIBLE);
        mWhatsapp.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSmsButton() {
        mSmsButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAccountKit() {
        mTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideToolbar() {
        mToolbar.setVisibility(View.GONE);
    }

    @Override
    public void hidePhoneInput() {
        mTextInputLayout.setVisibility(View.GONE);
        mEditText.setVisibility(View.GONE);
    }

    @Override
    public void hideWhatsAppButton() {
        mWhatsappButton.setVisibility(View.GONE);
        mWhatsapp.setVisibility(View.GONE);
    }

    @Override
    public void hideSmsButton() {
        mSmsButton.setVisibility(View.GONE);
    }

    @Override
    public void hideAccountKit() {
        mTextView.setVisibility(View.GONE);
    }

    /**
     * Tell listener to go to the next registration step. Delay 1 seconds to let user to read the
     * validation result.
     */
    @Override
    public void gotoNextStep(int type) {
        Handler handler = new Handler();
        Runnable runnable = () -> mListener.gotoNextStepFromPhoneValidation(mUser, type);
        handler.postDelayed(runnable, 1000);
    }

    /* ********************** */
    /* Private helper methods */
    /* ********************** */

    private void hideKeyboard() {
        InputMethodManager imm =
                (InputMethodManager) getContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }
}