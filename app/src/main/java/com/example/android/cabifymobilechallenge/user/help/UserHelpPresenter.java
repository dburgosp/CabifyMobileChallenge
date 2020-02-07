package com.example.android.cabifymobilechallenge.user.help;

import android.content.Context;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.model.BaseInteractor;
import com.example.android.cabifymobilechallenge.common.presenter.BasePresenter;

public class UserHelpPresenter
        extends BasePresenter<UserHelpContract.View>
        implements UserHelpContract.Presenter {

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
    public UserHelpPresenter(Context context, UserHelpContract.View view) {
        super(context, view);
        mInteractor = new BaseInteractor(context);
    }

    /* *********************************************** */
    /* UserHelpContract.Presenter implementation */
    /* *********************************************** */

    /**
     * Tell view what to do for displaying its initial state.
     */
    @Override
    public void start() {
        mView.initToolbar(mContext.getString(R.string.user_help_title));
        mView.initWebView(mInteractor.getHelpUrl());
    }
}
