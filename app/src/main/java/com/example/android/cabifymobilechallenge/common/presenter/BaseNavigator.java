package com.example.android.cabifymobilechallenge.common.presenter;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.transition.TransitionInflater;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.BaseMVP;

public class BaseNavigator<ViewType> implements BaseMVP.Navigator {

    /* *********************** */
    /* Public member variables */
    /* *********************** */

    public ViewType mView;
    public Context mContext;

    /* ******************* */
    /* Public constructors */
    /* ******************* */

    public BaseNavigator(Context context, ViewType view) {
        mContext = context;
        mView = view;
    }

    /* ******************************** */
    /* BaseMVP.Navigator implementation */
    /* ******************************** */

    /**
     * Load a new activity.
     *
     * @param fromActivity is the {@link Activity} that is trying to load the new one.
     * @param toActivity   is the new {@link Activity} to be started.
     */
    @Override
    public void loadActivity(Activity fromActivity, Activity toActivity) {
        Intent intent = new Intent(fromActivity, toActivity.getClass());
        fromActivity.startActivity(intent);
    }

    /**
     * Load a new activity using parameters.
     *
     * @param fromActivity is the {@link Activity} that is trying to load the new one.
     * @param toActivity   is the new {@link Activity} to be started.
     * @param params       are the {@link Bundle} parameters to be passed to the new activity.
     */
    @Override
    public void loadActivity(Activity fromActivity, Activity toActivity, Bundle params) {
        Intent intent = new Intent(fromActivity, toActivity.getClass());
        intent.putExtras(params);
        fromActivity.startActivity(intent);
    }

    /**
     * Load a new activity using parameters.
     *
     * @param fromActivity is the {@link Activity} that is trying to load the new one.
     * @param toActivity   is the new {@link Activity} to be started.
     * @param requestCode  is the request code for identifying the result from the calling activity.
     */
    @Override
    public void loadActivityForResult(Activity fromActivity, Activity toActivity, int requestCode) {
        Intent intent = new Intent(fromActivity, toActivity.getClass());
        fromActivity.startActivityForResult(intent, requestCode);
    }

    /**
     * Load a new activity using parameters.
     *
     * @param fromActivity is the {@link Activity} that is trying to load the new one.
     * @param toActivity   is the new {@link Activity} to be started.
     * @param params       are the {@link Bundle} parameters to be passed to the new activity.
     * @param requestCode  is the request code for identifying the result from the calling activity.
     */
    @Override
    public void loadActivityForResult(Activity fromActivity, Activity toActivity, Bundle params,
                                      int requestCode) {
        Intent intent = new Intent(fromActivity, toActivity.getClass());
        intent.putExtras(params);
        fromActivity.startActivityForResult(intent, requestCode);
    }

    /**
     * Load a fragment into a container. This fragment must be added to the back stack.
     *
     * @param fragmentManager is the {@link FragmentManager} used to load the new fragment.
     * @param containerResId  is the resource identifier of the layout container in which the
     *                        fragment is going to be loaded.
     * @param fragment        is the {@link Fragment} to be loaded.
     */
    @Override
    public void loadFragment(FragmentManager fragmentManager, int containerResId,
                             Fragment fragment) {
        setFragmentTransitions(fragment);
        fragmentManager.beginTransaction()
                .replace(containerResId, fragment)
                .addToBackStack(null)
                .commit();
    }

    /**
     * Load a fragment into a container. This fragment must not be added to the back stack.
     *
     * @param fragmentManager is the {@link FragmentManager} used to load the new fragment.
     * @param containerResId  is the resource identifier of the layout container in which the
     *                        fragment is going to be loaded.
     * @param fragment        is the {@link Fragment} to be loaded.
     */
    @Override
    public void loadTopFragment(FragmentManager fragmentManager, int containerResId,
                                Fragment fragment) {
        setFragmentTransitions(fragment);
        fragmentManager.beginTransaction()
                .replace(containerResId, fragment)
                .commit();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        mContext = null;
        mView = null;
    }

    /* ********************** */
    /* Private helper methods */
    /* ********************** */

    /**
     * Define default transitions for entering and exiting Fragments.
     *
     * @param fragment is the {@link Fragment} in which transitions are applied.
     */
    private void setFragmentTransitions(Fragment fragment) {
        // Transitions for entering and exiting fragments.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            fragment.setEnterTransition(TransitionInflater.from(mContext).inflateTransition(
                    R.transition.enter_transition));
            fragment.setExitTransition(TransitionInflater.from(mContext).inflateTransition(
                    R.transition.exit_transition));
        }
    }
}
