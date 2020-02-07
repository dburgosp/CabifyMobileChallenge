package com.example.android.cabifymobilechallenge.splash;

import android.content.Context;

import com.example.android.cabifymobilechallenge.common.model.BaseInteractor;
import com.example.android.cabifymobilechallenge.common.presenter.BasePresenter;

public class SplashPresenter
        extends BasePresenter<SplashContract.View>
        implements SplashContract.Presenter {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private SplashContract.View mView;
    private Context mContext;
    private BaseInteractor mInteractor;

    /**
     * Public constructor.
     *
     * @param context is the {@link Context} of the view that instantiates this class.
     * @param view    is the ViewType view that instantiates this class.
     */
    public SplashPresenter(Context context, SplashContract.View view) {
        super(context, view);

        mView = view;
        mContext = context;

        // Create interactor.
        mInteractor = new BaseInteractor(mContext);
    }

    /* *************************************** */
    /* SplashContract.Presenter implementation */
    /* *************************************** */

    /**
     * Tell View what to do when it starts.
     */
    @Override
    public void start() {
        mView.showIntro();
        mView.setupListeners();
        mView.waitForTimeout();
    }

    /**
     * Tell View where to navigate when the timeout has expired.
     */
    @Override
    public void onTimeoutExpired() {
        navigate();
    }

    /**
     * Tell View where to navigate when user has tapped the screen.
     */
    @Override
    public void onViewClicked() {
        navigate();
    }

    /* ********************** */
    /* Private helper methods */
    /* ********************** */

    /**
     * Private helper method for deciding where to navigate depending on data stored in Preferences.
     */
    private void navigate() {
        // Decide the next activity to go.
        if (mInteractor.getSkipTutorial()) {
            // If user has completed the tutorial before, next activity is StoreActivity.
            mView.navigateToStoreActivity();
        } else {
            // If user has not completed the tutorial before, next activity is TutorialActivity.
            mView.navigateToTutorialActivity();
        }
    }
}
