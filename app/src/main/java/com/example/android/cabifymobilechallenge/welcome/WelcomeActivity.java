package com.example.android.cabifymobilechallenge.welcome;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cabifymobilechallenge.R;
import com.example.android.cabifymobilechallenge.common.view.BaseActivity;
import com.example.android.cabifymobilechallenge.data.pojo.user.User;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.android.cabifymobilechallenge.welcome.WelcomeContract.PARAM_USER;

public class WelcomeActivity extends BaseActivity implements WelcomeContract.View {

    /* *************** */
    /* Layout elements */
    /* *************** */

    @BindView(R.id.user_register_title)
    TextView mWelcomeTextView;

    @BindView(R.id.user_select_address_skip)
    TextView mSkipTextView;

    @BindView(R.id.user_select_address)
    Button mAddressButton;

    /* ************************ */
    /* Private member variables */
    /* ************************ */

    private WelcomePresenter mPresenter;
    private WelcomeNavigator mNavigator;
    private User mUser;

    /* ******************************* */
    /* BaseActivity overridden methods */
    /* ******************************* */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);

        // Get parameters from calling Intent.
        if (getIntent() != null && getIntent().getExtras() != null) {
            mUser = getIntent().getExtras().getParcelable(PARAM_USER);
        }

        // Create the presenter and the navigator for this view.
        mPresenter = new WelcomePresenter(this, this);
        mNavigator = new WelcomeNavigator(this, this);

        // Ask presenter to initialize all elements in this Activity.
        mPresenter.start(mUser);
    }

    /* *********************************** */
    /* WelcomeContract.View implementation */
    /* *********************************** */

    /**
     * Display a text into the Welcome TextView.
     *
     * @param text is the text to be displayed.
     */
    @Override
    public void setWelcomeText(String text) {
        mWelcomeTextView.setText(text);
    }

    /**
     * Setup all required listeners on the clickable elements.
     */
    @Override
    public void setListeners() {
        mSkipTextView.setOnClickListener(v -> mPresenter.skipClicked());
        mAddressButton.setOnClickListener(v -> {
            mPresenter.newAddressClicked();

            // TODO: pendiente.
            Toast.makeText(this, "Gestión de direcciones pendiente de implementación",
                    Toast.LENGTH_SHORT).show();
        });
    }

    /**
     * Close this activity.
     */
    @Override
    public void closeActivity() {
        finish();
    }
}
