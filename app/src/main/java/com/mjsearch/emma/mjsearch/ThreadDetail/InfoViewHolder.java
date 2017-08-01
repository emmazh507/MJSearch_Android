package com.mjsearch.emma.mjsearch.ThreadDetail;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

//import com.facebook.drawee.view.SimpleDraweeView;
import com.mjsearch.emma.mjsearch.R;
import com.mjsearch.emma.mjsearch.base.BaseViewHolder;

import butterknife.BindView;

class InfoViewHolder extends BaseViewHolder {


    @BindView(R.id.thread_detail_title) TextView title;
    @BindView(R.id.thread_detail_content) TextView content;
    //@BindView(R.id.shot_author_picture) SimpleDraweeView authorPicture;
    @BindView(R.id.thread_detail_post_date) TextView post_date;
    @BindView(R.id.thread_detail_company) TextView company;
    @BindView(R.id.thread_action_share) TextView shareButton;

    public InfoViewHolder(View itemView) {
        super(itemView);
    }
}
