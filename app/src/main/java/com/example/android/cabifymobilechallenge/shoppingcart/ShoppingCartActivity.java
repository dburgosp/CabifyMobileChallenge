package com.example.android.cabifymobilechallenge.shoppingcart;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.view.BaseActivity;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.ShoppingCartItem;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.StoredShoppingCartItem;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.cabifymobilechallenge.shoppingcart.ShoppingCartContract.REQUEST_CODE_CHECKOUT;

public class ShoppingCartActivity extends BaseActivity implements ShoppingCartContract.View {

    public static final String PARAM_SHOPPING_CART = "PARAM_SHOPPING_CART";

    /* *************** */
    /* Layout elements */
    /* *************** */

    @BindView(R.id.cart_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.cart_progress)
    ProgressBar mProgressBar;

    @BindView(R.id.cart_error)
    TextView mErrorMsg;

    @BindView(R.id.cart_list)
    RecyclerView mRecyclerView;

    @BindView(R.id.buttons_layout)
    LinearLayout mButtonsLayout;

    @BindView(R.id.cart_delete)
    Button mDeleteButton;

    @BindView(R.id.cart_checkout)
    Button mCheckoutButton;

    @BindView(R.id.cart_save)
    TextView mTotalSaving;

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private ShoppingCartPresenter mPresenter;
    private ShoppingCartNavigator mNavigator;
    private ShoppingCartAdapter mAdapter;
    private ArrayList<StoredShoppingCartItem> mStoredShoppingCart = new ArrayList<>();
    private ArrayList<ShoppingCartItem> mShoppingCart = new ArrayList<>();

    /* ******************************* */
    /* BaseActivity overridden methods */
    /* ******************************* */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoppingcart);
        ButterKnife.bind(this);

        // Get parameters from calling Intent.
        if (getIntent() != null && getIntent().getExtras() != null) {
            mStoredShoppingCart = getIntent().getExtras().getParcelableArrayList(PARAM_SHOPPING_CART);
        }

        // Create the presenter and the navigator for this view.
        mPresenter = new ShoppingCartPresenter(this, this);
        mNavigator = new ShoppingCartNavigator(this, this);

        // Ask presenter to initialize all elements in this View.
        mPresenter.start(mStoredShoppingCart);
    }

    @Override
    public void onBackPressed() {
        mNavigator.startStoreActivity();
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CHECKOUT) {
            if (resultCode == Activity.RESULT_OK) {
                // We come from the Checkout Activity and result code is OK. Go back from this
                // activity to the Store Activity.
                onBackPressed();
            }
        }
    }

    /* **************************************** */
    /* ShoppingCartContract.View implementation */
    /* **************************************** */

    /**
     * Set toolbar title and enable navigation back.
     */
    @Override
    public void setupToolbar() {
        setupToolbar(mToolbar, getString(R.string.shopping_cart_title), true);
    }

    /**
     * Remove item from adapter.
     *
     * @param index is the list index of the item to be removed.
     */
    @Override
    public void removeShoppingCartItem(int index) {
        mAdapter.removeItem(index);
        if (mAdapter.getItemCount() == 0) {
            hideContent();
            showErrorMsg(getString(R.string.shopping_cart_is_emtpy));
        }
    }

    /**
     * Attach listeners to "Empty Cart" and "Checkout" buttons.
     */
    @Override
    public void setupListeners() {
        mDeleteButton.setOnClickListener(v -> {
            new AlertDialog.Builder(this)
                    .setMessage(R.string.confirm_shopping_cart_deletion)
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                        // Tell Presenter that user has confirmed the shopping cart deletion.
                        mPresenter.deleteButtonClicked();
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .show();
        });

        mCheckoutButton.setOnClickListener(v -> mPresenter.checkoutButtonClicked());
    }

    /**
     * Remove all entries from adapter.
     */
    @Override
    public void deleteShoppingCart() {
        mAdapter.clearList();
    }

    /**
     * Replaces an item into the adapter.
     *
     * @param index is the list index to be updated.
     * @param item  is the object to be updated.
     */
    @Override
    public void update(int index, ShoppingCartItem item) {
        mAdapter.replaceItem(index, item);
    }

    /**
     * Set total discount.
     *
     * @param totalDiscount is the amount that user saves in the current shopping cart.
     */
    @Override
    public void setTotalDiscount(double totalDiscount) {
        if (totalDiscount > 0.0) {
            mTotalSaving.setText(getString(R.string.total_saving,
                    String.format(Locale.getDefault(), "%.2f", totalDiscount)));
            mTotalSaving.setVisibility(View.VISIBLE);
        } else {
            mTotalSaving.setVisibility(View.GONE);
        }
    }

    /**
     * User is not logged. Load login View.
     */
    @Override
    public void notLoggedIn() {
        Toast.makeText(this, R.string.login_required_to_pay,
                Toast.LENGTH_SHORT).show();
        mNavigator.startLoginActivity();
    }

    /**
     * Load Checkout Activity.
     *
     * @param user         is the {@link User} object containing data about the logged user.
     * @param shoppingCart is the list of {@link ShoppingCartItem} objects in the shopping cart.
     */
    @Override
    public void gotoCheckout(User user, ArrayList<ShoppingCartItem> shoppingCart) {
        mNavigator.startCheckoutActivity(user, shoppingCart);
    }

    /**
     * Set title for the "Pay" button.
     *
     * @param amount is the amount to pay.
     */
    @Override
    public void setPayButton(Double amount) {
        mCheckoutButton.setText(getString(R.string.amount_to_pay,
                String.format(Locale.getDefault(), "%.2f", amount)));
    }

    /**
     * Initialize the list of shopping cart items.
     */
    @Override
    public void setupItemsList() {
        // Define listener for managing click events on every item into the list.
        ShoppingCartAdapter.InteractionListener listener =
                new ShoppingCartAdapter.InteractionListener() {
                    @Override
                    public void onRemoveShoppingCartItemClicked(ShoppingCartItem item, int index) {
                        // Tell Presenter that "Delete" button has been clicked on a shopping cart
                        // item.
                        mPresenter.removeShoppingCartItem(item.getCode(), index);
                    }

                    @Override
                    public void onAddToCartClicked(ShoppingCartItem item, int index) {
                        // Tell Presenter that "Add" button has been clicked on a shopping cart
                        // item.
                        mPresenter.addShoppingCartItem(item.getCode(), index);
                    }

                    @Override
                    public void onSubtractFromCartClicked(ShoppingCartItem item, int index) {
                        // Tell Presenter that "Subtract" button has been clicked on a shopping cart
                        // item.
                        mPresenter.subtractShoppingCartItem(item.getCode(), index);
                    }
                };

        // Create the adapter.
        mAdapter = new ShoppingCartAdapter(this, 0, listener,
                R.layout.item_shoppingcart);

        // Create the RecyclerView and set its initial state.
        setupVerticalRecyclerView(mRecyclerView, mAdapter);
    }

    /**
     * Load a new shopping cart into the adapter.
     *
     * @param shoppingCart is the list of {@link ShoppingCartItem} items to be loaded.
     */
    @Override
    public void loadShoppingCart(ArrayList<ShoppingCartItem> shoppingCart) {
        mShoppingCart = shoppingCart;
        mAdapter.replaceList(mShoppingCart);
    }

    /**
     * Show the ProgressBar.
     */
    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the ProgressBar.
     */
    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * Show the error messsage.
     *
     * @param msg is the text message to be written.
     */
    @Override
    public void showErrorMsg(String msg) {
        mErrorMsg.setText(msg);
        mErrorMsg.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the error messsage.
     */
    @Override
    public void hideErrorMsg() {
        mErrorMsg.setText("");
        mErrorMsg.setVisibility(View.GONE);
    }

    /**
     * Show shopping cart items list and buttons.
     */
    @Override
    public void showContent() {
        mRecyclerView.setVisibility(View.VISIBLE);
        mButtonsLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Hide shopping cart items list and buttons.
     */
    @Override
    public void hideContent() {
        mRecyclerView.setVisibility(View.GONE);
        mButtonsLayout.setVisibility(View.GONE);
    }
}
