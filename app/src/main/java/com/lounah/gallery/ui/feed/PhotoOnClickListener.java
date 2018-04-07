package com.lounah.gallery.ui.feed;


import android.support.annotation.NonNull;

import com.lounah.gallery.data.entity.Photo;

public interface PhotoOnClickListener {

    void onItemClicked(@NonNull final Photo photo);
}
