package com.example.android.cabifymobilechallenge.common.presenter;

import android.content.Context;

import com.example.android.cabifymobilechallenge.common.BaseMVP;
import com.example.android.cabifymobilechallenge.data.pojo.Error;

public class BasePresenter<ViewType> implements BaseMVP.Presenter<ViewType> {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    public Context mContext;
    public ViewType mView;

    /**
     * Public constructor.
     *
     * @param context is the {@link Context} of the view that instantiates this class.
     * @param view    is the ViewType view that instantiates this class.
     */
    public BasePresenter(Context context, ViewType view) {
        mContext = context;
        mView = view;
    }

    /* ******************************** */
    /* BaseMVP.Presenter implementation */
    /* ******************************** */

    @Override
    public void manageError(Error err) {
        // TODO: perform actions after receiving an error from interactor, for example retrying API call.
    }
}
