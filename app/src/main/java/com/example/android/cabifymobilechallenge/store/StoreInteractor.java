package com.example.android.cabifymobilechallenge.store;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.model.BaseInteractor;
import com.example.android.cabifymobilechallenge.common.presenter.Callback;
import com.example.android.cabifymobilechallenge.data.SampleData;
import com.example.android.cabifymobilechallenge.data.pojo.discounts.Discount;
import com.example.android.cabifymobilechallenge.data.pojo.discounts.DiscountTypeMxN;
import com.example.android.cabifymobilechallenge.data.pojo.discounts.DiscountTypeNorMore;
import com.example.android.cabifymobilechallenge.data.pojo.products.Product;
import com.example.android.cabifymobilechallenge.data.pojo.products.ProductDetails;
import com.example.android.cabifymobilechallenge.data.pojo.products.Products;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.ShoppingCartItem;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.StoredShoppingCartItem;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class StoreInteractor
        extends BaseInteractor
        implements StoreContract.Interactor {

    public static final int MIN_SHOPPING_CART_AMOUNT = 1;
    public static final int MAX_SHOPPING_CART_AMOUNT = 99;

    /**
     * Public constructors
     *
     * @param context is the Context of the calling View.
     */
    public StoreInteractor(Context context) {
        super(context);
    }

    /* *************************************** */
    /* StoreContract.Interactor implementation */
    /* *************************************** */

    /**
     * Create an {@link Single} Observable for retrieving the current login state.
     *
     * @param callback is the {@link Callback<String>} object that is going to receive the result
     *                 of this API call.
     */
    @SuppressLint("CheckResult")
    @Override
    public void isUserLogged(Callback<String> callback) {
        // Create the Observable.
        Single<String> loginState = Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    // User is currently logged if there are email, password and session token
                    // stored in Preferences.
                    String userId = "";
                    if (getUserId() != null && !getUserId().equals("") &&
                            getEmail() != null && !getEmail().equals("") &&
                            getPassword() != null && !getPassword().equals("") &&
                            getToken() != null && !getToken().equals("")) {
                        // If there is a current valid session, return stored user id.
                        userId = getUserId();
                    }
                    emitter.onSuccess(userId);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
            thread.start();
        });

        // Observe for results.
        loginState.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String userId) {
                        callback.returnResult(userId);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.returnError(e.getMessage());
                    }
                });
    }

    /**
     * Create an {@link Single} Observable for retrieving products from API.
     *
     * @param callback is the {@link Callback<Products>} object that is going to receive the result
     *                 of this API call.
     */
    @SuppressLint("CheckResult")
    @Override
    public void getProducts(Callback<Products> callback) {
        // Create the Observable.
        Single<Products> products = mApiService.getProducts();

        // Observe for results.
        products.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Products>() {
                    @Override
                    public void onSuccess(Products products) {
                        // The API should return more info about products, such as a small image
                        // path or discount info. Here we use some sample data to enhance the
                        // products information for the list.
                        for (int i = 0; i < products.getProducts().size(); i++) {
                            Product product = products.getProducts().get(i);
                            product.setImageResId(
                                    SampleData.getSampleImageSmall(product.getCode()));
                            product.setDiscount(
                                    SampleData.getSampleHasDiscount(product.getCode()));
                            products.getProducts().set(i, product);
                        }

                        callback.returnResult(products);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.returnError(e.getMessage());
                    }
                });
    }

    /**
     * Create an {@link Single} Observable for retrieving products from API.
     *
     * @param callback is the {@link Callback<ProductDetails>} object that is going to receive the
     *                 result of this API call.
     */
    @SuppressLint("CheckResult")
    @Override
    public void getProductDetails(String productCode, Callback<ProductDetails> callback) {
        // Create the Observable.
        Single<ProductDetails> productDetails = Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    // The API call doesn't exist, so we return some sample data.
                    emitter.onSuccess(SampleData.getProductDetails(mContext, productCode));
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
            thread.start();
        });

        // Observe for results.
        productDetails
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ProductDetails>() {
                    @Override
                    public void onSuccess(ProductDetails productDetails) {
                        callback.returnResult(productDetails);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.returnError(e.getMessage());
                    }
                });
    }

    /**
     * Create an {@link Single} Observable for retrieving product details from API and building a
     * list of {@link ShoppingCartItem} objects.
     *
     * @param shoppingCart is the list of {@link StoredShoppingCartItem} objects used as parameter
     *                     to the API call.
     * @param callback     is the {@link Callback<ArrayList<ShoppingCartItem>>} object that is going
     *                     to receive the result of this API call.
     */
    @SuppressLint("CheckResult")
    @Override
    public void getShoppingCartProductDetails(ArrayList<StoredShoppingCartItem> shoppingCart,
                                              Callback<ArrayList<ShoppingCartItem>> callback) {
        // Create the Observable.
        Single<ArrayList<ShoppingCartItem>> shoppingCartProductDetails = Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    // The API call doesn't exist, so we return some sample data.
                    ArrayList<ShoppingCartItem> result = new ArrayList<>();
                    ProductDetails productDetails;
                    for (StoredShoppingCartItem item : shoppingCart) {
                        productDetails = SampleData.getProductDetails(mContext, item.getProductCode());
                        result.add(getShoppingCartItemDetails(item.getProductCode(),
                                item.getQuantity(), productDetails.getName(),
                                productDetails.getPrice(), productDetails.getDiscount()));
                    }
                    emitter.onSuccess(result);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
            thread.start();
        });

        // Observe for results.
        shoppingCartProductDetails
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<ShoppingCartItem>>() {
                    @Override
                    public void onSuccess(ArrayList<ShoppingCartItem> shoppingCartProductDetails) {
                        callback.returnResult(shoppingCartProductDetails);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.returnError(e.getMessage());
                    }
                });
    }

    /**
     * Create an {@link Single} Observable for retrieving the stored shopping cart from Preferences
     * and building a list of {@link StoredShoppingCartItem} objects.
     *
     * @param callback is the {@link Callback<ArrayList<StoredShoppingCartItem>>} object that is
     *                 going to receive the result of this call.
     */
    @SuppressLint("CheckResult")
    @Override
    public void getSavedShoppingCart(Callback<ArrayList<StoredShoppingCartItem>> callback) {
        // Create the Observable.
        Single<ArrayList<StoredShoppingCartItem>> storedShoppingCart = Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    // Return the stored shopping cart from Preferences.
                    emitter.onSuccess(getStoredShoppingCart());
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
            thread.start();
        });

        // Observe for results.
        storedShoppingCart
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<StoredShoppingCartItem>>() {
                    @Override
                    public void onSuccess(ArrayList<StoredShoppingCartItem> shoppingCartProductDetails) {
                        callback.returnResult(shoppingCartProductDetails);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.returnError(e.getMessage());
                    }
                });
    }

    /**
     * Create an {@link Single} Observable for removing the stored shopping cart entry corresponding
     * to a given product code from Preferences.
     *
     * @param code     is the product code to be removed.
     * @param callback is the {@link Callback<ArrayList>} object that is going to receive the result
     *                 of this call.
     */
    @SuppressLint("CheckResult")
    @Override
    public void deleteShoppingCartItem(String code,
                                       Callback<ArrayList<StoredShoppingCartItem>> callback) {
        // Create the Observable.
        Single<ArrayList<StoredShoppingCartItem>> deleteStoredShoppingCartItem =
                Single.create(emitter -> {
                    Thread thread = new Thread(() -> {
                        try {
                            // Go through shared Preferences.
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            Map<String, ?> preferences = sharedPreferences.getAll();
                            for (Map.Entry<String, ?> entry : preferences.entrySet()) {
                                if (entry.getKey().contains(PREFERENCES_BASE_PRODUCT_KEY)) {
                                    // Shopping cart item found.
                                    String foundCode =
                                            entry.getKey().substring(PREFERENCES_BASE_PRODUCT_KEY.length());
                                    if (foundCode.equals(code)) {
                                        // If found Shopping cart item code is the same than the code
                                        // parameter, remove this entry.
                                        editor.remove(entry.getKey());
                                        editor.apply();
                                    }
                                }
                            }

                            // Return the updated shopping cart from Preferences.
                            emitter.onSuccess(getStoredShoppingCart());
                        } catch (Exception e) {
                            emitter.onError(e);
                        }
                    });
                    thread.start();
                });

        // Observe for results.
        deleteStoredShoppingCartItem
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<StoredShoppingCartItem>>() {
                    @Override
                    public void onSuccess(ArrayList<StoredShoppingCartItem> storedShoppingCart) {
                        callback.returnResult(storedShoppingCart);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.returnError(e.getMessage());
                    }
                });
    }

    /**
     * Create an {@link Single} Observable for increasing by one the number of items of a given
     * product in Preferences.
     *
     * @param productCode is the product code, which will be used to get the Preferences key for the
     *                    corresponding Cabify product.
     * @param callback    is the {@link Callback<ArrayList>} object that is going to receive the
     *                    result of this call.
     */
    @SuppressLint("CheckResult")
    @Override
    public void addShoppingCartItem(String productCode,
                                    Callback<ArrayList<StoredShoppingCartItem>> callback) {
        // Create the Observable.
        Single<ArrayList<StoredShoppingCartItem>> addStoredShoppingCartItem =
                Single.create(emitter -> {
                    Thread thread = new Thread(() -> {
                        try {
                            // Increase by one the amount of elements of current product stored in
                            // Preferences and update it.
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt(PREFERENCES_BASE_PRODUCT_KEY + productCode,
                                    getShoppingCartItem(productCode) + 1);
                            editor.apply();

                            // Return the updated shopping cart from Preferences.
                            emitter.onSuccess(getStoredShoppingCart());
                        } catch (Exception e) {
                            emitter.onError(e);
                        }
                    });
                    thread.start();
                });

        // Observe for results.
        addStoredShoppingCartItem
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<StoredShoppingCartItem>>() {
                    @Override
                    public void onSuccess(ArrayList<StoredShoppingCartItem> shoppingCart) {
                        callback.returnResult(shoppingCart);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.returnError(e.getMessage());
                    }
                });
    }

    /**
     * Create an {@link Single} Observable for decreasing by one the number of items of a given
     * product in Preferences.
     *
     * @param productCode is the product code, which will be used to get the Preferences key for the
     *                    corresponding Cabify product.
     * @param callback    is the {@link Callback<ArrayList<StoredShoppingCartItem>>} object that is
     *                    going to receive the result of this call.
     */
    @SuppressLint("CheckResult")
    @Override
    public void subtractShoppingCartItem(String productCode,
                                         Callback<ArrayList<StoredShoppingCartItem>> callback) {
        // Create the Observable.
        Single<ArrayList<StoredShoppingCartItem>> addStoredShoppingCartItem =
                Single.create(emitter -> {
                    Thread thread = new Thread(() -> {
                        try {
                            // Increase by one the amount of elements of current product stored in
                            // Preferences and update it.
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt(PREFERENCES_BASE_PRODUCT_KEY + productCode,
                                    getShoppingCartItem(productCode) - 1);
                            editor.apply();

                            // Return the updated shopping cart from Preferences.
                            emitter.onSuccess(getStoredShoppingCart());
                        } catch (Exception e) {
                            emitter.onError(e);
                        }
                    });
                    thread.start();
                });

        // Observe for results.
        addStoredShoppingCartItem
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<ArrayList<StoredShoppingCartItem>>() {
                    @Override
                    public void onSuccess(ArrayList<StoredShoppingCartItem> shoppingCart) {
                        callback.returnResult(shoppingCart);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.returnError(e.getMessage());
                    }
                });
    }

    /**
     * Create an {@link Single} Observable for retrieving user data.
     *
     * @param userId   is the user identifier, which will be used to get the user data.
     * @param callback is the {@link Callback<User>} object that is going to receive the result of
     *                 this call.
     */
    @SuppressLint("CheckResult")
    @Override
    public void getUser(String userId, Callback<User> callback) {
        // Create the Observable.
        Single<User> loginState = Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    // The API call doesn't exist, so we return a sample User object.
                    User user = SampleData.getUser();
                    user.setEmail(getEmail());
                    user.setPassword(getPassword());
                    emitter.onSuccess(user);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
            thread.start();
        });

        // Observe for results.
        loginState.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<User>() {
                    @Override
                    public void onSuccess(User user) {
                        callback.returnResult(user);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.returnError(e.getMessage());
                    }
                });
    }

    /**
     * @return the number of items of a given product stored in Preferences from a previous shopping
     * cart.
     */
    @Override
    public int getShoppingCartItem(String productKey) {
        return sharedPreferences.getInt(PREFERENCES_BASE_PRODUCT_KEY + productKey, 0);
    }

    /**
     * @param discount is the {@link Discount} object containing the product discount. It may be
     *                 null.
     * @return a string describing the product discount, or an empty string if there was no
     * discount.
     */
    @Override
    public String getStringDiscount(Discount discount) {
        String discountDescription = "";
        if (discount != null) {
            switch (discount.getType()) {
                case Discount.TYPE_M_X_N:
                    DiscountTypeMxN discountTypeMxN = (DiscountTypeMxN) discount.getDiscount();
                    discountDescription = mContext.getString(R.string.discount_description_m_x_n,
                            discountTypeMxN.getM(), discountTypeMxN.getN());
                    break;

                case Discount.TYPE_N_OR_MORE:
                    DiscountTypeNorMore discountTypeNorMore =
                            (DiscountTypeNorMore) discount.getDiscount();
                    discountDescription = mContext.getString(R.string.discount_description_n_or_more,
                            discountTypeNorMore.getN(), String.format(Locale.getDefault(),
                                    "%.2f", discountTypeNorMore.getPrice()));
            }
        }
        return discountDescription;
    }

    /* ********************** */
    /* Private helper methods */
    /* ********************** */

    /**
     * @param code     is the product code.
     * @param quantity is the amount of units of the given product.
     * @param name     is the product name.
     * @param price    is the price per unit.
     * @param discount is the {@link Discount} applied to the current product. It may be null.
     * @return a {@link ShoppingCartItem} object.
     */
    private ShoppingCartItem getShoppingCartItemDetails(String code, int quantity, String name,
                                                        Double price, Discount discount) {
        // Build discount data.
        String discountText = "";
        Double discountPrice = 0.0;
        if (discount != null) {
            switch (discount.getType()) {
                case Discount.TYPE_M_X_N: {
                    int m = ((DiscountTypeMxN) discount.getDiscount()).getM();
                    int n = ((DiscountTypeMxN) discount.getDiscount()).getN();
                    if (quantity >= m) {
                        discountText = mContext.getString(R.string.discount_m_x_n, m, n);
                        discountPrice = price * ((((quantity / m) + (quantity % m)) * n) - quantity);
                        //discountPrice = (price * (quantity / m) * n) + (price * (quantity % m) * n) - (price * quantity);
                    }
                    break;
                }

                case Discount.TYPE_N_OR_MORE: {
                    int n = ((DiscountTypeNorMore) discount.getDiscount()).getN();
                    Double p = ((DiscountTypeNorMore) discount.getDiscount()).getPrice();
                    if (quantity >= n) {
                        discountText = mContext.getString(R.string.discount_n_or_more, n,
                                String.format(Locale.getDefault(), "%.2f", p));
                        discountPrice = quantity * (p - price);
                    }
                }
            }
        }

        return new ShoppingCartItem(code, quantity, name, price, price * quantity,
                discount, discountPrice, discountText);
    }

    /**
     * @return the currently stored list of {@link StoredShoppingCartItem} items from Preferences.
     */
    private ArrayList<StoredShoppingCartItem> getStoredShoppingCart() {
        ArrayList<StoredShoppingCartItem> shoppingCart = new ArrayList<>();
        Map<String, ?> preferences = sharedPreferences.getAll();
        for (Map.Entry<String, ?> entry : preferences.entrySet()) {
            if (entry.getKey().contains(PREFERENCES_BASE_PRODUCT_KEY)) {
                // Shopping cart item found.
                String code = entry.getKey().substring(
                        PREFERENCES_BASE_PRODUCT_KEY.length());
                int quantity = getShoppingCartItem(code);
                shoppingCart.add(new StoredShoppingCartItem(code, quantity));
            }
        }
        return shoppingCart;
    }
}
