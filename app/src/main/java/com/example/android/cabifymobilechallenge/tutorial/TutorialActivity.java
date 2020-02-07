package com.example.android.cabifymobilechallenge.tutorial;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.android.cabifymobilechallenge.R;
import com.ramotion.paperonboarding.PaperOnboardingEngine;
import com.ramotion.paperonboarding.PaperOnboardingPage;

import com.example.android.cabifymobilechallenge.common.view.BaseActivity;
import com.example.android.cabifymobilechallenge.data.pojo.Tutorial;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TutorialActivity extends BaseActivity implements TutorialContract.View {

    /* *************** */
    /* Layout elements */
    /* *************** */

    @BindView(R.id.tutorial_skip)
    TextView mSkipTextView;

    @BindView(R.id.onboardingRootView)
    View mRootLayout;

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private TutorialPresenter mPresenter;
    private TutorialNavigator mNavigator;
    private PaperOnboardingEngine mPaperOnboardingEngine;

    /* ******************************* */
    /* BaseActivity overridden methods */
    /* ******************************* */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        ButterKnife.bind(this);

        // Create the presenter and the navigator for this view.
        mPresenter = new TutorialPresenter(this, this);
        mNavigator = new TutorialNavigator(this, this);

        // Initialize all elements in this Activity.
        mPresenter.start();
    }

    /* ************************************ */
    /* TutorialContract.View implementation */
    /* ************************************ */

    /**
     * Set listener for asking Presenter what to do when "Skip" button has been clicked.
     */
    @Override
    public void setupListeners() {
        mSkipTextView.setOnClickListener(view -> mPresenter.onSkipClicked());
    }

    /**
     * Show {@link Tutorial} data into the PaperOnboarding.
     */
    @Override
    public void showTutorial(Tutorial tutorial) {
        // Prepare data for the ViewPager.
        ArrayList<PaperOnboardingPage> elements = new ArrayList<>();
        for (int n = 0; n < tutorial.getTitlesArray().length; n++) {
            elements.add(new PaperOnboardingPage(
                    tutorial.getTitlesArray()[n],
                    tutorial.getDescriptionsArray()[n],
                    this.getResources().getColor(android.R.color.white),
                    tutorial.getImageViewArray().getResourceId(n, 0),
                    R.drawable.onboarding_pager_circle_icon));
        }

        // Pass data to TutorialViewPagerAdapter Class.
        mPaperOnboardingEngine = new PaperOnboardingEngine(mRootLayout, elements, this);

        // Set listeners for asking Presenter what to do when user has moved from one tutorial page
        // to another one or when user has swiped right from the last tutorial page.
        mPaperOnboardingEngine.setOnChangeListener((oldElementIndex, newElementIndex) ->
                mPresenter.onPageChanged(oldElementIndex, newElementIndex));
        mPaperOnboardingEngine.setOnRightOutListener(() -> mPresenter.onRightOut());
    }

    /**
     * Show "Finish" text in the Skip TextView.
     */
    @Override
    public void setSkipTextToFinish() {
        mSkipTextView.setText(R.string.finish);
    }

    /**
     * Show "Skip" text in the Skip TextView.
     */
    @Override
    public void setSkipTextToSkip() {
        mSkipTextView.setText(R.string.skip);
    }

    /**
     * Use Navigator for starting the Store Activity and closeActivity current one.
     */
    @Override
    public void navigateToStoreActivity() {
        mNavigator.startStoreActivity(this);
        finish();
    }
}
