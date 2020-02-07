package com.example.android.cabifymobilechallenge.checkout;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.example.android.cabifymobilechallenge.checkout.address.CheckoutAddressFragment;
import com.example.android.cabifymobilechallenge.checkout.payment.CheckoutPaymentFragment;
import com.example.android.cabifymobilechallenge.checkout.summary.CheckoutSummaryFragment;
import com.example.android.cabifymobilechallenge.common.presenter.BaseNavigator;
import com.example.android.cabifymobilechallenge.data.pojo.payment.Checkout;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.StoredShoppingCartItem;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;
import com.example.android.cabifymobilechallenge.shoppingcart.ShoppingCartActivity;
import com.example.android.cabifymobilechallenge.store.StoreActivity;

import java.util.ArrayList;

public class CheckoutNavigator
        extends BaseNavigator<CheckoutContract.View>
        implements CheckoutContract.Navigator {

    public CheckoutNavigator(Context context, CheckoutContract.View view) {
        super(context, view);
    }

    /* ***************************************** */
    /* CheckoutContract.Navigator implementation */
    /* ***************************************** */

    /**
     * Load a new {@link CheckoutAddressFragment}.
     *
     * @param fragmentManager is the {@link FragmentManager} to be used for loading fragments.
     * @param containerResId  is the layout resource identifier of the container that will hold the
     *                        new fragment.
     * @param user            is the {@link User} that has initiated the shopping process.
     * @param checkout        is the {@link Checkout} object containing the shopping process data so
     *                        far.
     */
    @Override
    public void loadCheckoutAddressFragment(FragmentManager fragmentManager, int containerResId,
                                            User user, Checkout checkout) {
        CheckoutAddressFragment fragment = CheckoutAddressFragment.newInstance(user, checkout);
        fragment.attachListener((CheckoutAddressFragment.OnInteractionListener) mView);
        loadTopFragment(fragmentManager, containerResId, fragment);
    }

    /**
     * Load a new {@link CheckoutPaymentFragment}.
     *
     * @param fragmentManager is the {@link FragmentManager} to be used for loading fragments.
     * @param containerResId  is the layout resource identifier of the container that will hold the
     *                        new fragment.
     * @param checkout        is the {@link Checkout} object containing the shopping process data so
     *                        far.
     */
    @Override
    public void loadCheckoutPaymentFragment(FragmentManager fragmentManager, int containerResId,
                                            Checkout checkout) {
        CheckoutPaymentFragment fragment = CheckoutPaymentFragment.newInstance(checkout);
        fragment.attachListener((CheckoutPaymentFragment.OnInteractionListener) mView);
        loadFragment(fragmentManager, containerResId, fragment);
    }

    /**
     * Load a new {@link CheckoutSummaryFragment}.
     *
     * @param fragmentManager is the {@link FragmentManager} to be used for loading fragments.
     * @param containerResId  is the layout resource identifier of the container that will hold the
     *                        new fragment.
     * @param checkout        is the {@link Checkout} object containing the shopping process data so
     *                        far.
     */
    @Override
    public void loadCheckoutSummaryFragment(FragmentManager fragmentManager, int containerResId,
                                            Checkout checkout) {
        CheckoutSummaryFragment fragment = CheckoutSummaryFragment.newInstance(checkout);
        fragment.attachListener((CheckoutSummaryFragment.OnInteractionListener) mView);
        loadFragment(fragmentManager, containerResId, fragment);
    }

    /**
     * Load a new {@link StoreActivity} and close current one.
     */
    @Override
    public void startStoreActivity() {
        Activity fromActivity = (Activity) mContext;
        Activity destinationActivity = new StoreActivity();
        loadActivity(fromActivity, destinationActivity);
        fromActivity.finish();
    }
}
