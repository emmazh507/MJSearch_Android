package com.mjsearch.emma.mjsearch;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mjsearch.emma.mjsearch.ThreadList.ThreadListFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by emmazhuang on 7/18/17.
 */

public class ListActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    public static final String KEY_KEYWORD = "keyword";

    public String data;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        if (isBackEnabled()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        setTitle(getActivityTitle());


        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, ThreadListFragment.newInstance())
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (isBackEnabled() && item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected boolean isBackEnabled() {
        return true;
    }

    @NonNull
    protected String getActivityTitle() {
        return "";
    }
}
