package com.example.android.cabifymobilechallenge.welcome;

import android.content.Context;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.presenter.BasePresenter;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;

class WelcomePresenter
        extends BasePresenter<WelcomeContract.View>
        implements WelcomeContract.Presenter {

    /* **************************** */
    /* BasePresenter implementation */
    /* **************************** */

    /**
     * Public constructor.
     *
     * @param context is the {@link Context} of the view that instantiates this class.
     * @param view    is the ViewType view that instantiates this class.
     */
    public WelcomePresenter(Context context, WelcomeContract.View view) {
        super(context, view);
    }

    /* **************************************** */
    /* WelcomeContract.Presenter implementation */
    /* **************************************** */

    /**
     * Tell view what to do for displaying its initial state.
     *
     * @param user is the {@link User} data of the new user.
     */
    @Override
    public void start(User user) {
        mView.setWelcomeText(mContext.getString(R.string.welcome_new_user, user.getName()));
        mView.setListeners();
    }

    /**
     * User has clicked on "Skip" button.
     */
    @Override
    public void skipClicked() {
        mView.closeActivity();
    }

    /**
     * User has clicked on "Add new address" button.
     */
    @Override
    public void newAddressClicked() {
        // TODO: add new address to user's profile.
        mView.closeActivity();
    }
}
