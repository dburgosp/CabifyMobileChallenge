package com.example.android.cabifymobilechallenge.splash;

import android.os.Bundle;
import android.os.Handler;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.view.BaseActivity;
import com.example.android.cabifymobilechallenge.utils.MyAnimationUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity implements SplashContract.View {

    private final int DELAY_DURATION = 4000;
    private final int ANIM_DURATION = 1000;

    /* *************** */
    /* Layout elements */
    /* *************** */

    @BindView(R.id.splash_image)
    TextView mIcon;

    @BindView(R.id.splash_layout)
    RelativeLayout mLayout;

    @BindView(R.id.splash_carbon_neutral)
    RelativeLayout mCarbonNeutral;

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private SplashPresenter mPresenter;
    private SplashNavigator mNavigator;
    private Handler mHandler;
    private Runnable mRunnable;

    /* ******************************* */
    /* BaseActivity overridden methods */
    /* ******************************* */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        // Create the presenter and the navigator for this view.
        mPresenter = new SplashPresenter(this, this);
        mNavigator = new SplashNavigator(this, this);

        // Initialize all elements in this Activity.
        mPresenter.start();
    }

    /* ********************************** */
    /* SplashContract.View implementation */
    /* ********************************** */

    /**
     * Display image and layout with animation.
     */
    public void showIntro() {
        MyAnimationUtils myAnimationUtils = new MyAnimationUtils(this);
        myAnimationUtils.add(mIcon, R.anim.scale_up);
        myAnimationUtils.add(mCarbonNeutral, R.anim.in_from_bottom);
        myAnimationUtils.animate(1000);
        //myAnimationUtils.scaleUp(mIcon, ANIM_DURATION);
        //myAnimationUtils.in_from_bottom(mCarbonNeutral);
    }

    /**
     * Set a listener for receiving click events on the whole view layout.
     */
    public void setupListeners() {
        mLayout.setOnClickListener(view -> {
            // Remove any pending posts of Runnable mRunnable that are in the message queue.
            if (mHandler != null) {
                mHandler.removeCallbacks(mRunnable);
            }

            // Tell Presenter that an onClick event has been received.
            mPresenter.onViewClicked();
        });
    }

    /**
     * Ask Presenter what to do after DELAY_DURATION milliseconds.
     */
    @Override
    public void waitForTimeout() {
        mHandler = new Handler();
        mRunnable = () -> mPresenter.onTimeoutExpired();
        mHandler.postDelayed(mRunnable, DELAY_DURATION);
    }

    /**
     * Use Navigator for starting the Tutorial Activity and closeActivity current one.
     */
    @Override
    public void navigateToTutorialActivity() {
        mNavigator.startTutorialActivity(this);
        finish();
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
