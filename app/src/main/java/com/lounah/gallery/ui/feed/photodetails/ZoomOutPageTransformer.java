package com.lounah.gallery.ui.feed.photodetails;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.View;

public class ZoomOutPageTransformer implements ViewPager.PageTransformer {

    private static final float MIN_SCALE = 0.85f;
    private static final float MIN_ALPHA = 0.5f;

    public void transformPage(@NonNull final View view, float position) {
        int pageWidth = view.getWidth();
        int pageHeight = view.getHeight();

        if (position < -1) view.setAlpha(0);
        else if (position <= 1) {

            final float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            final float verticalMargin = pageHeight * (1 - scaleFactor) / 2;
            final float horizontalMargin = pageWidth * (1 - scaleFactor) / 2;

            if (position < 0) view.setTranslationX(horizontalMargin - verticalMargin / 2);
             else view.setTranslationX(-horizontalMargin + verticalMargin / 2);

            view.setScaleX(scaleFactor);
            view.setScaleY(scaleFactor);

            view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));

        } else view.setAlpha(0);
    }
}
