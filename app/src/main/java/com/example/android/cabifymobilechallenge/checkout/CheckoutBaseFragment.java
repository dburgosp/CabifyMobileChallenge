package com.example.android.cabifymobilechallenge.checkout;

import android.content.res.Resources;
import android.widget.Button;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.view.BaseFragment;

/**
 * Common methods for Fragments in this package.
 */
public class CheckoutBaseFragment extends BaseFragment {

    /**
     * Enable bottom navigation button.
     *
     * @param button is the {@link Button} to be enabled.
     */
    public void enableNavigationButton(Button button) {
        if (getContext() != null) {
            Resources res = getContext().getResources();
            button.setBackgroundColor(res.getColor(R.color.colorAccent));
            button.setTextColor(res.getColor(android.R.color.white));
        }
        button.setAlpha(1.0f);
        button.setEnabled(true);
    }

    /**
     * Disable bottom navigation button.
     *
     * @param button is the {@link Button} to be disabled.
     */
    public void disableNavigationButton(Button button) {
        if (getContext() != null) {
            Resources res = getContext().getResources();
            button.setBackgroundColor(res.getColor(android.R.color.transparent));
            button.setTextColor(res.getColor(R.color.colorPrimaryDark));
        }
        button.setAlpha(0.5f);
        button.setEnabled(false);
    }
}
