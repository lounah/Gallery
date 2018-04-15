package com.lounah.gallery.ui.trash;


import android.support.annotation.NonNull;

import com.lounah.gallery.data.entity.Trash;

public interface ItemClickListener {

    void onItemClicked(@NonNull final Trash photo);
}
