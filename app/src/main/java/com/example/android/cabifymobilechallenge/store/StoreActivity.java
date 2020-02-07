package com.example.android.cabifymobilechallenge.store;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.view.BaseActivity;
import com.example.android.cabifymobilechallenge.data.pojo.products.Product;
import com.example.android.cabifymobilechallenge.data.pojo.products.ProductDetails;
import com.example.android.cabifymobilechallenge.data.pojo.products.Products;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.StoredShoppingCartItem;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;
import com.example.android.cabifymobilechallenge.store.details.StoreDetailsFragment;
import com.example.android.cabifymobilechallenge.store.list.StoreListFragment;
import com.example.android.cabifymobilechallenge.utils.MyAnimationUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.cabifymobilechallenge.store.StoreContract.REQUEST_CODE_LOGIN;

public class StoreActivity
        extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, StoreContract.View,
        StoreListFragment.OnInteractionListener, StoreDetailsFragment.OnInteractionListener {

    /* *************** */
    /* Layout elements */
    /* *************** */

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.fab)
    FloatingActionButton mFab;

    @BindView(R.id.text_count)
    TextView mNumCartItemsTextView;

    @BindView(R.id.store_error)
    TextView mErrorMsg;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawer;

    @BindView(R.id.store_progressbar)
    ProgressBar mProgressBar;

    @BindView(R.id.fab_layout)
    RelativeLayout mFabLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.store_container)
    FrameLayout mContainer;

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private StorePresenter mPresenter;
    private StoreNavigator mNavigator;
    private ArrayList<StoredShoppingCartItem> mShoppingCart = new ArrayList<>();
    private MyAnimationUtils mAnimationUtils;
    private int mQuantity = 0;
    private boolean mExit = false;
    private User mUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        ButterKnife.bind(this);

        mAnimationUtils = new MyAnimationUtils(this);

        // Create the presenter and the navigator for this view.
        mPresenter = new StorePresenter(this, this);
        mNavigator = new StoreNavigator(this, this);

        // Ask presenter to initialize all elements in this Activity.
        mPresenter.start();
    }

    @Override
    public void onBackPressed() {
        // Try to show toolbar again, just in case we come from the Product Details Fragment.
        showToolbar();

        // Navigation back should close the Navigation Drawer if it was open.
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else {
            if (getSupportFragmentManager().getBackStackEntryCount() > 0 || mExit) {
                // Allow going back.
                super.onBackPressed();
            } else {
                // Prompt a warning message before finishing app.
                Snackbar snackbar = Snackbar.make(mContainer, getString(R.string.store_confirm_exit),
                        Snackbar.LENGTH_LONG);
                snackbar.addCallback(new Snackbar.Callback() {
                    @Override
                    public void onShown(Snackbar snackbar) {
                        // If back is pressed again, exit app.
                        mExit = true;
                    }

                    @Override
                    public void onDismissed(Snackbar snackbar, int event) {
                        // After Snackbar is dismissed, user will be prompted for exit confirmation
                        // again when back is pressed.
                        mExit = false;
                    }
                });
                snackbar.show();
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_login:
                mNavigator.startLoginActivity();
                break;

            case R.id.nav_profile:
                // TODO: pendiente.
                Toast.makeText(this, "Perfil de usuario pendiente de implementación",
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_addresses:
                // TODO: pendiente.
                Toast.makeText(this, "Gestión de direcciones pendiente de implementación",
                        Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_logout:
                new AlertDialog.Builder(this)
                        .setMessage(R.string.confirm_logout)
                        .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                            // Tell Presenter that user has confirmed to log out.
                            mPresenter.loggedout();
                            Toast.makeText(this, getString(R.string.logged_out),
                                    Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton(android.R.string.no, null)
                        .show();
                break;

            case R.id.nav_help:
                mNavigator.startHelpActivity();
                break;
        }

        mDrawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_LOGIN) {
            if (resultCode == Activity.RESULT_OK) {
                // We come from the Login Activity and the result code is OK. Ask Presenter to
                // refresh View login state.
                mPresenter.loggedin();
            }
        }
    }

    /* ********************************* */
    /* StoreContract.View implementation */
    /* ********************************* */

    /**
     * Called by the Presenter when it has retrieved a non empty list of products.
     *
     * @param products is the list of {@link Product} items to display.
     */
    @Override
    public void setProducts(Products products) {
        // Ask presenter to initialize the shopping cart.
        mPresenter.initShoppingCart();

        // Load a new fragment for displaying the list of products.
        mNavigator.loadProductsListFragment(getSupportFragmentManager(),
                R.id.store_container, products);
    }

    /**
     * Show the progress bar.
     */
    @Override
    public void showProgressBar() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the progress bar.
     */
    @Override
    public void hideProgressBar() {
        mProgressBar.setVisibility(View.GONE);
    }

    /**
     * Show the fragments container of this Activity.
     */
    @Override
    public void showContent() {
        mContainer.setVisibility(View.VISIBLE);
    }

    /**
     * Hide the fragments container of this Activity.
     */
    @Override
    public void hideContent() {
        mContainer.setVisibility(View.GONE);
    }

    /**
     * Show the shopping cart FAB layout.
     */
    @Override
    public void showFab() {
        if (mQuantity > 0) {
            mFabLayout.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Hide the shopping cart FAB layout.
     */
    @Override
    public void hideFab() {
        mFabLayout.setVisibility(View.GONE);
    }

    /**
     * Show an error message.
     *
     * @param msg is the error message to be displayed.
     */
    @Override
    public void showErrorMsg(String msg) {
        mErrorMsg.setVisibility(View.VISIBLE);
        mErrorMsg.setText(msg);
    }

    /**
     * Hide the error message.
     */
    @Override
    public void hideErrorMsg() {
        mErrorMsg.setVisibility(View.GONE);
        mErrorMsg.setText("");
    }

    /**
     * Use Navigator for loading the Product Details Fragment.
     *
     * @param productDetails is the {@link ProductDetails} object with the product information to be
     *                       displayed.
     */
    @Override
    public void showProductDetails(ProductDetails productDetails) {
        mNavigator.loadProductsDetailsFragment(getSupportFragmentManager(),
                R.id.store_container, productDetails);
    }

    /**
     * Refresh shopping cart by adding one item.
     *
     * @param shoppingCart is the list of {@link StoredShoppingCartItem} items containing the
     *                     current shopping cart.
     */
    @Override
    public void addShoppingCartItem(ArrayList<StoredShoppingCartItem> shoppingCart) {
        // Update local shopping cart.
        mShoppingCart = shoppingCart;

        // Increase local items counter.
        mQuantity++;
        String quantity = Integer.toString(mQuantity);
        mNumCartItemsTextView.setText(quantity);
    }

    /**
     * Animate the badge over the shopping cart FAB.
     */
    @Override
    public void displayAddToCartAnimation() {
        mAnimationUtils.bounce(mNumCartItemsTextView);
    }

    /**
     * Initialize the tool bar.
     */
    @Override
    public void setupToolbar() {
        setSupportActionBar(mToolbar);
    }

    /**
     * Initialize the NavigationDrawer.
     *
     * @param user is a valid {@link User} object if there is a current valid login session, null
     *             otherwise.
     */
    @Override
    public void setupNavigationDrawer(User user) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();
        mNavigationView.setNavigationItemSelectedListener(this);
        toggle.setDrawerIndicatorEnabled(false);

        // Customize menu items depending on login state.
        View view = mNavigationView.getHeaderView(0);
        ImageView imageView = view.findViewById(R.id.nav_header_icon);
        TextView textView = view.findViewById(R.id.nav_header_text);
        if (user != null) {
            mNavigationView.getMenu().getItem(0).setVisible(false);
            mNavigationView.getMenu().getItem(1).setVisible(true);
            mNavigationView.getMenu().getItem(2).setVisible(true);
            mNavigationView.getMenu().getItem(3).setVisible(true);
            mNavigationView.getMenu().getItem(4).setVisible(true);

            // Custom drawable using username initials.
            toggle.setHomeAsUpIndicator(getCustomDrawable(user));
            imageView.setImageDrawable(getCustomDrawable(user));

            // Show user name.
            textView.setText(user.getName() + " " + user.getSurname());
        } else {
            mNavigationView.getMenu().getItem(0).setVisible(true);
            mNavigationView.getMenu().getItem(1).setVisible(false);
            mNavigationView.getMenu().getItem(2).setVisible(false);
            mNavigationView.getMenu().getItem(3).setVisible(true);
            mNavigationView.getMenu().getItem(4).setVisible(false);

            // Set default hamburger and Cabify icons.
            toggle.setHomeAsUpIndicator(getDrawable(R.drawable.baseline_dehaze_white_24));
            imageView.setImageDrawable(getDrawable(R.mipmap.ic_launcher_round));

            // Show default text.
            textView.setText(R.string.app_name);
        }

        toggle.setToolbarNavigationClickListener(v -> {
            if (mDrawer.isDrawerVisible(GravityCompat.START)) {
                mDrawer.closeDrawer(GravityCompat.START);
            } else {
                mDrawer.openDrawer(GravityCompat.START);
            }
        });
    }

    /**
     * Update shopping cart FAB content.
     *
     * @param quantity is the amount of items found in a previously stored shopping cart.
     */
    @Override
    public void updateFab(int quantity) {
        // Update the badge showing current amount of items in the shopping cart.
        mQuantity = quantity;
        mNumCartItemsTextView.setText(Integer.toString(quantity));
    }

    /**
     * Show the main tool bar containing the NavigationDrawer.
     */
    @Override
    public void showToolbar() {
        //mToolbar.setVisibility(View.VISIBLE);
        if (mToolbar.getVisibility() == View.GONE) {
            mAnimationUtils.in_from_left(mToolbar);
        }
    }

    /**
     * Hide the main tool bar containing the NavigationDrawer.
     */
    @Override
    public void hideToolbar() {
        //mToolbar.setVisibility(View.GONE);
        mAnimationUtils.out_from_right(mToolbar);
    }

    /**
     * Receive a shopping cart from the Presenter and updates the local shopping cart with this
     * data.
     *
     * @param shoppingCart is the list of {@link StoredShoppingCartItem} items received from the
     *                     Presenter.
     */
    @Override
    public void setShoppingCart(ArrayList<StoredShoppingCartItem> shoppingCart) {
        mShoppingCart = shoppingCart;
    }

    /**
     * Set a listener on the FAB to ask navigator where to go when it is clicked.
     */
    @Override
    public void setupFab() {
        mFab.setOnClickListener(view -> {
            // Navigate to the Shopping Cart and close this activity.
            mNavigator.startShoppingCartActivity(mShoppingCart);
            finish();
        });
    }

    /**
     * Update the local user.
     *
     * @param user is the {@link User} object to be stored.
     */
    @Override
    public void storeUserInfo(User user) {
        mUser = user;
    }

    /* ****************************************************** */
    /* StoreListFragment.OnInteractionListener implementation */
    /* ****************************************************** */

    /**
     * Tell Presenter that a product card has been clicked on the list of products fragment.
     *
     * @param item is the {@link Product} item that has been clicked.
     */
    @Override
    public void onProductClicked(Product item) {
        mPresenter.productClicked(item);
    }

    /**
     * Tell Presenter that "Add to cart" button has been clicked for adding one unit of a given
     * product to the shopping cart.
     *
     * @param productCode is the code of the {@link Product} that should be added to the shopping
     *                    cart.
     */
    @Override
    public void onAddToCartClicked(String productCode) {
        mPresenter.addToCartClicked(mShoppingCart, productCode);
    }

    /* ********************** */
    /* Private helper methods */
    /* ********************** */

    /**
     * @param user is a {@link User} object with the logged in user data.
     * @return a {@link Drawable} object containing the username initials.
     */
    private Drawable getCustomDrawable(User user) {
        // Username initials.
        String initials = user.getName().substring(0, 1) + user.getSurname().substring(0, 1);

        // Image size in pixels from dp.
        float scale = getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (32 * scale + 0.5f);

        return TextDrawable.builder()
                .beginConfig()
                .textColor(Color.BLACK)
                .useFont(Typeface.DEFAULT)
                .toUpperCase()
                .width(dpAsPixels)
                .height(dpAsPixels)
                .endConfig()
                .buildRound(initials, Color.WHITE);
    }
}
