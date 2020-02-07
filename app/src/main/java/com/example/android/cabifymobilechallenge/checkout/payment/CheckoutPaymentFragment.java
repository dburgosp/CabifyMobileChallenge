package com.example.android.cabifymobilechallenge.checkout.payment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.checkout.CheckoutBaseFragment;
import com.example.android.cabifymobilechallenge.checkout.CheckoutContract;
import com.example.android.cabifymobilechallenge.data.pojo.payment.Checkout;
import com.example.android.cabifymobilechallenge.data.pojo.payment.PaymentMethod;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CheckoutPaymentFragment
        extends CheckoutBaseFragment
        implements CheckoutPaymentContract.View {

    /* *************** */
    /* Layout elements */
    /* *************** */

    @BindView(R.id.checkout_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.checkout_payment_radiogroup)
    RadioGroup mPaymentRadioGroup;

    @BindView(R.id.checkout_next)
    Button mNextButton;

    @BindView(R.id.checkout_progress)
    ProgressBar mProgress;

    /* ******************************* */
    /* Communication with the Activity */
    /* ******************************* */

    public interface OnInteractionListener {
        void onGotoSummaryClicked(Checkout checkout);
    }

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private View mRootView;
    private CheckoutPaymentPresenter mPresenter;
    private OnInteractionListener mListener;
    private Checkout mCheckout;

    /* ************************************* */
    /* BaseFragment class overridden methods */
    /* ************************************* */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get arguments previously added in the newInstance method.
        if (getArguments() != null) {
            mCheckout = getArguments().getParcelable(CheckoutContract.PARAM_CHECKOUT);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        mRootView = inflater.inflate(
                R.layout.fragment_checkout_payment, container, false);
        ButterKnife.bind(this, mRootView);

        // Create the presenter for this view.
        mPresenter = new CheckoutPaymentPresenter(getContext(), this);

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
    public CheckoutPaymentFragment() {
    }

    /**
     * Factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param checkout is the {@link Checkout} object containing the shopping process data so far.
     * @return a new instance of fragment CheckoutPaymentFragment.
     */
    public static CheckoutPaymentFragment newInstance(Checkout checkout) {
        CheckoutPaymentFragment fragment = new CheckoutPaymentFragment();
        Bundle args = new Bundle();
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
    /* CheckoutPaymentContract.View implementation */
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
        mPaymentRadioGroup.setOnCheckedChangeListener((radioGroup, i) ->
                mPresenter.paymentMethodSelected(mCheckout, i));

        // Tell Presenter that user has clicked on the "Next step" button.
        mNextButton.setOnClickListener(view -> mPresenter.nextStepClicked());
    }

    /**
     * Initialize the list of user addresses into a dynamic RadioGroup.
     */
    @Override
    public void setupPaymentMethodsList(ArrayList<PaymentMethod> paymentMethods) {
        for (int i = 0; i < paymentMethods.size(); i++) {
            // Build item.
            RadioButton radioButton = new RadioButton(getContext());
            radioButton.setId(i);
            radioButton.setText(paymentMethods.get(i).getName());
            radioButton.setPadding(8, 8, 0, 8);

            // Add item to RadioGroup.
            mPaymentRadioGroup.addView(radioButton);
        }
    }

    /**
     * Tell listener that "Next step" button has been clicked.
     */
    @Override
    public void gotoSummary() {
        mListener.onGotoSummaryClicked(mCheckout);
    }

    /**
     * Get an updated {@link Checkout} object from Presenter and overwrite the local one.
     *
     * @param checkout is the {@link Checkout} object.
     */
    @Override
    public void updateCheckout(Checkout checkout) {
        mCheckout = checkout;
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
