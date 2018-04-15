package com.lounah.gallery.util;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.lounah.gallery.data.entity.Photo;
import com.lounah.gallery.data.entity.Trash;

import java.util.List;

public class TrashDiffUtilCallback extends DiffUtil.Callback {

    private List<Trash> oldPhotos;
    private List<Trash> newPhotos;

    public TrashDiffUtilCallback(@NonNull final List<Trash> newPhotos,
                                 @NonNull final List<Trash> oldPhotos) {
        this.newPhotos = newPhotos;
        this.oldPhotos = oldPhotos;
    }

    @Override
    public int getOldListSize() {
        return oldPhotos.size();
    }

    @Override
    public int getNewListSize() {
        return newPhotos.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPhotos.get(oldItemPosition).getId().equals(newPhotos.get(newItemPosition).getId());
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldPhotos.get(oldItemPosition).getName().equals(newPhotos.get(newItemPosition).getName());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }

}
