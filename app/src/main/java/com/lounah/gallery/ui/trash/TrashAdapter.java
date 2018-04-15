package com.lounah.gallery.ui.trash;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lounah.gallery.R;
import com.lounah.gallery.data.entity.Trash;
import com.lounah.gallery.util.TrashDiffUtilCallback;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;

public class TrashAdapter extends RecyclerView.Adapter<TrashAdapter.ViewHolder> {

    private final List<Trash> photos;
    private final ItemClickListener clickListener;

    TrashAdapter(@NonNull final ItemClickListener clickListener) {
        photos = new ArrayList<>();
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public TrashAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View photoView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trash, parent, false);
        return new TrashAdapter.ViewHolder(photoView);
    }

    @Override
    public void onBindViewHolder(@NonNull TrashAdapter.ViewHolder holder, int position) {
        final Trash photo = photos.get(position);
        
        Glide.with(holder.itemView.getContext())
                .load(photo.getDownloadLink())
                .apply(RequestOptions.placeholderOf(R.drawable.placeholder))
                .into(holder.photo);

        holder.name.setText(photo.getName());

        final DateFormat apiDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ",
                new Locale("ru", "RU"));

        final DateFormat localDateFormat = new SimpleDateFormat("dd/MM/yy HH:mm",
                new Locale("ru", "RU"));

        try {

            final Date apiDate = apiDateFormat.parse(photo.getDate());
            final String localFormattedTime = localDateFormat.format(apiDate);
            holder.date.setText(localFormattedTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_trash)
        ImageView photo;

        @BindView(R.id.tv_name)
        TextView name;

        @BindView(R.id.tv_date)
        TextView date;

        @BindView(R.id.rl_trash)
        RelativeLayout rlTrash;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnLongClick(R.id.rl_trash)
        boolean onItemLongClicked() {
            clickListener.onItemClicked(photos.get(getAdapterPosition()));
            return true;
        }

    }

    void updateDataSet(@Nullable final List<Trash> photos) {
        if (photos != null) {

            final TrashDiffUtilCallback diffCallback = new TrashDiffUtilCallback(photos, this.photos);
            final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

            this.photos.clear();
            this.photos.addAll(photos);

            diffResult.dispatchUpdatesTo(this);
        }
    }
}
