package com.example.android.cabifymobilechallenge.common.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.widget.ImageView;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.BaseMVP;
import com.example.android.cabifymobilechallenge.common.presenter.Callback;
import com.example.android.cabifymobilechallenge.data.api.ApiClient;
import com.example.android.cabifymobilechallenge.data.api.ApiService;
import com.example.android.cabifymobilechallenge.data.pojo.Error;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.ShoppingCartItem;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class BaseInteractor implements BaseMVP.Interactor {

    public final String PREFERENCES_BASE_PRODUCT_KEY = "cabify_product_key_";

    private static String URL_PRIVACY = "https://cabify.com/spain/privacy_policy";
    private static String URL_CONDITIONS = "https://cabify.com/spain/terms";
    private static String URL_HELP = "https://help.cabify.com/hc/es";
    private static String URL_ACCOUNT_KIT = "https://www.accountkit.com/faq/?locale=es_ES#0";
    private static String URL_FACEBOOK_CONDITIONS = "https://www.facebook.com/legal/terms";
    private static String URL_FACEBOOK_POLICIY = "https://www.facebook.com/privacy/explanation";
    private static String URL_FACEBOOK_COOKIES = "https://www.facebook.com/policies/cookies/";

    /* **************** */
    /* Member variables */
    /* **************** */

    public Context mContext;
    public ApiService mApiService;
    public static SharedPreferences sharedPreferences;
    public static Resources res;
    private Picasso mPicasso;

    /* ************** */
    /* Public methods */
    /* ************** */

    /**
     * Public constructor.
     *
     * @param context is the Context of the calling View.
     */
    public BaseInteractor(Context context) {
        mContext = context;

        // Specify the unsafe HTTP client from the API client as the Downloader that will be used
        // for downloading images using {@link Picasso}, in order to avoid errors while getting
        // images from a https url.
        mPicasso = new Picasso.Builder(mContext)
                .downloader(new OkHttp3Downloader(ApiClient.getUnsafeOkHttpClient().build()))
                .build();

        // Get SharedPreferences object.
        sharedPreferences = getDefaultSharedPreferences(mContext);
        res = mContext.getResources();

        // Create the API client.
        mApiService = ApiClient.getClient().create(ApiService.class);
    }

    /* ********************************* */
    /* BaseMVP.Interactor implementation */
    /* ********************************* */

    /**
     * @param shoppingCart is the list of {@link ShoppingCartItem} objects.
     * @return the total amount to pay, taking in account prices and discounts.
     */
    @Override
    public Double getAmountToPay(ArrayList<ShoppingCartItem> shoppingCart) {
        Double amount = 0.0;
        if (shoppingCart != null) {
            for (ShoppingCartItem item : shoppingCart) {
                amount += item.getTotalPrice();
                if (item.getDiscount() != null) {
                    amount += item.getDiscountPrice();
                }
            }
        }
        return amount;
    }

    /**
     * @param shoppingCart is the list of {@link ShoppingCartItem} objects.
     * @return the total shopping cart discount.
     */
    @Override
    public double getTotalDiscount(ArrayList<ShoppingCartItem> shoppingCart) {
        Double discount = 0.0;
        if (shoppingCart != null) {
            for (ShoppingCartItem item : shoppingCart) {
                discount += item.getDiscountPrice();
            }
        }
        return -discount;
    }

    /**
     * @param shoppingCart is the list of {@link ShoppingCartItem} objects.
     * @param code         is the product code to search.
     * @return the index of a given product code into the local list of shopping cart items.
     */
    @Override
    public int getShoppingCartIndex(ArrayList<ShoppingCartItem> shoppingCart, String code) {
        boolean found = false;
        int i = 0;
        if (shoppingCart.size() > 0) {
            while (!found && i < shoppingCart.size()) {
                if (shoppingCart.get(i).getCode().equals(code)) {
                    found = true;
                } else {
                    i++;
                }
            }
        }
        return i;
    }

    /**
     * @param errorBody is the {@link ResponseBody} object with the error information from the API.
     * @return an {@link Error} object containing the error code and a the error message.
     */
    @Override
    public Error getServerError(ResponseBody errorBody) {
        // TODO: Parse errorBody, construct the Error object and return it.
        return getServerUnknownError();
    }

    /**
     * Load an image from the internet using a CustomPicasso object created from Picasso library.
     *
     * @param imagePath     is the url to retrieve the image from.
     * @param imageView     is the ImageView in which we want to load the image.
     * @param drawableResId is the drawable resource id to show when there has been an error while
     *                      retrieving the image from internet.
     */
    @Override
    public void loadImage(String imagePath, ImageView imageView, int drawableResId) {
        String path = ApiClient.BASE_URL + imagePath;
        mPicasso.load(path).placeholder(drawableResId).error(drawableResId).into(imageView);
    }

    /**
     * Overloaded method to load an image from the internet using a CustomPicasso object created
     * from Picasso library, with no default image to load if an error happens.
     *
     * @param imagePath is the url to retrieve the image from.
     * @param imageView is the ImageView in which we want to load the image.
     */
    @Override
    public void loadImage(String imagePath, ImageView imageView) {
        String path = ApiClient.BASE_URL + imagePath;
        mPicasso.load(path).into(imageView);
    }

    /**
     * @return an unknown {@link Error} object.
     */
    @Override
    public Error getServerUnknownError() {
        return new Error(500, mContext.getString(R.string.api_unknown_error));
    }

    /**
     * Store the user's identifier in the preferences file.
     *
     * @param userId is the user's id.
     */
    @Override
    public void saveUserId(String userId) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(res.getString(R.string.preferences_id_key), userId);
        editor.apply();
    }

    /**
     * Fetch SharedPreferences for the user's identifier.
     *
     * @return a string with the user's id value stored in the preferences file.
     */
    @Override
    public String getUserId() {
        return sharedPreferences.getString(
                res.getString(R.string.preferences_id_key), "");
    }

    /**
     * Store the user's email in the preferences file.
     *
     * @param email is the user's email.
     */
    @Override
    public void saveEmail(String email) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(res.getString(R.string.preferences_email_key), email);
        editor.apply();
    }

    /**
     * Fetch SharedPreferences for the user's email.
     *
     * @return a string with the email value stored in the preferences file.
     */
    @Override
    public String getEmail() {
        return sharedPreferences.getString(
                res.getString(R.string.preferences_email_key), "");
    }

    /**
     * Store the user's password in the preferences file.
     *
     * @param password is the user's password.
     */
    @Override
    public void savePassword(String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(res.getString(R.string.preferences_password_key), password);
        editor.apply();
    }

    /**
     * Fetch SharedPreferences for the user's password.
     *
     * @return a string with the password value stored in the preferences file.
     */
    @Override
    public String getPassword() {
        return sharedPreferences.getString(
                res.getString(R.string.preferences_password_key), "");
    }

    /**
     * Store the user's name in the preferences file.
     *
     * @param name is the user's name.
     */
    @Override
    public void saveUserName(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(res.getString(R.string.preferences_name_key), name);
        editor.apply();
    }

    /**
     * Fetch SharedPreferences for the user's name.
     *
     * @return a string with the name value stored in the preferences file.
     */
    @Override
    public String getUserName() {
        return sharedPreferences.getString(
                res.getString(R.string.preferences_name_key), "");
    }

    /**
     * Store the user's surname in the preferences file.
     *
     * @param surname is the user's surname.
     */
    @Override
    public void saveUserSurname(String surname) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(res.getString(R.string.preferences_surname_key), surname);
        editor.apply();
    }

    /**
     * Fetch SharedPreferences for the user's surname.
     *
     * @return a string with the surname value stored in the preferences file.
     */
    @Override
    public String getUserSurname() {
        return sharedPreferences.getString(
                res.getString(R.string.preferences_surname_key), "");
    }

    /**
     * Store the user's phone in the preferences file.
     *
     * @param phone is the user's phone.
     */
    @Override
    public void saveUserPhone(String phone) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(res.getString(R.string.preferences_phone_key), phone);
        editor.apply();
    }

    /**
     * Fetch SharedPreferences for the user's phone.
     *
     * @return a string with the phone value stored in the preferences file.
     */
    @Override
    public String getUserPhone() {
        return sharedPreferences.getString(
                res.getString(R.string.preferences_phone_key), "");
    }

    /**
     * Store the user's session token in the preferences file.
     *
     * @param token is the user's session token.
     */
    @Override
    public void saveToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(res.getString(R.string.preferences_token_key), token);
        editor.apply();
    }

    /**
     * Fetch SharedPreferences for the user's session token.
     *
     * @return a string with the user's session token value stored in the preferences file.
     */
    @Override
    public String getToken() {
        return sharedPreferences.getString(
                res.getString(R.string.preferences_token_key), "");
    }

    /**
     * Store the tutorial completion in the preferences file.
     */
    @Override
    public void setSkipTutorial() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(res.getString(R.string.preferences_skip_tutorial_key), true);
        editor.apply();
    }

    /**
     * Fetch SharedPreferences and determine whether user has previously completed the tutorial or
     * not.
     *
     * @return true if user has previously completed the tutorial, false otherwise.
     */
    @Override
    public boolean getSkipTutorial() {
        return sharedPreferences.getBoolean(
                res.getString(R.string.preferences_skip_tutorial_key), false);
    }

    /**
     * @return the {@link BaseInteractor#URL_CONDITIONS} constant value.
     */
    @Override
    public String getConditionsUrl() {
        return URL_CONDITIONS;
    }

    /**
     * @return the {@link BaseInteractor#URL_PRIVACY} constant value.
     */
    @Override
    public String getPrivacyUrl() {
        return URL_PRIVACY;
    }

    /**
     * @return the {@link BaseInteractor#URL_HELP} constant value.
     */
    @Override
    public String getHelpUrl() {
        return URL_HELP;
    }

    /**
     * @return the {@link BaseInteractor#URL_ACCOUNT_KIT} constant value.
     */
    @Override
    public String getAccountKitUrl() {
        return URL_ACCOUNT_KIT;
    }

    /**
     * @return the {@link BaseInteractor#URL_FACEBOOK_CONDITIONS} constant value.
     */
    @Override
    public String getFacebookConditionsUrl() {
        return URL_FACEBOOK_CONDITIONS;
    }

    /**
     * @return the {@link BaseInteractor#URL_FACEBOOK_POLICIY} constant value.
     */
    @Override
    public String getFacebookPolicyUrl() {
        return URL_FACEBOOK_POLICIY;
    }

    /**
     * @return the {@link BaseInteractor#URL_FACEBOOK_COOKIES} constant value.
     */
    @Override
    public String getFacebookCookiesUrl() {
        return URL_FACEBOOK_COOKIES;
    }

    /**
     * Create an {@link Single} Observable for removing all stored shopping cart entries from
     * Preferences.
     *
     * @param callback is the {@link Callback <Boolean>} object that is going to receive the result
     *                 of this call.
     */
    @SuppressLint("CheckResult")
    @Override
    public void clearShoppingCart(Callback<Boolean> callback) {
        // Create the Observable.
        Single<Boolean> deleteStoredShoppingCart = Single.create(emitter -> {
            Thread thread = new Thread(() -> {
                try {
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    // Go through shared Preferences.
                    Map<String, ?> preferences = sharedPreferences.getAll();
                    for (Map.Entry<String, ?> entry : preferences.entrySet()) {
                        if (entry.getKey().contains(PREFERENCES_BASE_PRODUCT_KEY)) {
                            // Shopping cart item found.
                            editor.remove(entry.getKey());
                            editor.apply();
                        }
                    }

                    emitter.onSuccess(true);
                } catch (Exception e) {
                    emitter.onError(e);
                }
            });
            thread.start();
        });

        // Observe for results.
        deleteStoredShoppingCart
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Boolean>() {
                    @Override
                    public void onSuccess(Boolean deleted) {
                        callback.returnResult(deleted);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.returnError(e.getMessage());
                    }
                });
    }

    /**
     * Close local user session by cleaning all stored login data.
     */
    @Override
    public void logout() {
        saveToken("");
        saveUserId("");
    }
}
