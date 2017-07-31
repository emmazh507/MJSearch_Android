package com.mjsearch.emma.mjsearch.ThreadList;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import com.mjsearch.emma.mjsearch.R;
import com.mjsearch.emma.mjsearch.base.BaseViewHolder;

class ThreadViewHolder extends BaseViewHolder {

    @BindView(R.id.thread_title) public TextView title;
    @BindView(R.id.thread_company) public TextView company;
    @BindView(R.id.thread_post_date) public TextView post_date;
    @BindView(R.id.thread_clickable_cover) public View cover;

    public ThreadViewHolder(View itemView) {
        super(itemView);
    }
}
