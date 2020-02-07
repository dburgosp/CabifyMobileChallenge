package com.example.android.cabifymobilechallenge.shoppingcart;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.example.android.cabifymobilechallenge.checkout.CheckoutActivity;
import com.example.android.cabifymobilechallenge.checkout.CheckoutContract;
import com.example.android.cabifymobilechallenge.common.presenter.BaseNavigator;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.ShoppingCartItem;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.StoredShoppingCartItem;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;
import com.example.android.cabifymobilechallenge.store.StoreActivity;
import com.example.android.cabifymobilechallenge.user.UserActivity;

import java.util.ArrayList;

import static com.example.android.cabifymobilechallenge.shoppingcart.ShoppingCartContract.REQUEST_CODE_CHECKOUT;

public class ShoppingCartNavigator
        extends BaseNavigator<ShoppingCartContract.View>
        implements ShoppingCartContract.Navigator {

    public ShoppingCartNavigator(Context context, ShoppingCartContract.View view) {
        super(context, view);
    }

    /* ********************************************* */
    /* ShoppingCartContract.Navigator implementation */
    /* ********************************************* */

    /**
     * Load a new {@link UserActivity}.
     */
    @Override
    public void startLoginActivity() {
        Activity fromActivity = (Activity) mContext;
        Activity destinationActivity = new UserActivity();
        loadActivity(fromActivity, destinationActivity);
    }

    /**
     * Load a new {@link CheckoutActivity}.
     *
     * @param user         is the {@link User} object containing data about the logged user.
     * @param shoppingCart is the list of {@link ShoppingCartItem} objects in the shopping cart.
     */
    @Override
    public void startCheckoutActivity(User user, ArrayList<ShoppingCartItem> shoppingCart) {
        Activity fromActivity = (Activity) mContext;
        Activity destinationActivity = new CheckoutActivity();
        Bundle params = new Bundle();
        params.putParcelableArrayList(CheckoutContract.PARAM_SHOPPING_CART, shoppingCart);
        params.putParcelable(CheckoutContract.PARAM_USER, user);
        loadActivityForResult(fromActivity, destinationActivity, params, REQUEST_CODE_CHECKOUT);
    }

    /**
     * Load a new {@link StoreActivity}.
     */
    @Override
    public void startStoreActivity() {
        Activity fromActivity = (Activity) mContext;
        Activity destinationActivity = new StoreActivity();
        loadActivity(fromActivity, destinationActivity);
    }
}
