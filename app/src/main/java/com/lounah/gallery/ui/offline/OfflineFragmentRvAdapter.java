package com.lounah.gallery.ui.offline;

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
import com.lounah.gallery.ui.feed.PhotoOnClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OfflineFragmentRvAdapter extends RecyclerView.Adapter<OfflineFragmentRvAdapter.ViewHolder> {

    private final List<String> paths;
    private final OnClickListener clickListener;

    OfflineFragmentRvAdapter(@NonNull final OnClickListener clickListener) {
        paths = new ArrayList<>();
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public OfflineFragmentRvAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View photoView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_saved_photo, parent, false);
        return new OfflineFragmentRvAdapter.ViewHolder(photoView);
    }

    @Override
    public void onBindViewHolder(@NonNull OfflineFragmentRvAdapter.ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(paths.get(position))
                .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
                .into(holder.photo);
    }

    @Override
    public int getItemCount() {
        return paths.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_photo)
        ImageView photo;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.iv_photo)
        void onPhotoClicked() {
            clickListener.onClick(paths.get(getAdapterPosition()));
        }

    }

    void updateDataSet(@Nullable final List<String> paths) {
        if (paths != null) {
            this.paths.clear();
            this.paths.addAll(paths);
            notifyDataSetChanged();
        }
    }
}
