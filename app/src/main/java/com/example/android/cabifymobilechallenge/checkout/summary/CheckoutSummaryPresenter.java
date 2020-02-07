package com.example.android.cabifymobilechallenge.checkout.summary;

import android.content.Context;
import android.text.Html;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.checkout.CheckoutInteractor;
import com.example.android.cabifymobilechallenge.common.presenter.BasePresenter;
import com.example.android.cabifymobilechallenge.common.presenter.Callback;
import com.example.android.cabifymobilechallenge.data.pojo.payment.Checkout;
import com.example.android.cabifymobilechallenge.data.pojo.payment.TransactionDataResult;
import com.example.android.cabifymobilechallenge.data.pojo.payment.TransactionData;
import com.example.android.cabifymobilechallenge.data.pojo.user.Address;

public class CheckoutSummaryPresenter extends BasePresenter<CheckoutSummaryContract.View>
        implements CheckoutSummaryContract.Presenter {

    private CheckoutInteractor mInteractor;

    /**
     * Public constructor.
     *
     * @param context is the {@link Context} of the view that instantiates this class.
     * @param view    is the ViewType view that instantiates this class.
     */
    public CheckoutSummaryPresenter(Context context, CheckoutSummaryContract.View view) {
        super(context, view);
        mInteractor = new CheckoutInteractor(context);
    }

    /* ************************************************ */
    /* CheckoutSummaryContract.Presenter implementation */
    /* ************************************************ */

    /**
     * Tell view what to do for displaying its initial state.
     *
     * @param checkout is the {@link Checkout} object containing the shopping process data so far.
     */
    @Override
    public void start(Checkout checkout) {
        mView.initToolbar(mContext.getString(R.string.checkout_summary_title));
        mView.setupListeners();
        mView.setupItemsList();
        mView.hideProgress();
        mView.hideMessage();

        // User data.
        String userData = "<strong>" + checkout.getUserName() + "</strong><br>" +
                checkout.getUserPhone();
        mView.setUserText(Html.fromHtml(userData));

        // Shopping cart content.
        mView.loadItems(checkout.getShoppingCart());
        mView.setTotal(mInteractor.getAmountToPay(checkout.getShoppingCart()));

        // Selected address.
        Address address = checkout.getAddress();
        String addressData = "<strong>" + address.getAlias() + "</strong><br>" + address.getStreet()
                + " " + address.getNumber();
        if (address.getStreetInfo() != null && !address.getStreetInfo().equals("")) {
            addressData += ", " + address.getStreetInfo();
        }
        addressData += "<br>" + address.getLocation() + " " + address.getPostalCode() + ", " +
                address.getRegion() + ", " + address.getCountry();
        mView.setAddressText(Html.fromHtml(addressData));

        // Selected payment method.
        mView.setPaymentMethodText(checkout.getPaymentMethod().getName());
    }

    /**
     * @param checkout is the {@link Checkout} object containing the final shopping process data.
     */
    @Override
    public void checkoutClicked(Checkout checkout) {
        mView.hideSummary();
        mView.hideNextButton();
        mView.showProgress();
        mView.showMessage(mContext.getString(R.string.checkout_connecting));

        // Ask Interactor for the payment methods list and wait for the result in a Callback object.
        mInteractor.processPayment(checkout, new Callback<TransactionData>() {
            @Override
            public void returnResult(TransactionData result) {
                if (result.getResult().getCode() == TransactionDataResult.CODE_1) {
                    // Payment has been successful. Delete shopping cart in local storage and tell
                    // View that everything has been ok.
                    mInteractor.clearShoppingCart(new Callback<Boolean>() {
                        @Override
                        public void returnResult(Boolean result) {
                            // Don't care about the result.
                        }

                        @Override
                        public void returnError(String message) {
                            // TODO: manage error.
                        }
                    });
                    mView.hideProgress();
                    mView.hideMessage();
                    mView.checkoutOk(result);
                } else {
                    // There has happened some error with the payment. Inform View about this
                    // situation.
                    mView.showSummary();
                    mView.hideProgress();
                    mView.hideMessage();
                    mView.showNextButton();
                    mView.checkoutError(result);
                }
            }

            @Override
            public void returnError(String message) {
                // There has been some error while trying to connect to the online payment platform.
                // Inform View about this situation.
                mView.showSummary();
                mView.hideProgress();
                mView.hideMessage();
                mView.showNextButton();
                mView.checkoutServerError(message);
            }
        });
    }
}
