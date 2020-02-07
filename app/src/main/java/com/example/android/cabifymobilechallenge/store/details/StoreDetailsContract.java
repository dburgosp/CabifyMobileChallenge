package com.example.android.cabifymobilechallenge.store.details;

import android.graphics.drawable.Drawable;

import com.example.android.cabifymobilechallenge.data.pojo.products.ProductDetails;

public interface StoreDetailsContract {
    String PARAM_PRODUCT_DETAILS = "PARAM_PRODUCT_DETAILS";

    interface View {
        void setupToolbar(String title);

        void loadProductImage(Drawable drawable);

        void setProductPrice(String price);

        void setProductCode(String code);

        void setProductDescription(String description);

        void showProductDiscount(String discount);

        void hideProductDiscount();

        void setupListeners();
    }

    interface Presenter {
        void start(ProductDetails productDetails);
    }
}
