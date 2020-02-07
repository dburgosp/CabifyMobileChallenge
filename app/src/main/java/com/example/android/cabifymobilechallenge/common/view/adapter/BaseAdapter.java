package com.example.android.cabifymobilechallenge.common.view.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.cabifymobilechallenge.common.BaseMVP;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Public abstract class for
 *
 * @param <ViewType> is the data class of the objects
 */
public abstract class BaseAdapter<ViewType>
        extends RecyclerView.Adapter<BaseViewHolder<ViewType>>
        implements BaseMVP.Adapter<ViewType> {

    // Classes that extend BaseAdapter should declare all ViewHolder layout elements here.

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private ViewType mView;
    private ArrayList<ViewType> mItems;
    private int mLayoutResId, mLayoutResIdAux, mMaxItemsNumber;
    private Listener<ViewType> mListener;
    private LayoutInflater mInflater;
    private Context mContext;

    /* ************** */
    /* Public methods */
    /* ************** */

    /**
     * Constructor.
     *
     * @param context     is the Context of the calling activity or fragment.
     * @param numItems    is the maximum numbers of elements that mItems will hold.
     * @param listener    is the {@link Listener} to be added to every item in the
     *                    adapter.
     * @param layoutResId is the resource identifier of the layout for the {@link BaseViewHolder}
     *                    items.
     */
    public BaseAdapter(Context context, int numItems, Listener<ViewType> listener, int layoutResId) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mLayoutResId = layoutResId;
        mListener = listener;
        mMaxItemsNumber = numItems;

        // Save a backup of the main layout resource identifier.
        mLayoutResIdAux = mLayoutResId;

        // Initially create an empty list with the maximum number of items to be held, for infinite
        // scroll.
        clearList();
    }

    /* **************************************** */
    /* BaseMVP.Adapter<ViewType> implementation */
    /* **************************************** */

    /**
     * Create an empty list ready to hold the maximum number of items.
     */
    @Override
    public void clearList() {
        mItems = new ArrayList<>(mMaxItemsNumber);
        notifyDataSetChanged();
    }

    /**
     * Replaces a subset of the global list for a new sub-list.
     *
     * @param items    is the new list to be written into the global list.
     * @param position is the global list position from which the new list is going to be written.
     */
    @Override
    public void addItems(ArrayList<ViewType> items, int position) {
        for (int i = 0; i <= position; i++) {
            if ((mItems.size() - 1) < i) {
                // If there no space enough, create a new element.
                mItems.add(items.get(i));
            } else {
                // If there is space enough, overwrite element.
                mItems.set(i + position, items.get(i));
            }
        }
        notifyDataSetChanged();
    }

    /**
     * Add a list of items to the end of the current list.
     *
     * @param items is the new list to be written into the global list.
     */
    @Override
    public void addItemsToEnd(ArrayList<ViewType> items) {
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    /**
     * Add a new item to the end of the current list.
     *
     * @param item is the new object to be added to the global list.
     */
    @Override
    public void addItem(ViewType item) {
        mItems.add(item);
        notifyDataSetChanged();
    }

    /**
     * Add a new item to the end of the current list using a different layout resource identifier.
     *
     * @param item        is the new object to be added to the global list.
     * @param layoutResId is the new resource identifier of the layout for the
     *                    {@link BaseViewHolder} items.
     */
    @Override
    public void addItem(ViewType item, int layoutResId) {
        mLayoutResId = layoutResId;
        mItems.add(item);
        notifyDataSetChanged();
    }

    /**
     * Replaces the whole list for a new one.
     *
     * @param items is the new list to be stored in the adapter.
     */
    @Override
    public void replaceList(ArrayList<ViewType> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    /**
     * Replaces an item into the list.
     *
     * @param position is the list index to be updated.
     * @param item     is the object to be updated.
     */
    @Override
    public void replaceItem(int position, ViewType item) {
        if (mItems != null && mItems.size() > position) {
            mItems.set(position, item);
        }
        notifyDataSetChanged();
    }

    /**
     * Removes an item from the items list.
     *
     * @param position is the position of the item to be removed from the items list.
     */
    @Override
    public void removeItem(int position) {
        if (mItems != null && mItems.size() > position) {
            mItems.remove(position);
        }
        notifyDataSetChanged();
    }

    /**
     * @return current layout resource identifier.
     */
    @Override
    public int getLayoutResId() {
        return mLayoutResId;
    }

    /**
     * @return the context of the calling activity or fragment.
     */
    @Override
    public Context getContext() {
        return mContext;
    }

    /**
     * @param i is the position of the item into the global ArrayList<ViewType> of items.
     * @return the item at the position i.
     */
    @Override
    public ViewType getItem(int i) {
        return mItems.get(i);
    }

    /* ******************************************************************* */
    /* RecyclerView.Adapter<ClientHomeSearchViewHolder> overridden methods */
    /* ******************************************************************* */

    @SuppressWarnings("unchecked")
    @NonNull
    @Override
    public BaseViewHolder<ViewType> onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        // Inflate the view's layout using current layout resource identifier.
        View view = mInflater.inflate(mLayoutResId, viewGroup, false);

        // Restore the backup of the main layout resource identifier, just in case we had changed it
        // from a previous addItem(ViewType item, int layoutResId) call.
        mLayoutResId = mLayoutResIdAux;

        return new BaseViewHolder<>((ViewType) view);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<ViewType> viewHolder, int i) {
        Objects.requireNonNull(viewHolder).bind(mContext, mItems.get(i), i, mListener);

        // Classes that override BaseViewHolder<> should bind their item layout elements here like
        // this:
        //   1. Get item layout elements using viewHolder.getView().findViewById().
        //   2. Set values for layout elements. Values come from getItem(i).
        //   3. Optionally set listeners on specific layout elements.
    }

    @Override
    public int getItemCount() {
        return mItems != null ? mItems.size() : 0;
    }
}