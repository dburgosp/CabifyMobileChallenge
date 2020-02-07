package com.example.android.cabifymobilechallenge.common.view.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.android.cabifymobilechallenge.common.BaseMVP;
import com.example.android.cabifymobilechallenge.common.BaseMVP.Adapter.Listener;

public class BaseViewHolder<ViewType>
        extends RecyclerView.ViewHolder
        implements BaseMVP.ViewHolder<ViewType> {

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private ViewType mViewHolder;

    /* *********** */
    /* Constructor */
    /* *********** */

    /**
     * @param itemView is the View that contains the layout elements of every list item.
     */
    BaseViewHolder(ViewType itemView) {
        super((View) itemView);
        mViewHolder = itemView;
    }

    /* ************** */
    /* Public methods */
    /* ************** */


    @Override
    public View getView() {
        return itemView;
    }

    /**
     * Bind an item and a {@link Listener} to the current list item of the ViewHolder.
     *
     * @param context  is the Context of the calling activity.
     * @param item     is the object containing the current list item.
     * @param position is the position of the current into the list item.
     * @param listener is the callback method for receiving click events on the current list item.
     */
    @Override
    public void bind(Context context, ViewType item, int position, Listener<ViewType> listener) {
        if (listener != null) {
            // Set listener for click events on current item.
            ((View) mViewHolder).setOnClickListener(v -> listener.onClick(item, position));
        }
    }
}
