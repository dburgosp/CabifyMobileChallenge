package com.example.android.cabifymobilechallenge.store.details;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.view.BaseFragment;
import com.example.android.cabifymobilechallenge.data.pojo.products.ProductDetails;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.cabifymobilechallenge.store.details.StoreDetailsContract.PARAM_PRODUCT_DETAILS;

public class StoreDetailsFragment
        extends BaseFragment
        implements StoreDetailsContract.View {

    /* *************** */
    /* Layout elements */
    /* *************** */

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;

    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;

    @BindView(R.id.image)
    ImageView mProductImage;

    @BindView(R.id.product_code)
    TextView mProductCode;

    @BindView(R.id.product_price)
    TextView mProductPrice;

    @BindView(R.id.product_discount)
    TextView mProductDiscount;

    @BindView(R.id.product_description)
    TextView mProductDescription;

    @BindView(R.id.product_add)
    Button mProductAdd;

    /* ******************************* */
    /* Communication with the Activity */
    /* ******************************* */

    public interface OnInteractionListener {
        void onAddToCartClicked(String productCode);
    }

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private StoreDetailsPresenter mPresenter;
    private ProductDetails mProductDetails;
    private View mRootView;
    private OnInteractionListener mListener;

    /* ************************************* */
    /* BaseFragment class overridden methods */
    /* ************************************* */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get arguments previously added in the newInstance method.
        if (getArguments() != null) {
            mProductDetails = getArguments().getParcelable(PARAM_PRODUCT_DETAILS);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        mRootView = inflater.inflate(
                R.layout.fragment_product_details, container, false);
        ButterKnife.bind(this, mRootView);

        // Create the presenter and the navigator for this view.
        mPresenter = new StoreDetailsPresenter(getContext(), this);

        // Ask presenter to initialize all elements in this Fragment.
        mPresenter.start(mProductDetails);

        return mRootView;
    }

    /* ************** */
    /* Public methods */
    /* ************** */

    /**
     * Required empty public constructor.
     */
    public StoreDetailsFragment() {
    }

    /**
     * Factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param productDetails is the {@link ProductDetails} object passed as a parameter to this
     *                       method.
     * @return a new instance of fragment StoreDetailsFragment.
     */
    public static StoreDetailsFragment newInstance(ProductDetails productDetails) {
        StoreDetailsFragment fragment = new StoreDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(PARAM_PRODUCT_DETAILS, productDetails);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * @param listener is the Activity that is listening for user interactions on this fragment.
     */
    public void attachListener(OnInteractionListener listener) {
        mListener = listener;
    }

    /* **************************************** */
    /* StoreDetailsContract.View implementation */
    /* **************************************** */

    /**
     * Set the custom toolbar and show the back button.
     */
    @Override
    public void setupToolbar(String title) {
        setupCollapsingToolbar(mToolbar, mCollapsingToolbarLayout, title);
    }

    /**
     * Load a {@link Drawable} into the product image.
     *
     * @param drawable is the image to be loaded.
     */
    @Override
    public void loadProductImage(Drawable drawable) {
        mProductImage.setImageDrawable(drawable);
    }

    /**
     * Displays the product code.
     *
     * @param code is the text code to be written.
     */
    @Override
    public void setProductCode(String code) {
        mProductCode.setText(code);
    }

    /**
     * Displays the product description.
     *
     * @param price is the text price to be written.
     */
    @Override
    public void setProductPrice(String price) {
        mProductPrice.setText(price);
    }

    /**
     * Displays the product description.
     *
     * @param description is the text description to be written.
     */
    @Override
    public void setProductDescription(String description) {
        mProductDescription.setText(description);
    }

    /**
     * Displays the product discount.
     *
     * @param discount is the text description of the product discount.
     */
    @Override
    public void showProductDiscount(String discount) {
        mProductDiscount.setText(discount);
        mProductDiscount.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the product discount.
     */
    @Override
    public void hideProductDiscount() {
        mProductDiscount.setText("");
        mProductDiscount.setVisibility(View.GONE);
    }

    /**
     * Add OnClickListener to "Add to cart" button.
     */
    @Override
    public void setupListeners() {
        mProductAdd.setOnClickListener(v -> {
            // Tell listener that "Add to cart" button has been clicked.
            mListener.onAddToCartClicked(mProductDetails.getCode());
        });
    }
}
