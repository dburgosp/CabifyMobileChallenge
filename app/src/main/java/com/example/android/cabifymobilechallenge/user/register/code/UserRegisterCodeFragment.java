package com.example.android.cabifymobilechallenge.user.register.code;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;
import com.example.android.cabifymobilechallenge.user.UserBaseFragment;
import com.example.android.cabifymobilechallenge.user.UserContract;

import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserRegisterCodeFragment
        extends UserBaseFragment
        implements UserRegisterCodeContract.View {

    final static String PARAM_USER = "PARAM_USER";
    final static String PARAM_TYPE = "PARAM_TYPE";

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
    TextView mTitleText;

    @BindView(R.id.user_accept)
    TextView mConditionsText;

    @BindView(R.id.user_send_again)
    TextView mSendAgain;

    @BindView(R.id.user_errormsg)
    TextView mErrorMsg;

    @BindView(R.id.user_progress)
    ProgressBar mProgress;

    @BindView(R.id.user_next)
    Button mValidateButton;

    @BindView(R.id.user_progress2)
    ProgressBar mCodeGenerationProgress;

    @BindView(R.id.user_check)
    ImageView mCodeGenerationImage;

    @BindView(R.id.user_msg)
    TextView mCodeGenerationMsg;

    @BindView(R.id.user_next_layout)
    RelativeLayout mNextLayout;

    /* ******************************* */
    /* Communication with the Activity */
    /* ******************************* */

    public interface OnInteractionListener {
        void nextStepFromRegisterCodeClicked(User user);
    }

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private View mRootView;
    private UserRegisterCodePresenter mPresenter;
    private OnInteractionListener mListener;
    private User mUser;
    private int mType;

    /* ************************************* */
    /* BaseFragment class overridden methods */
    /* ************************************* */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get arguments previously added in the newInstance method.
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(PARAM_USER);
            mType = getArguments().getInt(PARAM_TYPE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        mRootView = inflater.inflate(
                R.layout.fragment_user_register_code, container, false);
        ButterKnife.bind(this, mRootView);

        // Create the presenter and the navigator for this view.
        mPresenter = new UserRegisterCodePresenter(getContext(), this);

        // Ask presenter to initialize all elements in this view.
        mPresenter.start(mUser);

        return mRootView;
    }

    /* ************** */
    /* Public methods */
    /* ************** */

    /**
     * Required empty public constructor.
     */
    public UserRegisterCodeFragment() {
    }

    /**
     * Factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param user is the {@link User} object containing the new user data so far.
     * @param type is the validation type. Allowed values are {@link UserContract#VALIDATION_SMS},
     *             {@link UserContract#VALIDATION_WHATSAPP}.
     * @return a new instance of fragment UserRegisterCodeFragment.
     */
    public static UserRegisterCodeFragment newInstance(User user, int type) {
        UserRegisterCodeFragment fragment = new UserRegisterCodeFragment();
        Bundle args = new Bundle();
        args.putParcelable(PARAM_USER, user);
        args.putInt(PARAM_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * @param listener is the Activity that is listening for user interactions on this fragment.
     */
    public void attachListener(OnInteractionListener listener) {
        mListener = listener;
    }

    /* ******************************************** */
    /* UserRegisterCodeContract.View implementation */
    /* ******************************************** */

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
     * Write an information text with some user instructions.
     *
     * @param text is the string to be written into the information text.
     */
    @Override
    public void initTitleText(String text) {
        mTitleText.setText(text);
    }

    /**
     * Setup Conditions text and place links to navigate to the corresponding Activities.
     *
     * @param conditions is the whole Conditions text.
     * @param text1      is the "Facebook Conditions" text pattern.
     * @param scheme1    is the Scheme for identifying the "Facebook Conditions" linked text.
     * @param text2      is the "Facebook Data Policy" text pattern.
     * @param scheme2    is the Scheme for identifying the "Facebook Data Policy" linked text.
     * @param text3      is the "Facebook Cookies" text pattern.
     * @param scheme3    is the Scheme for identifying the "Facebook Cookies" linked text.
     * @param text4      is the "Cabify Privacy Policy" text pattern.
     * @param scheme4    is the Scheme for identifying the "Cabify Privacy Policy" linked text.
     * @param text5      is the "Cabify Service Conditions" text pattern.
     * @param scheme5    is the Scheme for identifying the "Cabify Service Conditions" linked text.
     */
    @Override
    public void linkifyUserConditionsText(String conditions, String text1, String scheme1,
                                          String text2, String scheme2, String text3,
                                          String scheme3, String text4, String scheme4,
                                          String text5, String scheme5) {
        mConditionsText.setText(conditions);
        mConditionsText.setMovementMethod(LinkMovementMethod.getInstance());

        Pattern fbConditionsMatcher = Pattern.compile(text1);
        Linkify.addLinks(mConditionsText, fbConditionsMatcher, scheme1);

        Pattern fbDataPolicyMatcher = Pattern.compile(text2);
        Linkify.addLinks(mConditionsText, fbDataPolicyMatcher, scheme2);

        Pattern fbCookiesMatcher = Pattern.compile(text3);
        Linkify.addLinks(mConditionsText, fbCookiesMatcher, scheme3);

        Pattern privacyPolicyMatcher = Pattern.compile(text4);
        Linkify.addLinks(mConditionsText, privacyPolicyMatcher, scheme4);

        Pattern termsAndConditionsMatcher = Pattern.compile(text5);
        Linkify.addLinks(mConditionsText, termsAndConditionsMatcher, scheme5);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void setupListeners() {
        // Listen for user clicks on "Send again" button.
        mSendAgain.setOnClickListener(v -> {
            if (getContext() != null) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.generate_code_again)
                        .setMessage(R.string.send_new_code_text)
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            // Tell Presenter that user has clicked on "I didn't receive any code"
                            // link.
                            mPresenter.generateRegisterCode(mUser, mType);
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
            }
        });

        // Tell Presenter that user has clicked on the "Validate" button.
        mValidateButton.setOnClickListener(v -> mPresenter.validateClicked(
                mUser, mEditText.getText().toString()));

        // Tell Presenter that user has typed something into the code EditText.
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mPresenter.codeTextChanged(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Tell Presenter that user has clicked on the EditText DrawableEnd image.
        mEditText.setOnTouchListener((v, event) -> {
            if (mEditText.getCompoundDrawables()[2] != null) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (mEditText.getRight() -
                            mEditText.getCompoundDrawables()[2].getBounds().width())) {
                        mPresenter.editTextImageClicked();
                        return true;
                    }
                }
            }
            return false;
        });
    }

    @Override
    public void showToolbar() {
        mToolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void showTitleText() {
        mTitleText.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCodeInput() {
        mEditText.setVisibility(View.VISIBLE);
        mTextInputLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showSendAgain() {
        mSendAgain.setVisibility(View.VISIBLE);
    }

    @Override
    public void showConditionsText() {
        mConditionsText.setVisibility(View.VISIBLE);
    }

    @Override
    public void showNextButton() {
        mValidateButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideCodeGenerationImage() {
        mCodeGenerationImage.setVisibility(View.GONE);
    }

    @Override
    public void hideCodeGenerationProgress() {
        mCodeGenerationProgress.setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideCodeGenerationMsg() {
        mCodeGenerationMsg.setVisibility(View.GONE);
    }

    @Override
    public void disableNextButton() {
        disableNavigationButton(mValidateButton);
    }

    @Override
    public void hideToolbar() {
        mToolbar.setVisibility(View.GONE);
    }

    @Override
    public void hideTitleText() {
        mTitleText.setVisibility(View.GONE);
    }

    @Override
    public void hideCodeInput() {
        mEditText.setVisibility(View.GONE);
        mTextInputLayout.setVisibility(View.GONE);
    }

    @Override
    public void hideSendAgain() {
        mSendAgain.setVisibility(View.GONE);
    }

    @Override
    public void hideConditionsText() {
        mConditionsText.setVisibility(View.GONE);
    }

    @Override
    public void hideNextButton() {
        mValidateButton.setVisibility(View.GONE);
    }

    @Override
    public void showCodeGenerationImage(Drawable drawable) {
        mCodeGenerationImage.setImageDrawable(drawable);
        mCodeGenerationImage.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCodeGenerationProgress() {
        mCodeGenerationProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void showCodeGenerationMsg(String text) {
        mCodeGenerationMsg.setText(text);
        mCodeGenerationMsg.setVisibility(View.VISIBLE);
    }

    @Override
    public void enableNextButton() {
        enableNavigationButton(mValidateButton);
    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void showErrorMsg(String text) {
        mErrorMsg.setText(text);
        mErrorMsg.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideErrorMsg() {
        mErrorMsg.setVisibility(View.GONE);
    }

    /**
     * Tell listener that user has typed a valid code and must navigate to the next step.
     */
    @Override
    public void validCodeEntered() {
        mListener.nextStepFromRegisterCodeClicked(mUser);

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
     * Clear EditText content.
     */
    @Override
    public void clearEditText() {
        mEditText.setText("");
    }

    @Override
    public void hideNextLayout() {
        mNextLayout.setVisibility(View.GONE);
    }

    @Override
    public void showNextLayout() {
        mNextLayout.setVisibility(View.VISIBLE);
    }
}
