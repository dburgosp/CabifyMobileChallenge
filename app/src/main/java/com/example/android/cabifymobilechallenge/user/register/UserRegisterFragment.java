package com.example.android.cabifymobilechallenge.user.register;

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

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;
import com.example.android.cabifymobilechallenge.user.UserBaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserRegisterFragment
        extends UserBaseFragment
        implements UserRegisterContract.View {

    private static final String PARAM_USER = "PARAM_USER";
    private static final int EDITTEXT_TYPE_NAME = 0;
    private static final int EDITTEXT_TYPE_SURNAME = 1;
    private static final int EDITTEXT_TYPE_PASSWORD = 2;

    /* *************** */
    /* Layout elements */
    /* *************** */

    @BindView(R.id.user_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.user_name_edittext)
    EditText mNameEditText;

    @BindView(R.id.user_surname_edittext)
    EditText mSurnameEditText;

    @BindView(R.id.user_password_textinputlayout)
    TextInputLayout mPasswordTextInputLayout;

    @BindView(R.id.user_password_edittext)
    EditText mPasswordEditText;

    @BindView(R.id.user_enter)
    Button mEnterButton;

    /* ******************************* */
    /* Communication with the Activity */
    /* ******************************* */

    public interface OnInteractionListener {
        void nextStepFromRegisterClicked(User user);
    }

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private View mRootView;
    private UserRegisterPresenter mPresenter;
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
                R.layout.fragment_user_register, container, false);
        ButterKnife.bind(this, mRootView);

        // Create the presenter and the navigator for this view.
        mPresenter = new UserRegisterPresenter(getContext(), this);

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
    public UserRegisterFragment() {
    }

    /**
     * Factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param user is the {@link User} object passed as a parameter to this method.
     * @return a new instance of fragment UserRegisterPhoneFragment.
     */
    public static UserRegisterFragment newInstance(User user) {
        UserRegisterFragment fragment = new UserRegisterFragment();
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

    /* **************************************** */
    /* UserRegisterContract.View implementation */
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
        // Tell Presenter that user has clicked on the "Next Step" button.
        mEnterButton.setOnClickListener(v -> mPresenter.nextStepClicked());

        // Set listeners on EditTexts.
        setupEditTextListener(mNameEditText, EDITTEXT_TYPE_NAME);
        setupEditTextListener(mSurnameEditText, EDITTEXT_TYPE_SURNAME);
        setupEditTextListener(mPasswordEditText, EDITTEXT_TYPE_PASSWORD);
    }

    /**
     * Show the delete button into the "Name" EditText.
     *
     * @param drawableResId is the {@link Drawable} resource identifier of the delete button.
     */
    @Override
    public void showNameDeleteButton(int drawableResId) {
        showDeleteInEditText(mNameEditText, drawableResId);
    }

    /**
     * Show the delete button into the "Surname" EditText.
     *
     * @param drawableResId is the {@link Drawable} resource identifier of the delete button.
     */
    @Override
    public void showSurnameDeleteButton(int drawableResId) {
        showDeleteInEditText(mSurnameEditText, drawableResId);
    }

    /**
     * Show the delete button into the "Password" EditText.
     *
     * @param drawableResId is the {@link Drawable} resource identifier of the delete button.
     */
    @Override
    public void showPasswordDeleteButton(int drawableResId) {
        showDeleteInEditText(mPasswordEditText, drawableResId);
    }

    /**
     * Hide the delete button into the "Name" EditText.
     */
    @Override
    public void hideNameDeleteButton() {
        hideDeleteInEditText(mNameEditText);
    }

    /**
     * Hide the delete button into the "Surname" EditText.
     */
    @Override
    public void hideSurnameDeleteButton() {
        hideDeleteInEditText(mSurnameEditText);
    }

    /**
     * Hide the delete button into the "Password" EditText.
     */
    @Override
    public void hidePasswordDeleteButton() {
        hideDeleteInEditText(mPasswordEditText);
    }

    /**
     * Typed password is too short. Show a message warning about this.
     *
     * @param passLength is the minimum number of characters that a password must have.
     */
    @Override
    public void showPasswordTooShortError(int passLength) {
        mPasswordTextInputLayout.setError(getString(R.string.password_too_short, passLength));
    }

    /**
     * No errors on new password EditText. Hide error message.
     */
    @Override
    public void hidePasswordTooShortError() {
        mPasswordTextInputLayout.setError(null);
    }

    /**
     * Clear "Name" EditText content.
     */
    @Override
    public void clearNameEditText() {
        mNameEditText.setText("");
    }

    /**
     * Clear "Surname" EditText content.
     */
    @Override
    public void clearSurnameEditText() {
        mSurnameEditText.setText("");
    }

    /**
     * Clear "Password" EditText content.
     */
    @Override
    public void clearPasswordEditText() {
        mPasswordEditText.setText("");
    }

    /**
     * @return the content of the "Name" EditText.
     */
    @Override
    public String getName() {
        return mNameEditText.getText().toString();
    }

    /**
     * @return the content of the "Surname" EditText.
     */
    @Override
    public String getSurname() {
        return mSurnameEditText.getText().toString();
    }

    /**
     * @return the typed password string.
     */
    @Override
    public String getPassword() {
        return mPasswordEditText.getText().toString();
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
     * Update the local {@link User} object.
     *
     * @param name     is the user name.
     * @param surname  is the user surname.
     * @param password is the user password.
     */
    @Override
    public void updateUser(String name, String surname, String password) {
        mUser.setName(name);
        mUser.setSurname(surname);
        mUser.setPassword(password);
    }

    /**
     * Tell listener that user has clicked on "Next Step".
     */
    @Override
    public void gotoNextStep() {
        mListener.nextStepFromRegisterClicked(mUser);
    }

    /* ********************** */
    /* Private helper methods */
    /* ********************** */

    /**
     * @param editText is the {@link EditText} view to be setup.
     * @param type     is the EditText type. Allowed values are
     *                 {@link UserRegisterFragment#EDITTEXT_TYPE_NAME},
     *                 {@link UserRegisterFragment#EDITTEXT_TYPE_SURNAME},
     *                 {@link UserRegisterFragment#EDITTEXT_TYPE_PASSWORD}.
     */
    @SuppressLint("ClickableViewAccessibility")
    private void setupEditTextListener(EditText editText, int type) {
        // Listen for text changes in the EditText.
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // Tell Presenter that user has typed something into the EditText.
                switch (type) {
                    case EDITTEXT_TYPE_NAME:
                        mPresenter.nameEditTextChanged(charSequence);
                        break;

                    case EDITTEXT_TYPE_SURNAME:
                        mPresenter.surnameEditTextChanged(charSequence);
                        break;

                    case EDITTEXT_TYPE_PASSWORD:
                    default:
                        mPresenter.passwordEditTextChanged(charSequence);
                        break;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        // Listen for click events on end drawable ("Delete" button).
        editText.setOnTouchListener((v, event) -> {
            if (editText.getCompoundDrawables()[2] != null) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (editText.getRight() -
                            editText.getCompoundDrawables()[2].getBounds().width())) {
                        // Tell Presenter that user has clicked on the EditText DrawableEnd image.
                        switch (type) {
                            case EDITTEXT_TYPE_NAME:
                                mPresenter.nameDeleteClicked();
                                break;

                            case EDITTEXT_TYPE_SURNAME:
                                mPresenter.surnameDeleteClicked();
                                break;

                            case EDITTEXT_TYPE_PASSWORD:
                            default:
                                mPresenter.passwordDeleteClicked();
                                break;
                        }
                        return true;
                    }
                }
            }
            return false;
        });
    }
}