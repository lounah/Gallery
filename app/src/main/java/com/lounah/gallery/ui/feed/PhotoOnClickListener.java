package com.lounah.gallery.ui.feed;


import android.support.annotation.NonNull;

import com.lounah.gallery.data.entity.Photo;

public interface PhotoOnClickListener {

    void onClick(final int position);

    void onLongClick(@NonNull final Photo photo);
}
