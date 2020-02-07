package com.example.android.cabifymobilechallenge.store.list;

import android.content.Context;

import com.example.android.cabifymobilechallenge.common.presenter.BasePresenter;
import com.example.android.cabifymobilechallenge.store.StoreInteractor;

public class StoreListPresenter
        extends BasePresenter<StoreListContract.View>
        implements StoreListContract.Presenter {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private StoreListContract.View mView;
    private Context mContext;
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
    public StoreListPresenter(Context context, StoreListContract.View view) {
        super(context, view);

        mView = view;
        mContext = context;

        // Create interactor.
        mInteractor = new StoreInteractor(mContext);
    }

    /* ****************************************** */
    /* StoreListContract.Presenter implementation */
    /* ****************************************** */

    /**
     * Tell view what to do for displaying its initial state.
     */
    @Override
    public void start() {
        mView.setupItemsList();
        mView.loadProducts();
    }
}
