package com.example.android.cabifymobilechallenge.checkout;

import android.content.Context;

import com.example.android.cabifymobilechallenge.common.presenter.BasePresenter;
import com.example.android.cabifymobilechallenge.data.pojo.payment.Checkout;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.ShoppingCartItem;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;

import java.util.ArrayList;

public class CheckoutPresenter
        extends BasePresenter<CheckoutContract.View>
        implements CheckoutContract.Presenter {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private CheckoutInteractor mInteractor;

    /* **************************** */
    /* BasePresenter implementation */
    /* **************************** */

    /**
     * Public constructor.
     *
     * @param context is the {@link Context} of the view that instantiates this class.
     * @param view    is the ViewType view that instantiates this class.
     */
    public CheckoutPresenter(Context context, CheckoutContract.View view) {
        super(context, view);

        // Create interactor.
        mInteractor = new CheckoutInteractor(mContext);
    }

    /* ***************************************** */
    /* CheckoutContract.Presenter implementation */
    /* ***************************************** */

    /**
     * Tell view what to do for displaying its initial state.
     *
     * @param user         is the {@link User} that has initiated the shopping process.
     * @param shoppingCart is the list of {@link ShoppingCartItem} objects.
     */
    @Override
    public void start(User user, ArrayList<ShoppingCartItem> shoppingCart) {
        Checkout checkout = new Checkout(user.getId(),
                user.getName() + " " + user.getSurname(), user.getPhone(),
                shoppingCart, null, null);
        mView.initCheckout(checkout);
        mView.gotoAddress();
    }
}
