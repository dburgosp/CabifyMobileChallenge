package com.example.android.cabifymobilechallenge.store.details;

import android.content.Context;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.presenter.BasePresenter;
import com.example.android.cabifymobilechallenge.data.pojo.products.ProductDetails;
import com.example.android.cabifymobilechallenge.store.StoreInteractor;

import java.util.Locale;

public class StoreDetailsPresenter
        extends BasePresenter<StoreDetailsContract.View>
        implements StoreDetailsContract.Presenter {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private StoreDetailsContract.View mView;
    private Context mContext;
    private StoreInteractor mInteractor;

    /* **************************** */
    /* BasePresenter implementation */
    /* **************************** */

    /**
     * Public constructor.
     *
     * @param context is the {@link Context} of the view that instantiates this class.
     * @param view    is the ViewType view that instantiates this class.
     */
    public StoreDetailsPresenter(Context context, StoreDetailsContract.View view) {
        super(context, view);

        mView = view;
        mContext = context;

        // Create interactor.
        mInteractor = new StoreInteractor(mContext);
    }

    /* ********************************************* */
    /* StoreDetailsContract.Presenter implementation */
    /* ********************************************* */

    @Override
    public void start(ProductDetails productDetails) {
        // Get discount from Interactor.
        String discount = mInteractor.getStringDiscount(productDetails.getDiscount());

        // Update View with the product details.
        mView.setupToolbar(productDetails.getName());
        mView.loadProductImage(mContext.getDrawable(productDetails.getImageResId()));
        mView.setProductCode(productDetails.getCode());
        mView.setProductPrice(mContext.getString(R.string.product_price,
                String.format(Locale.getDefault(), "%.2f", productDetails.getPrice())));
        mView.setProductDescription(productDetails.getDescription());
        if (discount != null && !discount.equals("")) {
            mView.showProductDiscount(discount);
        } else {
            mView.hideProductDiscount();
        }

        // Tell View to setup listeners for handling user interactions.
        mView.setupListeners();
    }
}
