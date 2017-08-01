package com.mjsearch.emma.mjsearch.ThreadList;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.gson.reflect.TypeToken;
import com.mjsearch.emma.mjsearch.utils.ModelUtils;
import com.mjsearch.emma.mjsearch.R;
import com.mjsearch.emma.mjsearch.models.mjThread;
import com.mjsearch.emma.mjsearch.ThreadDetail.*;

///mport com.mjsearch.emma.mjsearch.thread_detail.ThreadActivity;
//import com.mjsearch.emma.mjsearch.thread_detail.ThreadFragment;

import java.util.List;

class ThreadListAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_THREAD = 1;
    private static final int VIEW_TYPE_LOADING = 2;

    private List<mjThread> data;
    private LoadMoreListener loadMoreListener;
    private boolean showLoading;

    public ThreadListAdapter(@NonNull List<mjThread> data, @NonNull LoadMoreListener loadMoreListener) {
        this.data = data;
        this.loadMoreListener = loadMoreListener;
        this.showLoading = true;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_THREAD) {
            View view = LayoutInflater.from(parent.getContext())
                                      .inflate(R.layout.list_item_thread, parent, false);
            return new ThreadViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                                      .inflate(R.layout.list_item_loading, parent, false);
            return new RecyclerView.ViewHolder(view) {};
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        final int viewType = getItemViewType(position);
        if (viewType == VIEW_TYPE_LOADING) {
            loadMoreListener.onLoadMore();
        } else {
            final mjThread thread = data.get(position);

            ThreadViewHolder threadViewHolder = (ThreadViewHolder) holder;
            threadViewHolder.title.setText((position+1)+String.valueOf(thread.thread_title));
            threadViewHolder.company.setText(String.valueOf(thread.company));
            threadViewHolder.post_date.setText(String.valueOf(thread.post_date));


            threadViewHolder.cover.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = holder.itemView.getContext();
                    Intent intent = new Intent(context, ThreadActivity.class);
                    intent.putExtra(ThreadFragment.KEY_THREAD,
                            ModelUtils.toString(thread, new TypeToken<mjThread>(){}));
                    intent.putExtra(ThreadActivity.KEY_THREAD_TITLE, thread.thread_title);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return showLoading ? data.size() + 1 : data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position < data.size()
                ? VIEW_TYPE_THREAD
                : VIEW_TYPE_LOADING;
    }

    public void append(@NonNull List<mjThread> moreShots) {
        data.addAll(moreShots);
        notifyDataSetChanged();
    }

    public int getDataCount() {
        return data.size();
    }

    public void setShowLoading(boolean showLoading) {
        this.showLoading = showLoading;
        notifyDataSetChanged();
    }

    public interface LoadMoreListener {
        void onLoadMore();
    }
}
