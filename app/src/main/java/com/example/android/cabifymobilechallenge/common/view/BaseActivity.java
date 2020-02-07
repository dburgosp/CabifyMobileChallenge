package com.example.android.cabifymobilechallenge.common.view;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.android.cabifymobilechallenge.common.BaseMVP;
import com.example.android.cabifymobilechallenge.common.view.adapter.BaseAdapter;

public class BaseActivity extends AppCompatActivity implements BaseMVP.View {

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
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(showBackArrow);
                getSupportActionBar().setDisplayShowHomeEnabled(true);
                getSupportActionBar().setTitle(title);
            }
        }
    }

    /**
     * Set a custom Collapsing Toolbar into the View.
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
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
            if (collapsingToolbarLayout != null) {
                collapsingToolbarLayout.setTitle(title);
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
                new LinearLayoutManager(this, orientation, false);
        recyclerView.setLayoutManager(layoutManager);
        //recyclerView.setNestedScrollingEnabled(false);

        // Define the separation between list items.
        DividerItemDecoration decoration =
                new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decoration);

        // Attach the adapter.
        recyclerView.setAdapter(adapter);
    }

    /* ************************************ */
    /* AppCompatActivity overridden methods */
    /* ************************************ */

    @Override
    public boolean onSupportNavigateUp() {
        // By default, the Up/Back button doesn’t go back to the previous activity, but launches the
        // parent instead. We override onSupportNavigateUp to call onBackPressed.
        onBackPressed();
        return true;
    }
}
