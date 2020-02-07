package com.example.android.cabifymobilechallenge.store.list;

public interface StoreListContract {
    String PARAM_PRODUCTS = "PARAM_PRODUCTS";

    interface View {
        void setupItemsList();

        void loadProducts();
    }

    interface Presenter {
        void start();
    }
}
