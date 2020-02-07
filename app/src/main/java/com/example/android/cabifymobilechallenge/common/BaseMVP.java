package com.example.android.cabifymobilechallenge.common;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

import com.example.android.cabifymobilechallenge.common.presenter.Callback;
import com.example.android.cabifymobilechallenge.common.view.adapter.BaseAdapter;
import com.example.android.cabifymobilechallenge.data.pojo.Error;
import com.example.android.cabifymobilechallenge.data.pojo.shoppingcart.ShoppingCartItem;

import java.util.ArrayList;

import okhttp3.ResponseBody;

public interface BaseMVP {

    /* **************************** */
    /* Common Model layer interface */
    /* **************************** */

    interface Interactor {
        Double getAmountToPay(ArrayList<ShoppingCartItem> shoppingCart);

        double getTotalDiscount(ArrayList<ShoppingCartItem> shoppingCart);

        int getShoppingCartIndex(ArrayList<ShoppingCartItem> shoppingCart, String code);

        void loadImage(String imagePath, ImageView imageView);

        void loadImage(String imagePath, ImageView imageView, int drawableResId);

        Error getServerError(ResponseBody errorBody);

        Error getServerUnknownError();

        void saveUserId(String userId);

        String getUserId();

        void saveEmail(String email);

        String getEmail();

        void savePassword(String password);

        String getPassword();

        void saveToken(String token);

        String getToken();

        void setSkipTutorial();

        boolean getSkipTutorial();

        String getConditionsUrl();

        String getPrivacyUrl();

        String getHelpUrl();

        String getAccountKitUrl();

        String getFacebookConditionsUrl();

        String getFacebookPolicyUrl();

        String getFacebookCookiesUrl();

        void clearShoppingCart(Callback<Boolean> callback);

        void logout();

        void saveUserName(String name);

        String getUserName();

        void saveUserSurname(String surname);

        String getUserSurname();

        void saveUserPhone(String phone);

        String getUserPhone();
    }

    /* *************************** */
    /* Common View layer interface */
    /* *************************** */

    interface View {
        void setupToolbar(Toolbar toolbar, String title, boolean showBackArrow);

        void setupCollapsingToolbar(Toolbar toolbar,
                                    CollapsingToolbarLayout collapsingToolbarLayout, String title);

        void setupVerticalRecyclerView(RecyclerView mRecyclerView, BaseAdapter mAdapter);
    }

    interface Adapter<ViewType> {
        interface Listener<ViewType> {
            void onClick(ViewType view, int position);
        }

        void clearList();

        void addItems(ArrayList<ViewType> items, int position);

        void addItemsToEnd(ArrayList<ViewType> items);

        void addItem(ViewType item);

        void addItem(ViewType item, int layoutResId);

        void replaceList(ArrayList<ViewType> items);

        void replaceItem(int index, ViewType item);

        void removeItem(int position);

        int getLayoutResId();

        Context getContext();

        ViewType getItem(int i);
    }

    interface ViewHolder<ViewType> {
        android.view.View getView();

        void bind(Context context, final ViewType item, int position,
                  final Adapter.Listener<ViewType> listener);
    }

    /* ******************************** */
    /* Common Presenter layer interface */
    /* ******************************** */

    interface Presenter<ViewType> {
        void manageError(Error err);
    }

    interface Navigator {
        void loadActivity(Activity fromActivity, Activity toActivity);

        void loadActivity(Activity fromActivity, Activity toActivity, Bundle params);

        void loadActivityForResult(Activity fromActivity, Activity toActivity, int requestCode);

        void loadActivityForResult(Activity fromActivity, Activity toActivity, Bundle params,
                                   int requestCode);

        void loadFragment(FragmentManager fragmentManager, int containerResId, Fragment fragment);

        void loadTopFragment(FragmentManager fragmentManager, int containerResId, Fragment fragment);
    }
}
