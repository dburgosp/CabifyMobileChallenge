package com.example.android.cabifymobilechallenge.user.conditions;

import android.content.Context;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.model.BaseInteractor;
import com.example.android.cabifymobilechallenge.common.presenter.BasePresenter;

public class UserConditionsPresenter
        extends BasePresenter<UserConditionsContract.View>
        implements UserConditionsContract.Presenter {

    private BaseInteractor mInteractor;

    /* **************************** */
    /* BasePresenter implementation */
    /* **************************** */

    /**
     * Public constructor.
     *
     * @param context is the {@link Context} of the view that instantiates this class.
     * @param view    is the ViewType view that instantiates this class.
     */
    public UserConditionsPresenter(Context context, UserConditionsContract.View view) {
        super(context, view);
        mInteractor = new BaseInteractor(context);
    }

    /* ***************************************** */
    /* UserHelpContract.Presenter implementation */
    /* ***************************************** */

    /**
     * Tell view what to do for displaying its initial state.
     *
     * @param scheme is the Scheme retrieved from the Activity calling intent data.
     */
    @Override
    public void start(String scheme) {
        if (scheme.equals("privacy")) {
            // Show Privacy Policy.
            mView.initToolbar(mContext.getString(R.string.user_privacy_title));
            mView.initWebView(mInteractor.getPrivacyUrl());
        } else if (scheme.equals("conditions")) {
            // Show General Use Conditions.
            mView.initToolbar(mContext.getString(R.string.user_conditions_title));
            mView.initWebView(mInteractor.getConditionsUrl());
        } else if (scheme.equals("account_kit")) {
            // Show Account Kit Conditions.
            mView.initToolbar(mContext.getString(R.string.account_kit_title));
            mView.initWebView(mInteractor.getAccountKitUrl());
        } else if (scheme.equals("fb_conditions")) {
            // Show Facebook Conditions.
            mView.initToolbar(mContext.getString(R.string.facebook_conditions));
            mView.initWebView(mInteractor.getFacebookConditionsUrl());
        } else if (scheme.equals("fb_privacy")) {
            // Show Facebook Data Policy.
            mView.initToolbar(mContext.getString(R.string.facebook_policy));
            mView.initWebView(mInteractor.getFacebookPolicyUrl());
        } else if (scheme.equals("fb_cookies")) {
            // Show Facebook Cookies.
            mView.initToolbar(mContext.getString(R.string.facebook_cookies));
            mView.initWebView(mInteractor.getFacebookCookiesUrl());
        }
    }
}
