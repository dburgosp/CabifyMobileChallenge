package com.example.android.cabifymobilechallenge.store;

import android.support.v4.app.FragmentManager;

import com.example.android.cabifymobilechallenge.common.presenter.Callback;
import com.example.android.cabifymobilechallenge.data.pojo.discounts.Discount;
import com.example.android.cabifymobilechallenge.data.pojo.products.Product;
import com.example.android.cabifymobilechallenge.data.pojo.products.ProductDetails;
import com.example.android.cabifymobilechallenge.data.pojo.products.Products;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.ShoppingCartItem;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.StoredShoppingCartItem;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;

import java.util.ArrayList;

public interface StoreContract {
    int REQUEST_CODE_LOGIN = 3;

    interface View {
        void setProducts(Products products);

        void showProgressBar();

        void hideProgressBar();

        void hideContent();

        void showContent();

        void showFab();

        void hideFab();

        void showErrorMsg(String msg);

        void hideErrorMsg();

        void showProductDetails(ProductDetails item);

        void addShoppingCartItem(ArrayList<StoredShoppingCartItem> shoppingCart);

        void displayAddToCartAnimation();

        void setupToolbar();

        void setupNavigationDrawer(User user);

        void updateFab(int quantity);

        void showToolbar();

        void hideToolbar();

        void setShoppingCart(ArrayList<StoredShoppingCartItem> shoppingCart);

        void setupFab();

        void storeUserInfo(User user);
    }

    interface Presenter {
        void start();

        void productClicked(Product item);

        void addToCartClicked(ArrayList<StoredShoppingCartItem> shoppingCart, String productCode);

        void initShoppingCart();

        void loggedout();

        void loggedin();
    }

    interface Interactor {
        void isUserLogged(Callback<String> callback);

        void getProducts(Callback<Products> callback);

        void getProductDetails(String productCode, Callback<ProductDetails> callback);

        void getSavedShoppingCart(Callback<ArrayList<StoredShoppingCartItem>> callback);

        void getShoppingCartProductDetails(ArrayList<StoredShoppingCartItem> shoppingCart,
                                           Callback<ArrayList<ShoppingCartItem>> callback);

        void deleteShoppingCartItem(String code,
                                    Callback<ArrayList<StoredShoppingCartItem>> callback);

        void addShoppingCartItem(String productKey,
                                 Callback<ArrayList<StoredShoppingCartItem>> callback);

        void subtractShoppingCartItem(String code,
                                      Callback<ArrayList<StoredShoppingCartItem>> callback);

        int getShoppingCartItem(String productKey);

        String getStringDiscount(Discount discount);

        void getUser(String userId, Callback<User> userCallback);
    }

    interface Navigator {
        void loadProductsListFragment(FragmentManager fragmentManager, int containerResId,
                                      Products products);

        void loadProductsDetailsFragment(FragmentManager fragmentManager, int containerResId,
                                         ProductDetails productDetails);

        void startShoppingCartActivity(ArrayList<StoredShoppingCartItem> shoppingCart);

        void startLoginActivity();

        void startHelpActivity();
    }
}
