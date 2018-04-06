package com.lounah.gallery.ui.allphotos;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lounah.gallery.R;
import com.lounah.gallery.ui.BaseFragment;

import butterknife.ButterKnife;
import timber.log.Timber;

public class AllPhotosFragment extends BaseFragment {

    public static AllPhotosFragment newInstance() {
        return new AllPhotosFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        Timber.i("ON ATTACH");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Timber.i("ON CREATE");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Timber.i("ON CREATE VIEW");

        View view = inflater.inflate(R.layout.fragment_all_photos, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Timber.i("ON ACTIVITY CREATED");
    }

    @Override
    public void onStart() {
        super.onStart();

        Timber.i("ON START");
    }

    @Override
    public void onResume() {
        super.onResume();

        Timber.i("ON RESUME");
    }

    @Override
    public void onPause() {
        super.onPause();

        Timber.i("ON PAUSE");
    }

    @Override
    public void onStop() {
        super.onStop();

        Timber.i("ON STOP");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        Timber.i("ON DESTROY VIEW");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Timber.i("ON DESTROY");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        Timber.i("ON DETACH");
    }

}
