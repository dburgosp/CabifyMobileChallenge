package com.example.android.cabifymobilechallenge.shoppingcart;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.view.adapter.BaseAdapter;
import com.example.android.cabifymobilechallenge.common.view.adapter.BaseViewHolder;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.ShoppingCartItem;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoppingCartAdapter extends BaseAdapter<ShoppingCartItem> {

    /* *************** */
    /* Layout elements */
    /* *************** */

    @BindView(R.id.cart_item_name)
    TextView mItemName;

    @BindView(R.id.cart_item_delete)
    ImageView mItemDelete;

    @BindView(R.id.cart_item_subtract)
    Button mItemSubtract;

    @BindView(R.id.cart_item_units)
    TextView mItemQuantity;

    @BindView(R.id.cart_item_add)
    Button mItemAdd;

    @BindView(R.id.cart_item_price)
    TextView mItemPrice;

    @BindView(R.id.cart_item_discount)
    LinearLayout mDiscountLayout;

    @BindView(R.id.cart_item_discount_text)
    TextView mItemDiscountText;

    @BindView(R.id.cart_item_discount_price)
    TextView mItemDiscountPrice;

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private View mView;

    /* **************************************************** */
    /* OnInteractionListener for click events on ViewHolder */
    /* **************************************************** */

    public interface InteractionListener {
        void onRemoveShoppingCartItemClicked(ShoppingCartItem shoppingCartItem, int i);

        void onAddToCartClicked(ShoppingCartItem shoppingCartItem, int i);

        void onSubtractFromCartClicked(ShoppingCartItem shoppingCartItem, int i);
    }

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private InteractionListener mListener;

    /**
     * Public constructor.
     *
     * @param context     is the Context of the calling activity or fragment.
     * @param numItems    is the maximum numbers of elements that mItems will hold by default.
     * @param listener    is the {@link InteractionListener} to be added to every item in
     *                    the adapter.
     * @param layoutResId is the resource identifier of the layout for the {@link BaseViewHolder}
     *                    items.
     */
    public ShoppingCartAdapter(Context context, int numItems, InteractionListener listener,
                               int layoutResId) {
        super(context, numItems, null, layoutResId);
        mListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ShoppingCartItem> viewHolder, int position) {
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
     * @param item is the {@link ShoppingCartItem} object extracted from the current item.
     * @param i    is the index of the current item into the adapter.
     */
    private void startHolder(ShoppingCartItem item, int i) {
        // Product details.
        mItemName.setText(item.getName());
        mItemQuantity.setText(String.valueOf(item.getQuantity()));
        mItemPrice.setText(getContext().getString(R.string.product_price,
                String.format(Locale.getDefault(), "%.2f", item.getTotalPrice())));

        // Discount details.
        if (item.getDiscountPrice() != 0.0) {
            mItemDiscountPrice.setText(getContext().getString(R.string.product_price,
                    String.format(Locale.getDefault(), "%.2f", item.getDiscountPrice())));
            mItemDiscountText.setText(item.getDiscountText());
            mDiscountLayout.setVisibility(View.VISIBLE);
        } else {
            mItemDiscountPrice.setText("");
            mItemDiscountText.setText("");
            mDiscountLayout.setVisibility(View.GONE);
        }

        // Set listeners.
        mItemDelete.setOnClickListener(view -> mListener.onRemoveShoppingCartItemClicked(item, i));
        mItemAdd.setOnClickListener(view -> mListener.onAddToCartClicked(item, i));
        mItemSubtract.setOnClickListener(view -> mListener.onSubtractFromCartClicked(item, i));
    }
}