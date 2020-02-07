package com.example.android.cabifymobilechallenge.checkout;

import android.support.v4.app.FragmentManager;

import com.example.android.cabifymobilechallenge.common.presenter.Callback;
import com.example.android.cabifymobilechallenge.data.pojo.payment.Checkout;
import com.example.android.cabifymobilechallenge.data.pojo.payment.PaymentMethod;
import com.example.android.cabifymobilechallenge.data.pojo.payment.TransactionData;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.ShoppingCartItem;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;

import java.util.ArrayList;

public interface CheckoutContract {

    String PARAM_SHOPPING_CART = "PARAM_SHOPPING_CART";
    String PARAM_USER = "PARAM_USER";
    String PARAM_CHECKOUT = "PARAM_CHECKOUT";

    interface View {
        void gotoAddress();

        void initCheckout(Checkout checkout);
    }

    interface Presenter {
        void start(User user, ArrayList<ShoppingCartItem> shoppingCart);
    }

    interface Navigator {
        void loadCheckoutAddressFragment(FragmentManager fragmentManager, int containerResId,
                                         User user, Checkout checkout);

        void loadCheckoutPaymentFragment(FragmentManager fragmentManager, int containerResId,
                                         Checkout checkout);

        void loadCheckoutSummaryFragment(FragmentManager fragmentManager, int containerResId,
                                         Checkout checkout);

        void startStoreActivity();
    }

    interface Interactor {
        void getPaymentMethods(Callback<ArrayList<PaymentMethod>> callback);

        void processPayment(Checkout checkout, Callback<TransactionData> callback);
    }
}
