package com.mjsearch.emma.mjsearch.ThreadDetail;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import com.mjsearch.emma.mjsearch.base.SingleFragmentActivity;

public class ThreadActivity extends SingleFragmentActivity {

    public static final String KEY_THREAD_TITLE = "thread_title";

    @NonNull
    @Override
    protected Fragment newFragment() {
        return ThreadFragment.newInstance(getIntent().getExtras());
    }

    @NonNull
    @Override
    protected String getActivityTitle() {
        return getIntent().getStringExtra(KEY_THREAD_TITLE);
    }
}
