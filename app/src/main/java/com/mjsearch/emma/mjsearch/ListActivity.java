package com.mjsearch.emma.mjsearch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
//import com.mjsearch.emma.mjsearch.ThreadList.ThreadListFragment;

/**
 * Created by emmazhuang on 7/18/17.
 */

public class ListActivity extends AppCompatActivity {
    public static final String KEY_KEYWORD = "keyword";

    public String data;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    //       .add(R.id.fragment_container, ThreadListFragment.newInstance())
                    .commit();
        }
    }

}
