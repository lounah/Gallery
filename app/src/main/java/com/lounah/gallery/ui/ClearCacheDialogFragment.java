package com.lounah.gallery.ui;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;

import com.lounah.gallery.R;

public class ClearCacheDialogFragment extends DialogFragment {

    private OnClickListener mClickListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mClickListener = (OnClickListener) getActivity();
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_clear_cache)
                .setPositiveButton(R.string.yes, (dialog, id) -> {
                    mClickListener.onClearCache();
                })
                .setNegativeButton(R.string.cancel, (dialog, id) -> {
                    mClickListener.onCancel();
                });
        return builder.create();
    }

    public interface OnClickListener {

        void onCancel();

        void onClearCache();

    }

}
