package com.example.android.cabifymobilechallenge.store;

import android.content.Context;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.presenter.BasePresenter;
import com.example.android.cabifymobilechallenge.common.presenter.Callback;
import com.example.android.cabifymobilechallenge.data.pojo.products.Product;
import com.example.android.cabifymobilechallenge.data.pojo.products.ProductDetails;
import com.example.android.cabifymobilechallenge.data.pojo.products.Products;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.StoredShoppingCartItem;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;

import java.util.ArrayList;

public class StorePresenter
        extends BasePresenter<StoreContract.View>
        implements StoreContract.Presenter {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

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
    public StorePresenter(Context context, StoreContract.View view) {
        super(context, view);

        // Create interactor.
        mInteractor = new StoreInteractor(mContext);
    }

    /* ************************************** */
    /* StoreContract.Presenter implementation */
    /* ************************************** */

    /**
     * Tell view what to do for displaying its initial state.
     */
    @Override
    public void start() {
        mView.setupToolbar();
        mView.setupFab();
        mView.hideContent();
        mView.hideErrorMsg();
        mView.hideFab();
        mView.showProgressBar();

        // Ask Interactor if user is logged in.
        AskInteractorForLogin();

        // Ask Interactor for Store content.
        AskInteractorForProducts();
    }

    /**
     * Ask Interactor for a previously stored shopping cart and return it to the View. Also tell
     * View to initialize its shopping cart layout depending on the previous shopping cart has been
     * found or not.
     *
     * @return the list of {@link StoredShoppingCartItem} items found, if it exists; null otherwise.
     */
    @Override
    public void initShoppingCart() {
        // Ask Interactor for the previously stored shopping cart, if it exists, waiting for the
        // result in a Callback object.
        mInteractor.getSavedShoppingCart(new Callback<ArrayList<StoredShoppingCartItem>>() {
            @Override
            public void returnResult(ArrayList<StoredShoppingCartItem> shoppingCart) {
                // Interactor has returned a previously stored shopping cart. Tell View to setup the
                // shopping cart FAB layout depending on the content of the retrieved stored
                // shopping cart.
                int quantity = 0;
                if (shoppingCart != null) {
                    if (shoppingCart.size() > 0) {
                        for (StoredShoppingCartItem item : shoppingCart) {
                            quantity += item.getQuantity();
                        }
                    }
                }

                mView.setShoppingCart(shoppingCart);
                mView.updateFab(quantity);
                if (quantity > 0) {
                    mView.showFab();
                } else {
                    mView.hideFab();
                }
            }

            @Override
            public void returnError(String message) {
                // TODO: Interactor has returned an error.
            }
        });
    }

    /**
     * User has asked to close current session.
     */
    @Override
    public void loggedout() {
        // Ask interactor to perform the loggedout operation.
        mInteractor.logout();

        // Tell View to refresh NavigationDrawer.
        mView.setupNavigationDrawer(null);
    }

    /**
     * User has just logged in.
     */
    @Override
    public void loggedin() {
        // Retrieve login data from Interactor and ask View to configure the Navigation Drawer with
        // this login data.
        AskInteractorForLogin();
    }

    /**
     * User has clicked on a product in the View. Ask Interactor for details of this product and
     * tell view to show them.
     *
     * @param product is the {@link Product} item that has been clicked.
     */
    @Override
    public void productClicked(Product product) {
        // Show and hide the appropriate View elements while observing the API call result.
        mView.hideContent();
        mView.hideErrorMsg();
        mView.hideFab();
        mView.showProgressBar();

        // Ask Interactor for the product details, waiting for the result in a Callback object.
        mInteractor.getProductDetails(product.getCode(), new Callback<ProductDetails>() {
            @Override
            public void returnResult(ProductDetails productDetails) {
                // Interactor has returned the product details.
                mView.hideProgressBar();

                if (productDetails != null) {
                    // Tell view to show the product details.
                    mView.hideToolbar();
                    mView.showProductDetails(productDetails);
                    mView.showContent();
                    mView.showFab();
                } else {
                    // Interactor has returned a null object.
                    mView.showErrorMsg(mContext.getString(R.string.product_details_error));
                }
            }

            @Override
            public void returnError(String message) {
                // Interactor has returned an error.
                mView.hideProgressBar();
                mView.showErrorMsg(message);
            }
        });
    }

    /**
     * Add one unit of the given product to the shopping cart, tell view to refresh its shopping
     * cart layout and ask interactor to update the shopping cart in local storage.
     *
     * @param shoppingCart is the list of {@link StoredShoppingCartItem} items containing the current
     *                     shopping cart.
     * @param productCode  is the code of the {@link Product} to be added to the shopping cart.
     */
    @Override
    public void addToCartClicked(ArrayList<StoredShoppingCartItem> shoppingCart,
                                 String productCode) {
        // Ask Interactor to update the shopping cart in local storage, waiting for the result in a
        // Callback object.
        mInteractor.addShoppingCartItem(productCode,
                new Callback<ArrayList<StoredShoppingCartItem>>() {
                    @Override
                    public void returnResult(ArrayList<StoredShoppingCartItem> newShoppingCart) {
                        // Tell View to refresh the shopping cart layout.
                        mView.addShoppingCartItem(newShoppingCart);
                        mView.showFab();
                        mView.displayAddToCartAnimation();
                    }

                    @Override
                    public void returnError(String message) {
                        // Interactor has returned an error.
                    }
                });
    }

    /* ********************** */
    /* Private helper methods */
    /* ********************** */

    /**
     * Ask Interactor for Store content, waiting for the result in a Callback object.
     */
    private void AskInteractorForProducts() {
        mInteractor.getProducts(new Callback<Products>() {
            @Override
            public void returnResult(Products products) {
                // Interactor has returned a list of products.
                mView.hideProgressBar();
                if (products != null) {
                    if (products.getProducts().size() > 0) {
                        // Pass the products list to the View. Show and hide the appropriate
                        // View elements in order to display results.
                        mView.setProducts(products);
                        mView.showContent();
                        mView.showFab();
                    } else {
                        // Interactor has returned an empty products list.
                        mView.showErrorMsg(mContext.getString(R.string.no_products));
                    }
                } else {
                    // Interactor has returned a null object.
                    mView.showErrorMsg(mContext.getString(R.string.products_error));
                }
            }

            @Override
            public void returnError(String message) {
                // Interactor has returned an error.
                mView.hideProgressBar();
                mView.showErrorMsg(message);
            }
        });
    }

    /**
     * Ask Interactor if there is a valid stored login session.
     */
    private void AskInteractorForLogin() {
        mInteractor.isUserLogged(new Callback<String>() {
            @Override
            public void returnResult(String userId) {
                if (!userId.equals("")) {
                    // There is a current stored valid session. Ask Interactor for user data.
                    AskInteractorForUserData(userId);
                } else {
                    // Tell View to setup NavigationDrawer with no active session.
                    mView.setupNavigationDrawer(null);
                }
            }

            @Override
            public void returnError(String message) {
                // TODO: manage error.
            }
        });
    }

    /**
     * Ask Interactor for user data.
     *
     * @param userId is the {@link User} identifier of the user to be fetched.
     */
    private void AskInteractorForUserData(String userId) {
        mInteractor.getUser(userId, new Callback<User>() {
            @Override
            public void returnResult(User user) {
                // Interactor has decided if there is a current and active login session. Tell View
                // to configure the Navigation Drawer depending on this information.
                mView.storeUserInfo(user);
                mView.setupNavigationDrawer(user);
            }

            @Override
            public void returnError(String message) {
                // TODO: manage error.
            }
        });
    }
}
