package com.example.android.cabifymobilechallenge.checkout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.checkout.address.CheckoutAddressFragment;
import com.example.android.cabifymobilechallenge.checkout.payment.CheckoutPaymentFragment;
import com.example.android.cabifymobilechallenge.checkout.summary.CheckoutSummaryFragment;
import com.example.android.cabifymobilechallenge.common.view.BaseActivity;
import com.example.android.cabifymobilechallenge.data.pojo.payment.Checkout;
import com.example.android.cabifymobilechallenge.data.pojo.payment.TransactionData;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.ShoppingCartItem;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CheckoutActivity extends BaseActivity
        implements CheckoutContract.View, CheckoutAddressFragment.OnInteractionListener,
        CheckoutPaymentFragment.OnInteractionListener,
        CheckoutSummaryFragment.OnInteractionListener {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private CheckoutPresenter mPresenter;
    private CheckoutNavigator mNavigator;
    private ArrayList<ShoppingCartItem> mShoppingCart = new ArrayList<>();
    private User mUser;
    private Checkout mCheckout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        // Get parameters from calling Intent.
        if (getIntent() != null && getIntent().getExtras() != null) {
            mShoppingCart = getIntent().getExtras().getParcelableArrayList(
                    CheckoutContract.PARAM_SHOPPING_CART);
            mUser = getIntent().getExtras().getParcelable(CheckoutContract.PARAM_USER);

            // Create the presenter and the navigator for this view.
            mPresenter = new CheckoutPresenter(this, this);
            mNavigator = new CheckoutNavigator(this, this);

            // Ask presenter to initialize all elements in this Activity.
            mPresenter.start(mUser, mShoppingCart);
        } else {
            finish();
        }
    }

    /* ************************************ */
    /* CheckoutContract.View implementation */
    /* ************************************ */

    /**
     * Initialize the private member variable mCheckout.
     *
     * @param checkout is the {@link Checkout} object created from the Presenter.
     */
    @Override
    public void initCheckout(Checkout checkout) {
        mCheckout = checkout;
    }

    /**
     * Load {@link CheckoutAddressFragment}.
     */
    @Override
    public void gotoAddress() {
        mNavigator.loadCheckoutAddressFragment(
                getSupportFragmentManager(), R.id.checkout_container, mUser, mCheckout);
    }

    /* ************************************************************ */
    /* CheckoutAddressFragment.OnInteractionListener implementation */
    /* ************************************************************ */

    /**
     * Load {@link CheckoutPaymentFragment}.
     *
     * @param checkout is the {@link Checkout} object to passed as an argument to the new fragment.
     */
    @Override
    public void onGotoPaymentClicked(Checkout checkout) {
        mNavigator.loadCheckoutPaymentFragment(
                getSupportFragmentManager(), R.id.checkout_container, checkout);
    }

    /* ************************************************************ */
    /* CheckoutPaymentFragment.OnInteractionListener implementation */
    /* ************************************************************ */

    /**
     * Load {@link CheckoutPaymentFragment}.
     *
     * @param checkout is the {@link Checkout} object to passed as an argument to the new fragment.
     */
    @Override
    public void onGotoSummaryClicked(Checkout checkout) {
        mNavigator.loadCheckoutSummaryFragment(
                getSupportFragmentManager(), R.id.checkout_container, checkout);
    }

    /* ************************************************************ */
    /* CheckoutSummaryFragment.OnInteractionListener implementation */
    /* ************************************************************ */

    /**
     * Payment transaction has finished successfully. Show a dialog with transaction details and
     * close this activity.
     *
     * @param result is the {@link TransactionData} object with the payment transaction details.
     */
    @Override
    public void checkoutSuccessful(TransactionData result) {
        showTransactionResultDialog(result, v -> {
            // Tell calling activity that everything is ok before finishing.
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }, true);
    }

    /**
     * Payment transaction has finished unsuccessfully. Show a dialog with error details and stay in
     * this activity.
     *
     * @param result is the {@link TransactionData} object with the payment transaction details.
     */
    @Override
    public void checkoutNotSuccessful(TransactionData result) {
        showTransactionResultDialog(result, null, false);
    }

    /* ********************** */
    /* Private helper methods */
    /* ********************** */

    /**
     * Show a custom dialog to notify user the request creation result.
     *
     * @param result   is the {@link TransactionData} object with the payment transaction details.
     * @param listener is the {@link View.OnClickListener} to be attached to the dialog "Close"
     *                 button.
     * @param success  is true if the transaction result has been successful, false otherwise.
     */
    private void showTransactionResultDialog(TransactionData result, View.OnClickListener listener,
                                             boolean success) {
        // Use custom layout for dialog.
        LayoutInflater factory = LayoutInflater.from(this);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        final View customView =
                factory.inflate(R.layout.layout_checkout_transaction_result, null);
        alertDialogBuilder.setView(customView);

        // Initialize dialog layout.
        TextView titleTextView = customView.findViewById(R.id.transaction_result);
        TextView transactionIdTextView = customView.findViewById(R.id.transaction_id);
        TextView authorizationCodeTextView = customView.findViewById(R.id.transaction_code);
        TextView dateTextView = customView.findViewById(R.id.transaction_date_time);
        TextView amountTextView = customView.findViewById(R.id.transaction_amount);
        TextView userTextView = customView.findViewById(R.id.transaction_user);
        TextView commentsTextView = customView.findViewById(R.id.transaction_comments);
        Button closeButton = customView.findViewById(R.id.transaction_close);

        // Set dialog data depending on transaction result.
        titleTextView.setText(result.getResult().getDescription());
        transactionIdTextView.setText(result.getTransactionId());
        authorizationCodeTextView.setText(result.getAuthorizationCode());
        dateTextView.setText(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT)
                .format(result.getTransactionDate()));
        amountTextView.setText(getString(R.string.transaction_to_pay,
                String.format(Locale.getDefault(), "%.2f", result.getAmount())));
        userTextView.setText(result.getUserData());
        if (success) {
            // Transaction has been successful.
            titleTextView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
            commentsTextView.setText(getString(R.string.transaction_comments));
            commentsTextView.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        } else {
            // Transaction has been unsuccessful.
            titleTextView.setTextColor(getResources().getColor(R.color.colorPink));
            commentsTextView.setText(getString(R.string.transaction_error));
            commentsTextView.setTextColor(getResources().getColor(R.color.colorPink));
        }

        // Do not cancel the Alert Dialog on clicking area outside dialog. We want that user cancels
        // action by explicitly clicking on "Close" button.
        alertDialogBuilder.setCancelable(false);

        // Attach listener to "Close" button, defining actions to be executed when user clicks on it.
        closeButton.setOnClickListener(listener);

        // Create and display dialog.
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}