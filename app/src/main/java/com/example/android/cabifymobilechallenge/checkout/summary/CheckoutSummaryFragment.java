package com.example.android.cabifymobilechallenge.checkout.summary;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.checkout.CheckoutBaseFragment;
import com.example.android.cabifymobilechallenge.checkout.CheckoutContract;
import com.example.android.cabifymobilechallenge.data.pojo.payment.Checkout;
import com.example.android.cabifymobilechallenge.data.pojo.payment.TransactionData;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.ShoppingCartItem;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class CheckoutSummaryFragment
        extends CheckoutBaseFragment
        implements CheckoutSummaryContract.View {

    /* *************** */
    /* Layout elements */
    /* *************** */

    @BindView(R.id.checkout_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.checkout_user)
    TextView mUserData;

    @BindView(R.id.checkout_items_recyclerview)
    RecyclerView mRecyclerView;

    @BindView(R.id.checkout_address)
    TextView mAddress;

    @BindView(R.id.checkout_payment)
    TextView mPayment;

    @BindView(R.id.checkout_items_total)
    TextView mTotal;

    @BindView(R.id.checkout_next)
    Button mPayButton;

    @BindView(R.id.checkout_errormsg)
    TextView mMessage;

    @BindView(R.id.checkout_progress)
    ProgressBar mProgressBar;

    @BindView(R.id.checkout_scrollview)
    ScrollView mScrollView;

    /* ******************************* */
    /* Communication with the Activity */
    /* ******************************* */

    public interface OnInteractionListener {
        void checkoutSuccessful(TransactionData result);

        void checkoutNotSuccessful(TransactionData result);
    }

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private View mRootView;
    private CheckoutSummaryPresenter mPresenter;
    private OnInteractionListener mListener;
    private Checkout mCheckout;
    private CheckoutSummaryAdapter mAdapter;

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
                R.layout.fragment_checkout_summary, container, false);
        ButterKnife.bind(this, mRootView);

        // Create the presenter for this view.
        mPresenter = new CheckoutSummaryPresenter(getContext(), this);

        // Ask presenter to initialize all elements in this view.
        mPresenter.start(mCheckout);

        return mRootView;
    }

    /* ************** */
    /* Public methods */
    /* ************** */

    /**
     * Required empty public constructor.
     */
    public CheckoutSummaryFragment() {
    }

    /**
     * Factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param checkout is the {@link Checkout} object containing the shopping process data so far.
     * @return a new instance of fragment CheckoutSummaryFragment.
     */
    public static CheckoutSummaryFragment newInstance(Checkout checkout) {
        CheckoutSummaryFragment fragment = new CheckoutSummaryFragment();
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
    /* CheckoutSummaryContract.View implementation */
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
        // Tell Presenter that user has clicked on the "Pay" button.
        mPayButton.setOnClickListener(view -> mPresenter.checkoutClicked(mCheckout));
    }

    /**
     * Create adapter and set initial state for the RecyclerView..
     */
    @Override
    public void setupItemsList() {
        mAdapter = new CheckoutSummaryAdapter(getContext(), R.layout.item_checkout_shoppingcart);
        setupVerticalRecyclerView(mRecyclerView, mAdapter);
    }

    /**
     * Load a list of items into the adapter.
     *
     * @param shoppingCart is the list of {@link ShoppingCartItem} objects to be loaded.
     */
    @Override
    public void loadItems(ArrayList<ShoppingCartItem> shoppingCart) {
        mAdapter.replaceList(mCheckout.getShoppingCart());
    }

    /**
     * Show the ProgressBar.
     */
    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the ProgressBar.
     */
    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(GONE);
    }

    /**
     * Show a text in the user data TextView.
     *
     * @param text is the string to be set into the TextView.
     */
    @Override
    public void setUserText(Spanned text) {
        mUserData.setText(text);
    }

    /**
     * Show a text in the address TextView.
     *
     * @param text is the string to be set into the TextView.
     */
    @Override
    public void setAddressText(Spanned text) {
        mAddress.setText(text);
    }

    /**
     * Show a text in the payment method TextView.
     *
     * @param text is the string to be set into the TextView.
     */
    @Override
    public void setPaymentMethodText(String text) {
        mPayment.setText(text);
    }

    /**
     * Show a text in the "Total to pay" TextView.
     *
     * @param amount is the total amount to pay from the shopping cart.
     */
    @Override
    public void setTotal(Double amount) {
        mTotal.setText(getString(R.string.checkout_summary_total,
                String.format(Locale.getDefault(), "%.2f", amount)));
    }

    /**
     * "Next step" button is enabled and may receive click events from now on.
     */
    @Override
    public void enableNextButton() {
        enableNavigationButton(mPayButton);
    }

    /**
     * "Next step" button is disabled and may not receive click events from now on.
     */
    @Override
    public void disableNextButton() {
        disableNavigationButton(mPayButton);
    }

    /**
     * Payment has been ok. Inform listener about this.
     *
     * @param result is the {@link TransactionData} object containing details about the online
     *               payment transaction.
     */
    @Override
    public void checkoutOk(TransactionData result) {
        mListener.checkoutSuccessful(result);
    }

    /**
     * Some error has happened while executing the payment transaction.
     *
     * @param result is the {@link TransactionData} object containing details about the online
     *               payment transaction.
     */
    @Override
    public void checkoutError(TransactionData result) {
        // Show a custom dialog to notify user the request creation result.
        LayoutInflater factory = LayoutInflater.from(getContext());
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getContext());
        final View customView =
                factory.inflate(R.layout.layout_checkout_transaction_result, null);
        alertDialogBuilder.setView(customView);

        // Initialize dialog layout.
        TextView titleTextView = customView.findViewById(R.id.transaction_result);
        LinearLayout authorizationIdLayout =
                customView.findViewById(R.id.transaction_authorizationId_layout);
        TextView codeTextView = customView.findViewById(R.id.transaction_id);
        TextView dateTextView = customView.findViewById(R.id.transaction_date_time);
        TextView amountTextView = customView.findViewById(R.id.transaction_amount);
        TextView userTextView = customView.findViewById(R.id.transaction_user);
        TextView commentsTextView = customView.findViewById(R.id.transaction_comments);
        Button closeButton = customView.findViewById(R.id.transaction_close);

        titleTextView.setText(result.getResult().getDescription());
        titleTextView.setTextColor(getResources().getColor(R.color.colorPink));
        authorizationIdLayout.setVisibility(GONE);
        codeTextView.setText(result.getTransactionId());
        dateTextView.setText(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
                .format(result.getTransactionDate()));
        amountTextView.setText(getString(R.string.transaction_to_pay,
                String.format(Locale.getDefault(), "%.2f", result.getAmount())));
        commentsTextView.setText(getString(R.string.transaction_error));
        commentsTextView.setTextColor(getResources().getColor(R.color.colorPink));
        userTextView.setText(result.getUserData());

        // Do not cancel the Alert Dialog on clicking area outside dialog. We want that user cancels
        // action by explicitly clicking on "Close" button.
        alertDialogBuilder.setCancelable(false);

        // Configure dialog, create it and display it.
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();

        // Actions to be performed on user interactions.
        closeButton.setOnClickListener(null);
    }

    /**
     * There has been some error while connecting to the online payment platform. Raise an alert
     * showing the error message.
     *
     * @param message is the error message returned from the interactor.
     */
    @Override
    public void checkoutServerError(String message) {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.checkout_payment_server_error_title)
                .setMessage(getString(R.string.checkout_payment_server_error_details, message))
                .setPositiveButton(R.string.close, null)
                .show();
    }

    /**
     * Show the "Pay" button.
     */
    @Override
    public void showNextButton() {
        mPayButton.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the "Pay" button.
     */
    @Override
    public void hideNextButton() {
        mPayButton.setVisibility(View.GONE);
    }

    /**
     * Show message.
     *
     * @param text is the message to be shown.
     */
    @Override
    public void showMessage(String text) {
        mMessage.setText(text);
        mMessage.setVisibility(View.VISIBLE);
    }

    /**
     * Hide message.
     */
    @Override
    public void hideMessage() {
        mMessage.setText("");
        mMessage.setVisibility(GONE);

    }

    /**
     * Show the ScrollView containing the summary.
     */
    @Override
    public void showSummary() {
        mScrollView.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the ScrollView containing the summary.
     */
    @Override
    public void hideSummary() {
        mScrollView.setVisibility(GONE);
    }
}
