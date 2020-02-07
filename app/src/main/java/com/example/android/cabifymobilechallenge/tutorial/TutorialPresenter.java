package com.example.android.cabifymobilechallenge.tutorial;

import android.content.Context;

import com.example.android.cabifymobilechallenge.common.presenter.BasePresenter;
import com.example.android.cabifymobilechallenge.data.CabifyTutorial;
import com.example.android.cabifymobilechallenge.data.pojo.Tutorial;

public class TutorialPresenter
        extends BasePresenter<TutorialContract.View>
        implements TutorialContract.Presenter {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private TutorialContract.View mView;
    private Context mContext;
    private TutorialInteractor mInteractor;

    /**
     * Public constructor.
     *
     * @param context is the {@link Context} of the view that instantiates this class.
     * @param view    is the ViewType view that instantiates this class.
     */
    public TutorialPresenter(Context context, TutorialContract.View view) {
        super(context, view);

        mView = view;
        mContext = context;

        // Create interactor.
        mInteractor = new TutorialInteractor(mContext);
    }

    /* ***************************************** */
    /* TutorialContract.Presenter implementation */
    /* ***************************************** */

    public void start() {
        // Ask View to setup listeners.
        mView.setupListeners();

        // Retrieve tutorial data from the Interactor and deliver it to the View.
        Tutorial tutorial = mInteractor.getTutorial();
        mView.showTutorial(tutorial);
    }

    /**
     * Tell view to navigate to the SmartCollection Activity when user has clicked on "Skip
     * tutorial".
     */
    @Override
    public void onSkipClicked() {
        mView.navigateToStoreActivity();
    }

    /**
     * Determine the string to be written in the Tutorial Skip TextView whenever a new page is
     * selected in the tutorial.
     *
     * @param oldElementIndex is the last visited page in the tutorial.
     * @param newElementIndex is the new page in the tutorial.
     */
    @Override
    public void onPageChanged(int oldElementIndex, int newElementIndex) {
        boolean endPage = newElementIndex == (CabifyTutorial.getSize() - 1);
        if (endPage) {
            // If current page is the last one, tell view to set text to "Finish".
            mView.setSkipTextToFinish();

            // If this is the last page, we have completed the tutorial. Save this information in
            // the preferences file, so that we won't see the tutorial again when the application is
            // restarted in the future.
            mInteractor.setSkipTutorial();
        } else {
            // If current page is not the last one, tell view to set text to "Skip".
            mView.setSkipTextToSkip();
        }

    }

    /**
     * Tell view to navigate to the SmartCollection Activity when user has swiped right from the
     * last tutorial page.
     */
    @Override
    public void onRightOut() {
        mView.navigateToStoreActivity();
    }
}
