package com.example.android.cabifymobilechallenge.store.list;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.view.BaseFragment;
import com.example.android.cabifymobilechallenge.data.pojo.products.Product;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StoreListFragment
        extends BaseFragment
        implements StoreListContract.View {

    /* *************** */
    /* Layout elements */
    /* *************** */

    @BindView(R.id.store_list)
    RecyclerView mRecyclerView;

    /* ******************************* */
    /* Communication with the Activity */
    /* ******************************* */

    public interface OnInteractionListener {
        void onProductClicked(Product item);

        void onAddToCartClicked(String productCode);
    }

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private StoreListPresenter mPresenter;
    private StoreListAdapter mAdapter;
    private ArrayList<Product> mProducts;
    private View mRootView;
    private OnInteractionListener mListener;

    /* ************************************* */
    /* BaseFragment class overridden methods */
    /* ************************************* */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get arguments previously added in the newInstance method.
        if (getArguments() != null) {
            mProducts = getArguments().getParcelableArrayList(StoreListContract.PARAM_PRODUCTS);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment.
        mRootView = inflater.inflate(
                R.layout.fragment_products_list, container, false);
        ButterKnife.bind(this, mRootView);

        // Create the presenter for this view.
        mPresenter = new StoreListPresenter(getContext(), this);

        // Ask presenter to initialize all elements in this Fragment.
        mPresenter.start();

        return mRootView;
    }

    /* ************** */
    /* Public methods */
    /* ************** */

    /**
     * Required empty public constructor.
     */
    public StoreListFragment() {
    }

    /**
     * Factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param products is the list of {@link Product} objects passed as a parameter to this method.
     * @return a new instance of fragment StoreListFragment.
     */
    public static StoreListFragment newInstance(ArrayList<Product> products) {
        StoreListFragment fragment = new StoreListFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(StoreListContract.PARAM_PRODUCTS, products);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * @param listener is the Activity that is listening for user interactions on this fragment.
     */
    public void attachListener(OnInteractionListener listener) {
        mListener = listener;
    }

    /* ************************************* */
    /* StoreListContract.View implementation */
    /* ************************************* */

    /**
     * Initialize the RecyclerView.
     */
    @Override
    public void setupItemsList() {
        // Define listener for managing click events on every item into the list.
        StoreListAdapter.ProductInteractionListener listener =
                new StoreListAdapter.ProductInteractionListener() {
                    @Override
                    public void onProductClicked(ImageView imageView, Product item) {
                        mListener.onProductClicked(item);
                    }

                    @Override
                    public void onAddToCartClicked(Product item) {
                        mListener.onAddToCartClicked(item.getCode());
                    }
                };

        // Create adapter.
        mAdapter = new StoreListAdapter(getContext(), 0, listener, R.layout.item_store);

        // Create the RecyclerView and set its initial state.
        if (getActivity() != null) {
            setupVerticalRecyclerView(mRecyclerView, mAdapter);
        }
    }

    /**
     * Called by the Presenter for asking View to display the {@link Product} objects list.
     */
    @Override
    public void loadProducts() {
        mAdapter.replaceList(mProducts);
    }
}
