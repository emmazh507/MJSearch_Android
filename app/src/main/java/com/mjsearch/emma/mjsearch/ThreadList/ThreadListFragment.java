package com.mjsearch.emma.mjsearch.ThreadList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.google.gson.JsonSyntaxException;
import com.mjsearch.emma.mjsearch.R;
import com.mjsearch.emma.mjsearch.es.ESClient;
import com.mjsearch.emma.mjsearch.models.mjThread;
import com.mjsearch.emma.mjsearch.base.SpaceItemDecoration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ThreadListFragment extends Fragment {

    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private ThreadListAdapter adapter;

    public static ThreadListFragment newInstance() {
        return new ThreadListFragment();
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new SpaceItemDecoration(
                getResources().getDimensionPixelSize(R.dimen.spacing_medium)));

        adapter = new ThreadListAdapter(new ArrayList<mjThread>(), new ThreadListAdapter.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                // this method will be called when the RecyclerView is displayed
                // page starts from 1
                //should execute in anohter thread other than UI thread
                AsyncTaskCompat.executeParallel(
                        new LoadThreadTask(adapter.getDataCount() / ESClient.COUNT_PER_PAGE + 1));
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private class LoadThreadTask extends AsyncTask<Void, Void, List<mjThread>> {

        int page;

        public LoadThreadTask(int page) {
            this.page = page;
        }

        @Override
        protected List<mjThread> doInBackground(Void... params) {
            // this method is executed on non-UI thread
            try {
                List<mjThread> test = ESClient.post(getActivity(), page);
                return ESClient.post(getActivity(), page);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<mjThread> threads) {
            // this method is executed on UI thread!!!!
            if (threads != null) {
                adapter.append(threads);
                adapter.setShowLoading(threads.size() == ESClient.COUNT_PER_PAGE);
            } else {
                //Snackbar.make(getView(), "Error!", Snackbar.LENGTH_LONG).show();
            }
        }
    }

    //Data在fragment(adapter传递进loading more信号)中产生
//    private List<Thread> fakeData() {
//        List<Thread> threadList = new ArrayList<>();
//        Random random = new Random();
//        for (int i = 0; i < 20; ++i) {
//            Thread thread = new Thread();
//            thread.title = "OA 题目+面试3" + i;
//            thread.company = "facebook";
//            thread.post_date = "2017-08-08";
//            threadList.add(thread);
//        }
//        return threadList;
//    }

}
