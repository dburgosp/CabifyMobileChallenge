package com.example.android.cabifymobilechallenge.user;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.view.BaseFragment;

/**
 * Common methods for Fragments in this package.
 */
public class UserBaseFragment extends BaseFragment {

    /**
     * Show the "Delete" button into the EditText.
     *
     * @param editText      is the {@link EditText} to be modified.
     * @param drawableResId is the {@link Drawable} resource identifier of the delete button.
     */
    public void showDeleteInEditText(EditText editText, int drawableResId) {
        if (getContext() != null) {
            Drawable drawable = ContextCompat.getDrawable(getContext(), drawableResId);
            int tint = ContextCompat.getColor(getContext(), android.R.color.darker_gray);
            if (drawable != null) {
                drawable = DrawableCompat.wrap(drawable);
                DrawableCompat.setTint(drawable.mutate(), tint);
                editText.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        drawable, null);
            }
        }
    }

    /**
     * Hide the "Delete" button into the EditText.
     *
     * @param editText is the {@link EditText} to be modified.
     */
    public void hideDeleteInEditText(EditText editText) {
        if (getContext() != null) {
            editText.setCompoundDrawablesWithIntrinsicBounds(null, null, null,
                    null);
        }
    }

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
