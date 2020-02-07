package com.example.android.cabifymobilechallenge.common.view;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.android.cabifymobilechallenge.common.BaseMVP;
import com.example.android.cabifymobilechallenge.common.view.adapter.BaseAdapter;

public class BaseFragment extends Fragment implements BaseMVP.View {

    /**
     * Handle back press.
     *
     * @return true if back press was handled (i.e. if event is consumed), false otherwise.
     */
    public boolean onBackPressed() {
        // If fragment does not override this method, return default value false because the back
        // press event has not been handled.
        return false;
    }

    /* *************************** */
    /* BaseMVP.View implementation */
    /* *************************** */

    /**
     * Init custom toolbar and sets its title.
     *
     * @param title         is the string for the toolbar title.
     * @param showBackArrow is true if we want to show the back arrow in the toolbar, false
     *                      otherwise.
     */
    @Override
    public void setupToolbar(Toolbar toolbar, String title, boolean showBackArrow) {
        if (toolbar != null) {
            if (getActivity() != null) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
                appCompatActivity.setSupportActionBar(toolbar);
                if (appCompatActivity.getSupportActionBar() != null) {
                    appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(showBackArrow);
                    appCompatActivity.getSupportActionBar().setDisplayShowHomeEnabled(true);
                    appCompatActivity.getSupportActionBar().setTitle(title);
                }
            }
        }
    }

    /**
     * Set a custom Collapsing Toolbar into the fragment.
     *
     * @param toolbar                 is the custom {@link Toolbar}.
     * @param collapsingToolbarLayout is the {@link CollapsingToolbarLayout} to be set.
     * @param title                   is the string title for the Collapsing Toolbar.
     */
    @Override
    public void setupCollapsingToolbar(Toolbar toolbar,
                                       CollapsingToolbarLayout collapsingToolbarLayout,
                                       String title) {
        if (toolbar != null) {
            if (getActivity() != null) {
                AppCompatActivity appCompatActivity = (AppCompatActivity) getActivity();
                appCompatActivity.setSupportActionBar(toolbar);
                if (appCompatActivity.getSupportActionBar() != null) {
                    appCompatActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                }
                if (collapsingToolbarLayout != null) {
                    collapsingToolbarLayout.setTitle(title);
                }
            }
        }
    }

    /**
     * Create the RecyclerView and set its initial state.
     *
     * @param recyclerView is the {@link RecyclerView} to be configured.
     * @param adapter      is the {@link BaseAdapter} to be set to the RecyclerView.
     */
    @Override
    public void setupVerticalRecyclerView(RecyclerView recyclerView, BaseAdapter adapter) {
        // Set vertical layout.
        int orientation = LinearLayoutManager.VERTICAL;
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), orientation, false);
        recyclerView.setLayoutManager(layoutManager);

        // Define the separation between list items.
        if (getContext() != null) {
            DividerItemDecoration decoration =
                    new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
            recyclerView.addItemDecoration(decoration);
        }

        // Attach the adapter.
        recyclerView.setAdapter(adapter);
    }
}
