package com.example.android.cabifymobilechallenge.checkout.address;

import com.example.android.cabifymobilechallenge.data.pojo.payment.Checkout;
import com.example.android.cabifymobilechallenge.data.pojo.user.Address;

public interface CheckoutAddressContract {
    interface View {
        void initToolbar(String title);

        void setupListeners();

        void enableNextButton();

        void disableNextButton();

        void showProgress();

        void hideProgress();

        void showNextButton();

        void hideNextButton();

        void setupAddressesList();

        void updateCheckOut(Checkout checkout);

        void gotoPayment();
    }

    interface Presenter {
        void start();

        void addressSelected(Checkout checkout, Address address);

        void nextStepClicked();
    }
}
