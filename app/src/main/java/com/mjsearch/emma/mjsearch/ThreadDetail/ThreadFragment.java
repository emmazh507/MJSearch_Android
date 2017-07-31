package com.mjsearch.emma.mjsearch.ThreadDetail;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.reflect.TypeToken;
import com.mjsearch.emma.mjsearch.R;
import com.mjsearch.emma.mjsearch.models.mjThread;
import com.mjsearch.emma.mjsearch.utils.ModelUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThreadFragment extends Fragment {

    public static final String KEY_SHOT = "thread";

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    public static ThreadFragment newInstance(@NonNull Bundle args) {
        ThreadFragment fragment = new ThreadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        mjThread thread = ModelUtils.toObject(getArguments().getString(KEY_SHOT),
                                        new TypeToken<mjThread>(){});
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new ThreadAdapter(thread));
    }
}
