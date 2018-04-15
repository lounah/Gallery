package com.lounah.gallery.ui.trash;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.lounah.gallery.R;
import com.lounah.gallery.data.entity.Trash;

public class TrashOptionsDialogFragment extends DialogFragment {

    private static final String INITIAL_RESOURCE_TAG = "INITIAL_RESOURCE_TAG";
    private OnClickListener clickListener;

    public static TrashOptionsDialogFragment newInstance(final Trash photo) {
        TrashOptionsDialogFragment frag = new TrashOptionsDialogFragment();
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

        final Trash photo = getArguments().getParcelable(INITIAL_RESOURCE_TAG);

        return new AlertDialog.Builder(getContext())
                .setItems(R.array.trash_options, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            clickListener.onMoveToGalleryClicked(photo);
                            break;
                        case 1:
                            clickListener.onDeletePermanentlyClicked(photo);
                            break;
                    }
                })
                .create();
    }

    public interface OnClickListener {

        void onMoveToGalleryClicked(@NonNull final Trash photo);

        void onDeletePermanentlyClicked(@NonNull final Trash photo);
    }

}
