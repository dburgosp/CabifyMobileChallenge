package com.example.android.cabifymobilechallenge.shoppingcart;

import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.ShoppingCartItem;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.StoredShoppingCartItem;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;

import java.util.ArrayList;

public interface ShoppingCartContract {
    int REQUEST_CODE_CHECKOUT = 1;

    interface View {
        void setupItemsList();

        void loadShoppingCart(ArrayList<ShoppingCartItem> shoppingCart);

        void showProgress();

        void hideProgress();

        void showErrorMsg(String msg);

        void hideErrorMsg();

        void showContent();

        void hideContent();

        void setupToolbar();

        void removeShoppingCartItem(int index);

        void setupListeners();

        void deleteShoppingCart();

        void setPayButton(Double amount);

        void update(int index, ShoppingCartItem item);

        void setTotalDiscount(double totalDiscount);

        void notLoggedIn();

        void gotoCheckout(User user, ArrayList<ShoppingCartItem> shoppingCart);
    }

    interface Presenter {
        void start(ArrayList<StoredShoppingCartItem> storedShoppingCart);

        void removeShoppingCartItem(String code, int index);

        void deleteButtonClicked();

        void checkoutButtonClicked();

        void addShoppingCartItem(String code, int index);

        void subtractShoppingCartItem(String code, int index);
    }

    interface Navigator {
        void startLoginActivity();

        void startStoreActivity();

        void startCheckoutActivity(User user, ArrayList<ShoppingCartItem> shoppingCart);
    }
}
