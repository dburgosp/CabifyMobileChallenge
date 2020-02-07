package com.example.android.cabifymobilechallenge.checkout.payment;

import com.example.android.cabifymobilechallenge.data.pojo.payment.Checkout;
import com.example.android.cabifymobilechallenge.data.pojo.payment.PaymentMethod;

import java.util.ArrayList;

public interface CheckoutPaymentContract {
    interface View {
        void initToolbar(String title);

        void setupListeners();

        void enableNextButton();

        void disableNextButton();

        void showProgress();

        void hideProgress();

        void showNextButton();

        void hideNextButton();

        void setupPaymentMethodsList(ArrayList<PaymentMethod> paymentMethods);

        void gotoSummary();

        void updateCheckout(Checkout checkout);
    }

    interface Presenter {
        void start();

        void nextStepClicked();

        void paymentMethodSelected(Checkout checkout, int i);
    }
}
