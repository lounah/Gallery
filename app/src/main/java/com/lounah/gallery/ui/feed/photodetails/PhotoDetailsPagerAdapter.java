package com.lounah.gallery.ui.feed.photodetails;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
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

public class PhotoDetailsPagerAdapter extends PagerAdapter {

    @BindView(R.id.iv_photo_detail)
    ImageView photo;

    private final List<Photo> photos;

    PhotoDetailsPagerAdapter() {
        photos = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return photos.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        final LayoutInflater mLayoutInflater = (LayoutInflater) container.getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        final View itemView = mLayoutInflater
                .inflate(R.layout.item_pager_photo, container, false);

        ButterKnife.bind(this, itemView);

        Glide.with(itemView.getContext())
                .load(photos.get(position).getFileDownloadLink())
                .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
                .into(photo);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    void updateDataSet(@Nullable final List<Photo> photos) {
        if (photos != null) {
            this.photos.clear();
            this.photos.addAll(photos);
            notifyDataSetChanged();
        }
    }
}
