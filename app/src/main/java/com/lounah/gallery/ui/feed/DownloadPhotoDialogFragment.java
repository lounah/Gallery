package com.lounah.gallery.ui.feed;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lounah.gallery.R;
import com.lounah.gallery.data.entity.Photo;
import com.lounah.gallery.util.FileUtil;
import java.io.File;
import java.io.FileOutputStream;

public class DownloadPhotoDialogFragment extends DialogFragment {

    private static final String RESOURCE_KEY = "RESOURCE_KEY";
    private static final String WORKER_RETAINED_FRAGMENT_TAG =
            "DownloadPhotoDialogFragment.WorkerFragment";

    private Photo photo;
    private WorkerFragment workerFragment;
    private DownloadPhotoCallback mCallback;

    public static DownloadPhotoDialogFragment newInstance(@NonNull final Photo photo) {
        DownloadPhotoDialogFragment fragment = new DownloadPhotoDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(RESOURCE_KEY, photo);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photo = getArguments().getParcelable(RESOURCE_KEY);
        mCallback = (DownloadPhotoCallback) getParentFragment();
    }

    @Override
    public void onActivityCreated (Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        FragmentManager fragmentManager = getFragmentManager();
        workerFragment = (WorkerFragment) fragmentManager.findFragmentByTag(WORKER_RETAINED_FRAGMENT_TAG);
        if (workerFragment == null || workerFragment.getTargetFragment() == null) {
            workerFragment = new WorkerFragment();
            fragmentManager.beginTransaction()
                    .add(workerFragment, WORKER_RETAINED_FRAGMENT_TAG)
                    .commit();
            workerFragment.downloadPhoto(getActivity(), photo);
        }
        workerFragment.setTargetFragment(this, 0);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
        if (workerFragment != null)
            workerFragment.setTargetFragment(null, 0);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new MaterialDialog.Builder(getContext())
                .content(R.string.please_wait)
                .progress(true, 0)
                .progressIndeterminateStyle(true)
                .build();
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
    }

    private void onDownloadComplete() {
        mCallback.onDownloadCompletedSuccessfully();
    }

    private void onDownloadFailed(@NonNull final String error) {
        mCallback.onDownloadCompletedWithError(error);
    }

    private void onPhotoAlreadyExists() {
        mCallback.onPhotoAlreadyExists();
    }

    public static class WorkerFragment extends Fragment {

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        public void downloadPhoto(@NonNull final Context context, @NonNull final Photo photo) {
            final String name = photo.getName();
            final String downloadUrl = photo.getFileDownloadLink();

            Glide.with(context)
                    .asBitmap()
                    .load(downloadUrl)
                    .into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            saveImageFromBitmapToGallery(resource, name);
                        }
                    });
        }

        private void saveImageFromBitmapToGallery(Bitmap bitmap, String imageName) {

            File result = new File(FileUtil.savedPhotosDirectory(), imageName);
            if (!result.exists()) {
                try {
                    FileOutputStream out = new FileOutputStream(result);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                    out.flush();
                    out.close();
                    onDownloadCompleted();
                } catch (Exception e) {
                    e.printStackTrace();
                    onDownloadFailed(e.getMessage());
                }
            } else photoAlreadyExists();
        }


        private void onDownloadCompleted() {
            DownloadPhotoDialogFragment targetFragment = (DownloadPhotoDialogFragment) getTargetFragment();
            if (targetFragment != null) {
                targetFragment.onDownloadComplete();
            }
        }

        private void onDownloadFailed(@NonNull final String error) {
            DownloadPhotoDialogFragment targetFragment = (DownloadPhotoDialogFragment) getTargetFragment();
            if (targetFragment != null) {
                targetFragment.onDownloadFailed(error);
            }
        }

        private void photoAlreadyExists() {
            DownloadPhotoDialogFragment targetFragment = (DownloadPhotoDialogFragment) getTargetFragment();
            if (targetFragment != null) {
                targetFragment.onPhotoAlreadyExists();
            }
        }


    }

    public interface DownloadPhotoCallback {

        void onDownloadCompletedSuccessfully();

        void onDownloadCompletedWithError(@NonNull final String error);

        void onPhotoAlreadyExists();

    }


}
