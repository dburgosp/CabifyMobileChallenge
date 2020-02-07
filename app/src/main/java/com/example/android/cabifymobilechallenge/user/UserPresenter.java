package com.example.android.cabifymobilechallenge.user;

import android.content.Context;

import com.example.android.cabifymobilechallenge.common.presenter.BasePresenter;

public class UserPresenter
        extends BasePresenter<UserContract.View>
        implements UserContract.Presenter {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private UserInteractor mInteractor;

    /* **************************** */
    /* BasePresenter implementation */
    /* **************************** */

    /**
     * Public constructor.
     *
     * @param context is the {@link Context} of the view that instantiates this class.
     * @param view    is the ViewType view that instantiates this class.
     */
    public UserPresenter(Context context, UserContract.View view) {
        super(context, view);

        // Create interactor.
        mInteractor = new UserInteractor(mContext);
    }

    /* ************************************** */
    /* UserContract.Presenter implementation */
    /* ************************************** */

    /**
     * Tell view what to do for displaying its initial state.
     */
    @Override
    public void start() {
        mView.gotoLogin();
    }
}
