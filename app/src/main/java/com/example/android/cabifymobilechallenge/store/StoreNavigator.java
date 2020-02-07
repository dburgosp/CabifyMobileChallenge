package com.example.android.cabifymobilechallenge.store;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.example.android.cabifymobilechallenge.common.presenter.BaseNavigator;
import com.example.android.cabifymobilechallenge.data.pojo.products.Product;
import com.example.android.cabifymobilechallenge.data.pojo.products.ProductDetails;
import com.example.android.cabifymobilechallenge.data.pojo.products.Products;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.StoredShoppingCartItem;
import com.example.android.cabifymobilechallenge.store.details.StoreDetailsFragment;
import com.example.android.cabifymobilechallenge.store.list.StoreListFragment;
import com.example.android.cabifymobilechallenge.shoppingcart.ShoppingCartActivity;
import com.example.android.cabifymobilechallenge.user.UserActivity;
import com.example.android.cabifymobilechallenge.user.help.UserHelpActivity;

import java.util.ArrayList;

import static com.example.android.cabifymobilechallenge.store.StoreContract.REQUEST_CODE_LOGIN;

public class StoreNavigator
        extends BaseNavigator<StoreContract.View>
        implements StoreContract.Navigator {

    public StoreNavigator(Context context, StoreContract.View view) {
        super(context, view);
    }

    /* ************************************** */
    /* StoreContract.Navigator implementation */
    /* ************************************** */

    /**
     * Load a new {@link StoreListFragment} fragment passing a list of products as parameter.
     *
     * @param fragmentManager is the {@link FragmentManager} to be used for loading fragments.
     * @param containerResId  is the layout resource identifier of the container that will hold the
     *                        new fragment.
     * @param products        is the {@link Products} object containing the list of products to be
     *                        passed as parameter to the new fragment.
     */
    @Override
    public void loadProductsListFragment(FragmentManager fragmentManager, int containerResId,
                                         Products products) {
        StoreListFragment fragment =
                StoreListFragment.newInstance(new ArrayList<>(products.getProducts()));
        loadTopFragment(fragmentManager, containerResId, fragment);
        fragment.attachListener((StoreListFragment.OnInteractionListener) mView);
    }

    /**
     * Load a new {@link StoreDetailsFragment} fragment passing a product as parameter.
     *
     * @param fragmentManager is the {@link FragmentManager} to be used for loading fragments.
     * @param containerResId  is the layout resource identifier of the container that will hold the
     *                        new fragment.
     * @param productDetails  is the {@link Product} object to be passed as parameter to the new
     *                        fragment.
     */
    @Override
    public void loadProductsDetailsFragment(FragmentManager fragmentManager, int containerResId,
                                            ProductDetails productDetails) {
        StoreDetailsFragment fragment = StoreDetailsFragment.newInstance(productDetails);
        fragment.attachListener((StoreDetailsFragment.OnInteractionListener) mView);
        loadFragment(fragmentManager, containerResId, fragment);
    }

    /**
     * Load a new {@link ShoppingCartActivity} passing a shopping cart as parameter.
     *
     * @param shoppingCart is the list of {@link StoredShoppingCartItem} items to be passed as
     *                     parameter to the new activity.
     */
    @Override
    public void startShoppingCartActivity(ArrayList<StoredShoppingCartItem> shoppingCart) {
        Activity fromActivity = (Activity) mContext;
        Activity destinationActivity = new ShoppingCartActivity();
        Bundle params = new Bundle();
        params.putParcelableArrayList(ShoppingCartActivity.PARAM_SHOPPING_CART, shoppingCart);
        loadActivity(fromActivity, destinationActivity, params);
    }

    /**
     * Load a new {@link UserActivity}.
     */
    @Override
    public void startLoginActivity() {
        Activity fromActivity = (Activity) mContext;
        Activity destinationActivity = new UserActivity();
        loadActivityForResult(fromActivity, destinationActivity, REQUEST_CODE_LOGIN);
    }

    /**
     * Load a new {@link UserHelpActivity}.
     */
    @Override
    public void startHelpActivity() {
        Activity fromActivity = (Activity) mContext;
        Activity destinationActivity = new UserHelpActivity();
        loadActivity(fromActivity, destinationActivity);
    }
}
