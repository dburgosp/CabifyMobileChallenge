package com.example.android.cabifymobilechallenge.user.help;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.view.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserHelpActivity extends BaseActivity
        implements UserHelpContract.View {

    /* *************** */
    /* Layout elements */
    /* *************** */

    @BindView(R.id.user_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.user_webview)
    WebView mWebView;

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private String mScheme;
    private UserHelpPresenter mPresenter;

    /* ******************************* */
    /* BaseActivity overridden methods */
    /* ******************************* */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_help);
        ButterKnife.bind(this);

        // Create the presenter for this view.
        mPresenter = new UserHelpPresenter(this, this);

        // Ask presenter to initialize all elements in this Activity.
        mPresenter.start();
    }

    /* ******************************** */
    /* UserContract.View implementation */
    /* ******************************** */

    /**
     * Initializes Toolbar setting a title and enabling the back arrow.
     *
     * @param title is the string title to be shown in the Toolbar.
     */
    @Override
    public void initToolbar(String title) {
        setupToolbar(mToolbar, title, true);
    }

    /**
     * Load a given url into the WebView.
     *
     * @param url is the string url to be loaded.
     */
    @Override
    public void initWebView(String url) {
        mWebView.loadUrl(url);
    }
}
