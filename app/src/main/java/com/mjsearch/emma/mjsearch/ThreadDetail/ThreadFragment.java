package com.mjsearch.emma.mjsearch.ThreadDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.mjsearch.emma.mjsearch.R;
import com.mjsearch.emma.mjsearch.models.mjThread;
import com.mjsearch.emma.mjsearch.utils.ModelUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThreadFragment extends Fragment {

    public static final String KEY_THREAD = "mjThread";
    private mjThread data;

    @BindView(R.id.thread_detail_title) TextView title;
    @BindView(R.id.thread_detail_content) TextView content;
    //@BindView(R.id.shot_author_picture) SimpleDraweeView authorPicture;
    @BindView(R.id.thread_detail_post_date) TextView post_date;
    @BindView(R.id.thread_detail_company) TextView company;
    @BindView(R.id.thread_action_share) TextView shareButton;

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
        View view = inflater.inflate(R.layout.thread_item_info, container, false);
        ButterKnife.bind(this, view);
        //view.setNestedScrollingEnabled(false);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        mjThread thread = ModelUtils.toObject(getArguments().getString(KEY_THREAD),
                                        new TypeToken<mjThread>(){});
        this.data = thread;
        title.setText(data.thread_title);
        company.setText(data.company);
        post_date.setText(data.post_date);
        content.setText(data.article);

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share(v.getContext());
            }
        });
    }

    private void share(Context context) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, data.thread_title + " " + data.html_url);

        shareIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(shareIntent,
                context.getString(R.string.share_thread)));
    }
}
