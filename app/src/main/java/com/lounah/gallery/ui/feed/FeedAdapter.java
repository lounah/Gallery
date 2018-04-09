package com.lounah.gallery.ui.feed;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lounah.gallery.R;
import com.lounah.gallery.data.entity.Photo;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private final List<Photo> photos;
    private final PhotoOnClickListener clickListener;

    FeedAdapter(@NonNull final PhotoOnClickListener clickListener) {
        photos = new ArrayList<>();
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public FeedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View photoView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_photo, parent, false);
        return new ViewHolder(photoView);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedAdapter.ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(photos.get(position).getFileDownloadLink())
                .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
                .into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_photo)
        ImageView photo;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    void updateDataSet(@Nullable final List<Photo> photos) {
        if (photos != null) {
            this.photos.clear();
            this.photos.addAll(photos);
            notifyDataSetChanged();
        }
    }
}
