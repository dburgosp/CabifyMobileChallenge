package com.example.android.cabifymobilechallenge.checkout.summary;

import android.text.Spanned;

import com.example.android.cabifymobilechallenge.data.pojo.payment.Checkout;
import com.example.android.cabifymobilechallenge.data.pojo.payment.TransactionData;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.ShoppingCartItem;

import java.util.ArrayList;

public interface CheckoutSummaryContract {
    interface View {
        void initToolbar(String title);

        void setupListeners();

        void setupItemsList();

        void loadItems(ArrayList<ShoppingCartItem> shoppingCart);

        void setAddressText(Spanned text);

        void setPaymentMethodText(String text);

        void setTotal(Double amountToPay);

        void setUserText(Spanned fromHtml);

        void showProgress();

        void hideProgress();

        void enableNextButton();

        void disableNextButton();

        void checkoutOk(TransactionData result);

        void checkoutError(TransactionData result);

        void checkoutServerError(String message);

        void showNextButton();

        void hideNextButton();

        void showMessage(String text);

        void hideMessage();

        void showSummary();

        void hideSummary();
    }

    interface Presenter {
        void start(Checkout checkout);

        void checkoutClicked(Checkout checkout);
    }
}
