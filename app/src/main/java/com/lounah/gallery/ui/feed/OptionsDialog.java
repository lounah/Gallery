package com.lounah.gallery.ui.feed;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.lounah.gallery.R;

public class OptionsDialog extends DialogFragment {

    private static final String RESOURCE_LINK = "RESOURCE_LINK";

    public static OptionsDialog newInstance(final String resLink) {
        OptionsDialog frag = new OptionsDialog();
        Bundle args = new Bundle();
        args.putString(RESOURCE_LINK, resLink);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final String resLink = getArguments().getString(RESOURCE_LINK);

        return new AlertDialog.Builder(getActivity())
                .setItems(R.array.photo_options, (dialog, which) -> {

                })
                .create();
    }

}
