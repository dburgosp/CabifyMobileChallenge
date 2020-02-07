package com.example.android.cabifymobilechallenge.checkout.address;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.checkout.CheckoutBaseFragment;
import com.example.android.cabifymobilechallenge.checkout.CheckoutContract;
import com.example.android.cabifymobilechallenge.data.pojo.payment.Checkout;
import com.example.android.cabifymobilechallenge.data.pojo.user.Address;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutAddressFragment
        extends CheckoutBaseFragment
        implements CheckoutAddressContract.View {

    /* *************** */
    /* Layout elements */
    /* *************** */

    @BindView(R.id.checkout_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.checkout_addresses_radiogroup)
    RadioGroup mAddressesRadioGroup;

    @BindView(R.id.checkout_addresses_new)
    TextView mNewAddress;

    @BindView(R.id.checkout_next)
    Button mNextButton;

    @BindView(R.id.checkout_progress)
    ProgressBar mProgress;

    /* ******************************* */
    /* Communication with the Activity */
    /* ******************************* */

    public interface OnInteractionListener {
        void onGotoPaymentClicked(Checkout checkout);
    }

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private View mRootView;
    private CheckoutAddressPresenter mPresenter;
    private OnInteractionListener mListener;
    private User mUser;
    private Checkout mCheckout;

    /* ************************************* */
    /* BaseFragment class overridden methods */
    /* ************************************* */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get arguments previously added in the newInstance method.
        if (getArguments() != null) {
            mUser = getArguments().getParcelable(CheckoutContract.PARAM_USER);
            mCheckout = getArguments().getParcelable(CheckoutContract.PARAM_CHECKOUT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        mRootView = inflater.inflate(
                R.layout.fragment_checkout_address, container, false);
        ButterKnife.bind(this, mRootView);

        // Create the presenter for this view.
        mPresenter = new CheckoutAddressPresenter(getContext(), this);

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
    public CheckoutAddressFragment() {
    }

    /**
     * Factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param user     is the {@link User} that has initiated the shopping process.
     * @param checkout is the {@link Checkout} object containing the shopping process data so far.
     * @return a new instance of fragment CheckoutAddressFragment.
     */
    public static CheckoutAddressFragment newInstance(User user, Checkout checkout) {
        CheckoutAddressFragment fragment = new CheckoutAddressFragment();
        Bundle args = new Bundle();
        args.putParcelable(CheckoutContract.PARAM_USER, user);
        args.putParcelable(CheckoutContract.PARAM_CHECKOUT, checkout);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * @param listener is the Activity that is listening for user interactions on this fragment.
     */
    public void attachListener(OnInteractionListener listener) {
        mListener = listener;
    }

    /* ******************************************* */
    /* CheckoutAddressContract.View implementation */
    /* ******************************************* */

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
        // Tell Presenter that user has selected one address.
        mAddressesRadioGroup.setOnCheckedChangeListener((radioGroup, i) ->
                mPresenter.addressSelected(mCheckout, mUser.getAddresses().get(i)));

        // Tell Presenter that user has clicked on the "Next step" button.
        mNextButton.setOnClickListener(view -> mPresenter.nextStepClicked());
    }

    /**
     * Initialize the list of user addresses into a dynamic RadioGroup.
     */
    @Override
    public void setupAddressesList() {
        for (int i = 0; i < mUser.getAddresses().size(); i++) {
            // Get address info.
            Address address = mUser.getAddresses().get(i);
            String text = "<strong>" + address.getAlias() + "</strong><br>" + address.getStreet()
                    + " " + address.getNumber();
            if (address.getStreetInfo() != null && !address.getStreetInfo().equals("")) {
                text += ", " + address.getStreetInfo();
            }
            text += "<br>" + address.getLocation() + " " + address.getPostalCode() + ", " +
                    address.getRegion() + ", " + address.getCountry();

            // Build item.
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setId(i);
            radioButton.setText(Html.fromHtml(text));
            radioButton.setPadding(8, 8, 0, 8);

            // Add item to RadioGroup.
            mAddressesRadioGroup.addView(radioButton);
        }
    }

    /**
     * Get a new {@link Checkout} object from Presenter and update the local one.
     *
     * @param checkout is the {@link Checkout} object.
     */
    @Override
    public void updateCheckOut(Checkout checkout) {
        mCheckout = checkout;
    }

    @Override
    public void gotoPayment() {
        mListener.onGotoPaymentClicked(mCheckout);
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
}
