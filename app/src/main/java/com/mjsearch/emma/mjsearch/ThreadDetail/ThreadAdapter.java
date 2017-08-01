package com.mjsearch.emma.mjsearch.ThreadDetail;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.text.method.ScrollingMovementMethod;

import com.mjsearch.emma.mjsearch.R;
import com.mjsearch.emma.mjsearch.models.mjThread;

// ShotAdapter is used to display a Shot object as items in RecyclerView
class ThreadAdapter extends RecyclerView.Adapter {

    private static final int VIEW_TYPE_THREAD_IMAGE = 0;
    private static final int VIEW_TYPE_THREAD_INFO = 1;

    private final mjThread thread;

    public ThreadAdapter(@NonNull mjThread thread) {
        this.thread = thread;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        //switch (viewType) {
            //case VIEW_TYPE_THREAD_IMAGE:
            //    view = LayoutInflater.from(parent.getContext())
            //                         .inflate(R.layout.thread_item_image, parent, false);
            //    return new ImageViewHolder(view);
            //case VIEW_TYPE_THREAD_INFO:
                view = LayoutInflater.from(parent.getContext())
                                     .inflate(R.layout.thread_item_info, parent, false);
                return new InfoViewHolder(view);
            //default:
                //return null;
        //}
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int viewType = VIEW_TYPE_THREAD_INFO;

        //final int viewType = getItemViewType(position);
        //  switch (viewType) {
         /*   case VIEW_TYPE_THREAD_IMAGE:
                // play gif automatically
                DraweeController controller = Fresco.newDraweeControllerBuilder()
                                                    .setUri(Uri.parse(shot.getImageUrl()))
                                                    .setAutoPlayAnimations(true)
                                                    .build();
                ((ImageViewHolder) holder).image.setController(controller);
                break;*/
            //case VIEW_TYPE_THREAD_INFO:
                InfoViewHolder threadDetailViewHolder = (InfoViewHolder) holder;
                threadDetailViewHolder.title.setText(thread.thread_title);
                threadDetailViewHolder.company.setText(thread.company);
                threadDetailViewHolder.post_date.setText(thread.post_date);
                threadDetailViewHolder.content.setText(thread.article);
                //threadDetailViewHolder.content.setMovementMethod(new ScrollingMovementMethod());


        threadDetailViewHolder.shareButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        share(v.getContext());
                    }
                });
               // break;
        //}
    }

    @Override
    public int getItemCount() {
        //return 2;
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_TYPE_THREAD_IMAGE;
        } else {
            return VIEW_TYPE_THREAD_INFO;
        }
    }

    private void share(Context context) {
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, thread.thread_title + " " + thread.html_url);
        shareIntent.setType("text/plain");
        context.startActivity(Intent.createChooser(shareIntent,
                                                   context.getString(R.string.share_thread)));
    }
}
