package com.example.android.cabifymobilechallenge.checkout.address;

import android.content.Context;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.presenter.BasePresenter;
import com.example.android.cabifymobilechallenge.data.pojo.payment.Checkout;
import com.example.android.cabifymobilechallenge.data.pojo.user.Address;

public class CheckoutAddressPresenter
        extends BasePresenter<CheckoutAddressContract.View>
        implements CheckoutAddressContract.Presenter {

    /* **************************** */
    /* BasePresenter implementation */
    /* **************************** */

    /**
     * Public constructor.
     *
     * @param context is the {@link Context} of the view that instantiates this class.
     * @param view    is the ViewType view that instantiates this class.
     */
    public CheckoutAddressPresenter(Context context, CheckoutAddressContract.View view) {
        super(context, view);
    }

    /* ************************************************ */
    /* CheckoutAddressContract.Presenter implementation */
    /* ************************************************ */

    /**
     * Tell view what to do for setting its initial state.
     */
    @Override
    public void start() {
        mView.initToolbar(mContext.getString(R.string.checkout_address_title));
        mView.disableNextButton();
        mView.hideProgress();
        mView.setupListeners();
        mView.setupAddressesList();
    }

    /**
     * User has selected an address. Update the checkout setting the selected address an pass it
     * back to the view.
     *
     * @param checkout is the {@link Checkout} object containing the shopping process data so far.
     * @param address  is the selected {@link Address} for delivery.
     */
    @Override
    public void addressSelected(Checkout checkout, Address address) {
        checkout.setAddress(address);
        mView.updateCheckOut(checkout);
        mView.enableNextButton();
    }

    /**
     * User has clicked on "Next step" button. Tell view to load the payment view.
     */
    @Override
    public void nextStepClicked() {
        mView.gotoPayment();
    }
}
