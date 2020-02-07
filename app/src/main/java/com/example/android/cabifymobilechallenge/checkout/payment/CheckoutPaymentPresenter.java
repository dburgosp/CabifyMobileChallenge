package com.example.android.cabifymobilechallenge.checkout.payment;

import android.content.Context;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.checkout.CheckoutInteractor;
import com.example.android.cabifymobilechallenge.common.presenter.BasePresenter;
import com.example.android.cabifymobilechallenge.common.presenter.Callback;
import com.example.android.cabifymobilechallenge.data.pojo.payment.Checkout;
import com.example.android.cabifymobilechallenge.data.pojo.payment.PaymentMethod;

import java.util.ArrayList;

public class CheckoutPaymentPresenter extends BasePresenter<CheckoutPaymentContract.View>
        implements CheckoutPaymentContract.Presenter {

    private CheckoutInteractor mInteractor;
    private ArrayList<PaymentMethod> mPaymentMethods;

    /**
     * Public constructor.
     *
     * @param context is the {@link Context} of the view that instantiates this class.
     * @param view    is the ViewType view that instantiates this class.
     */
    public CheckoutPaymentPresenter(Context context, CheckoutPaymentContract.View view) {
        super(context, view);
        mInteractor = new CheckoutInteractor(context);
    }

    /* ************************************************ */
    /* CheckoutPaymentContract.Presenter implementation */
    /* ************************************************ */

    /**
     * Tell view what to do for displaying its initial state.
     */
    @Override
    public void start() {
        mView.initToolbar(mContext.getString(R.string.checkout_payment_title));
        mView.disableNextButton();
        mView.hideProgress();
        mView.setupListeners();

        // Ask Interactor for the payment methods list and wait for the result in a Callback object.
        mInteractor.getPaymentMethods(new Callback<ArrayList<PaymentMethod>>() {
            @Override
            public void returnResult(ArrayList<PaymentMethod> paymentMethods) {
                if (paymentMethods != null && paymentMethods.size() > 0) {
                    // Interactor has returned a non-empty payment methods list.
                    mPaymentMethods = paymentMethods;
                    mView.setupPaymentMethodsList(paymentMethods);
                } else {
                    // TODO: Interactor has returned an empty payment methods list.
                }
            }

            @Override
            public void returnError(String message) {
                // TODO: Manage error.
            }
        });
    }

    /**
     * User has clicked on "Next step" button. Tell view to load the summary view.
     */
    @Override
    public void nextStepClicked() {
        mView.gotoSummary();
    }

    /**
     * User has selected a payument method. Add it to the {@link Checkout} object and pass it back
     * to the View.
     *
     * @param checkout is the {@link Checkout} object containing the shopping process so far.
     * @param i        is the index of the selected payment method.
     */
    @Override
    public void paymentMethodSelected(Checkout checkout, int i) {
        if (mPaymentMethods != null && mPaymentMethods.size() > 0) {
            checkout.setPaymentMethod(mPaymentMethods.get(i));
            mView.updateCheckout(checkout);
            mView.enableNextButton();
        }
    }
}
