package com.example.android.cabifymobilechallenge.shoppingcart;

import android.content.Context;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.presenter.BasePresenter;
import com.example.android.cabifymobilechallenge.common.presenter.Callback;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.ShoppingCartItem;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.StoredShoppingCartItem;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;
import com.example.android.cabifymobilechallenge.store.StoreInteractor;

import java.util.ArrayList;

public class ShoppingCartPresenter
        extends BasePresenter<ShoppingCartContract.View>
        implements ShoppingCartContract.Presenter {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private StoreInteractor mInteractor;
    private ArrayList<ShoppingCartItem> mShoppingCart;

    /**
     * Public constructor.
     *
     * @param context is the {@link Context} of the view that instantiates this class.
     * @param view    is the ViewType view that instantiates this class.
     */
    public ShoppingCartPresenter(Context context, ShoppingCartContract.View view) {
        super(context, view);

        // Create interactor.
        mInteractor = new StoreInteractor(mContext);
    }

    /* ********************************************* */
    /* ShoppingCartContract.Presenter implementation */
    /* ********************************************* */

    /**
     * Set View initial state.
     */
    @Override
    public void start(ArrayList<StoredShoppingCartItem> storedShoppingCart) {
        // Setup toolbar.
        mView.setupToolbar();

        // Setup layout elements.
        mView.setupItemsList();
        mView.setupListeners();
        mView.showProgress();
        mView.hideErrorMsg();
        mView.hideContent();

        // Ask Interactor for shopping cart items details, waiting for the result in a Callback
        // object. We have a list of StoredShoppingCartItem items so far, containing product codes
        // and quantities. Maybe these product codes are deprecated or prices has changed since they
        // were stored, so we need to ask Interactor for the updated product details.
        mInteractor.getShoppingCartProductDetails(storedShoppingCart,
                new Callback<ArrayList<ShoppingCartItem>>() {
                    @Override
                    public void returnResult(ArrayList<ShoppingCartItem> shoppingCart) {
                        // Interactor has returned a list of products.
                        mView.hideProgress();
                        if (shoppingCart != null) {
                            mShoppingCart = shoppingCart;
                            if (shoppingCart.size() > 0) {
                                // Pass the shopping cart to the View. Show and hide the appropriate
                                // View elements in order to display results.
                                mView.loadShoppingCart(shoppingCart);
                                mView.showContent();
                                mView.setPayButton(mInteractor.getAmountToPay(shoppingCart));
                                mView.setTotalDiscount(mInteractor.getTotalDiscount(shoppingCart));
                            } else {
                                // Interactor has returned an empty shopping cart.
                                mView.showErrorMsg(mContext.getString(
                                        R.string.shopping_cart_is_emtpy));
                            }
                        } else {
                            // Interactor has returned a null object.
                            mView.showErrorMsg(mContext.getString(R.string.shopping_cart_error));
                        }
                    }

                    @Override
                    public void returnError(String message) {
                        // Interactor has returned an error.
                        mView.hideProgress();
                        mView.showErrorMsg(message);
                    }
                });
    }

    /**
     * Delete a product from the shopping cart.
     *
     * @param code is the product code to be removed.
     */
    @Override
    public void removeShoppingCartItem(String code, int index) {
        // Ask Interactor to remove a shopping cart item, waiting for the result in a Callback
        // object.
        mInteractor.deleteShoppingCartItem(code, new Callback<ArrayList<StoredShoppingCartItem>>() {
            @Override
            public void returnResult(ArrayList<StoredShoppingCartItem> storedShoppingCart) {
                // If item has been successfully removed, tell View to update the shopping cart.
                mView.removeShoppingCartItem(index);

                // Ask Interactor for shopping cart items details, waiting for the result in a
                // Callback object.
                mInteractor.getShoppingCartProductDetails(storedShoppingCart,
                        new Callback<ArrayList<ShoppingCartItem>>() {
                            @Override
                            public void returnResult(ArrayList<ShoppingCartItem> shoppingCart) {
                                // Interactor has returned a list of products. Update the local
                                // Presenter shopping cart.
                                mShoppingCart = shoppingCart;
                                mView.setPayButton(mInteractor.getAmountToPay(shoppingCart));
                                mView.setTotalDiscount(mInteractor.getTotalDiscount(shoppingCart));
                            }

                            @Override
                            public void returnError(String message) {
                                // TODO: manage error.
                            }
                        });
            }

            @Override
            public void returnError(String message) {
                // TODO: manage error.
            }
        });
    }

    /**
     * User has clicked on "Add" button.
     */
    @Override
    public void addShoppingCartItem(String code, int adapterIndex) {
        int listIndex = mInteractor.getShoppingCartIndex(mShoppingCart, code);
        if (mShoppingCart.get(listIndex).getQuantity() < mInteractor.MAX_SHOPPING_CART_AMOUNT) {
            // Ask Interactor to increase by one the amount of a shopping cart item, waiting for the
            // result in a Callback object.
            mInteractor.addShoppingCartItem(code, new Callback<ArrayList<StoredShoppingCartItem>>() {
                @Override
                public void returnResult(ArrayList<StoredShoppingCartItem> storedShoppingCart) {
                    // Ask Interactor for shopping cart items details, waiting for the result in a
                    // Callback object.
                    mInteractor.getShoppingCartProductDetails(storedShoppingCart,
                            new Callback<ArrayList<ShoppingCartItem>>() {
                                @Override
                                public void returnResult(ArrayList<ShoppingCartItem> shoppingCart) {
                                    // Interactor has returned a list of products. Update the local
                                    // Presenter shopping cart.
                                    mShoppingCart = shoppingCart;

                                    // Tell View to update the corresponding adapter entry and the
                                    // button with the total amount to pay.
                                    mView.update(adapterIndex, mShoppingCart.get(listIndex));
                                    mView.setPayButton(mInteractor.getAmountToPay(shoppingCart));
                                    mView.setTotalDiscount(mInteractor.getTotalDiscount(shoppingCart));
                                }

                                @Override
                                public void returnError(String message) {
                                    // TODO: manage error.
                                }
                            });
                }

                @Override
                public void returnError(String message) {
                    // TODO: manage error.
                }
            });
        }
    }

    /**
     * User has clicked on "Subtract" button.
     */
    @Override
    public void subtractShoppingCartItem(String code, int adapterIndex) {
        int listIndex = mInteractor.getShoppingCartIndex(mShoppingCart, code);
        if (mShoppingCart.get(listIndex).getQuantity() > mInteractor.MIN_SHOPPING_CART_AMOUNT) {
            // Ask Interactor to increase by one the amount of a shopping cart item, waiting for the
            // result in a Callback object.
            mInteractor.subtractShoppingCartItem(code, new Callback<ArrayList<StoredShoppingCartItem>>() {
                @Override
                public void returnResult(ArrayList<StoredShoppingCartItem> storedShoppingCart) {
                    // Ask Interactor for shopping cart items details, waiting for the result in a
                    // Callback object.
                    mInteractor.getShoppingCartProductDetails(storedShoppingCart,
                            new Callback<ArrayList<ShoppingCartItem>>() {
                                @Override
                                public void returnResult(ArrayList<ShoppingCartItem> shoppingCart) {
                                    // Interactor has returned a list of products. Update the local
                                    // Presenter shopping cart.
                                    mShoppingCart = shoppingCart;

                                    // Tell View to update the corresponding adapter entry and the
                                    // button with the total amount to pay.
                                    mView.update(adapterIndex, mShoppingCart.get(listIndex));
                                    mView.setPayButton(mInteractor.getAmountToPay(shoppingCart));
                                    mView.setTotalDiscount(mInteractor.getTotalDiscount(shoppingCart));
                                }

                                @Override
                                public void returnError(String message) {
                                    // TODO: manage error.
                                }
                            });
                }

                @Override
                public void returnError(String message) {
                    // TODO: manage error.
                }
            });
        }
    }

    /**
     * User has clicked on "Empty cart" button.
     */
    @Override
    public void deleteButtonClicked() {
        // Ask Interactor to delete the shopping cart, waiting for the result in a Callback object.
        mInteractor.clearShoppingCart(new Callback<Boolean>() {
            @Override
            public void returnResult(Boolean deleted) {
                if (deleted) {
                    mShoppingCart = new ArrayList<>();

                    // If shopping cart has been successfully deleted, tell View to update the
                    // shopping cart layout.
                    mView.deleteShoppingCart();
                    mView.hideContent();
                    mView.showErrorMsg(mContext.getString(R.string.shopping_cart_is_emtpy));
                }
            }

            @Override
            public void returnError(String message) {
                // TODO: manage error.
            }
        });
    }

    /**
     * User has clicked on "Pay" button.
     */
    @Override
    public void checkoutButtonClicked() {
        mInteractor.isUserLogged(new Callback<String>() {
            @Override
            public void returnResult(String userId) {
                if (!userId.equals("")) {
                    // User is currently logged in. Retrieve all user data from Interactor.
                    mInteractor.getUser(userId, new Callback<User>() {
                        @Override
                        public void returnResult(User user) {
                            // Tell View to go on to the payment activity.
                            mView.gotoCheckout(user, mShoppingCart);
                        }

                        @Override
                        public void returnError(String message) {
                            // TODO: manage error.
                        }
                    });
                } else {
                    // User is not currently logged in. Tell View to load login activity.
                    mView.notLoggedIn();
                }
            }

            @Override
            public void returnError(String message) {
                // TODO: manage error.
            }
        });
    }
}
