package com.lounah.gallery.ui.feed;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.lounah.gallery.R;
import com.lounah.gallery.data.entity.Photo;

public class OptionsDialogFragment extends DialogFragment {

    private static final String INITIAL_RESOURCE_TAG = "INITIAL_RESOURCE_TAG";
    private OnClickListener clickListener;

    public static OptionsDialogFragment newInstance(final Photo photo) {
        OptionsDialogFragment frag = new OptionsDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(INITIAL_RESOURCE_TAG, photo);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        clickListener = (OnClickListener) getParentFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Photo photo = getArguments().getParcelable(INITIAL_RESOURCE_TAG);

        return new AlertDialog.Builder(getContext())
                .setItems(R.array.photo_options, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            clickListener.onSavePhotoClicked(photo);
                            break;
                        case 1:
                            clickListener.onDeletePhotoClicked(photo);
                            break;
                    }
                })
                .create();
    }

    public interface OnClickListener {

        void onSavePhotoClicked(@NonNull final Photo photo);
        void onDeletePhotoClicked(@NonNull final Photo photo);

    }

}
