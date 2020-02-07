package com.example.android.cabifymobilechallenge.store.list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.view.adapter.BaseAdapter;
import com.example.android.cabifymobilechallenge.common.view.adapter.BaseViewHolder;
import com.example.android.cabifymobilechallenge.data.pojo.products.Product;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoreListAdapter extends BaseAdapter<Product> {

    /* *************** */
    /* Layout elements */
    /* *************** */

    @BindView(R.id.item_image)
    ImageView mProductImage;

    @BindView(R.id.item_discount)
    ImageView mProductDiscountIcon;

    @BindView(R.id.item_name)
    TextView mProductName;

    @BindView(R.id.item_code)
    TextView mProductCode;

    @BindView(R.id.item_price)
    TextView mProductPrice;

    @BindView(R.id.item_add)
    Button mAddToCart;

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private View mView;

    /* **************************************************** */
    /* OnInteractionListener for click events on ViewHolder */
    /* **************************************************** */

    public interface ProductInteractionListener {
        void onProductClicked(ImageView imageView, Product product);

        void onAddToCartClicked(Product Product);
    }

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private ProductInteractionListener mListener;

    /**
     * Public constructor.
     *
     * @param context     is the Context of the calling activity or fragment.
     * @param numItems    is the maximum numbers of elements that mItems will hold by default.
     * @param listener    is the {@link ProductInteractionListener} to be added to every item in
     *                    the adapter.
     * @param layoutResId is the resource identifier of the layout for the {@link BaseViewHolder}
     *                    items.
     */
    public StoreListAdapter(Context context, int numItems, ProductInteractionListener listener,
                            int layoutResId) {
        super(context, numItems, null, layoutResId);
        mListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<Product> viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);
        mView = viewHolder.getView();
        ButterKnife.bind(this, mView);

        // Initialize all elements in this ViewHolder.
        startHolder(getItem(position), position);
    }

    /* ********************** */
    /* Private helper methods */
    /* ********************** */

    /**
     * Set data and listeners to the current item view elements depending on current layout resource
     * identifier.
     *
     * @param product is the {@link Product} object extracted from the current item.
     * @param i       is the index of the current item into the adapter.
     */
    private void startHolder(Product product, int i) {
        // Draw product small image.
        mProductImage.setImageDrawable(getContext().getDrawable(product.getImageResId()));

        // Show or hide discount indicator.
        if (product.isDiscount()) {
            mProductDiscountIcon.setVisibility(View.VISIBLE);
        } else {
            mProductDiscountIcon.setVisibility(View.GONE);
        }

        // Set product name, code and price in euros.
        mProductName.setText(product.getName());
        mProductCode.setText(product.getCode());
        mProductPrice.setText(getContext().getString(R.string.product_price,
                String.format(Locale.getDefault(), "%.2f", product.getPrice())));

        // Set listener on the whole item.
        mView.setOnClickListener(view -> mListener.onProductClicked(mProductImage, product));

        // Set listener on "Add to cart" button.
        mAddToCart.setOnClickListener(view -> mListener.onAddToCartClicked(product));
    }
}