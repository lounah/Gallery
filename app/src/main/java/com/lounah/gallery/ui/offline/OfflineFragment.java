package com.lounah.gallery.ui.offline;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lounah.gallery.R;
import com.lounah.gallery.ui.BaseFragment;
import com.lounah.gallery.util.FileUtil;

import java.io.File;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;


public class OfflineFragment extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @BindView(R.id.rv_photos)
    RecyclerView rvPhotos;

    @BindView(R.id.tv_empty)
    TextView emptyView;

    @BindView(R.id.srl)
    SwipeRefreshLayout swipeRefreshLayout;

    private OfflineFragmentViewModel viewModel;
    private OfflineFragmentRvAdapter adapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(OfflineFragmentViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel.getSavedPhotosPaths(FileUtil.savedPhotosDirectory())
                .observe(this, paths -> {
                if (!paths.isEmpty()) {
                    enableDataView();
                    onHideSwipeLoadingView();
                    adapter.updateDataSet(paths);
                } else showEmptyView();
                });
    }

    private void enableDataView() {
        emptyView.setVisibility(View.GONE);
        rvPhotos.setVisibility(View.VISIBLE);
    }

    private void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        rvPhotos.setVisibility(View.GONE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_offline, container, false);
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setOnRefreshListener(this);
        initializeAdapter();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.refresh();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser && viewModel != null)
            viewModel.refresh();
    }

    private void initializeAdapter() {
        adapter = new OfflineFragmentRvAdapter(this::onOpenImage);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        rvPhotos.setLayoutManager(layoutManager);
        rvPhotos.setAdapter(adapter);
    }

    private void onHideSwipeLoadingView() {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        viewModel.refresh();
    }

    /*
        Открывает фотографию по заданному URI в галерее
     */
    private void onOpenImage(@NonNull final String path) {
        final Intent goToGalleryIntent = new Intent(Intent.ACTION_VIEW);
        goToGalleryIntent.setDataAndType(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ?
                        FileProvider.getUriForFile(getContext(),  "com.lounah.gallery.provider", new File(path))
                        : Uri.fromFile(new File(path)),
                "image/*").addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(goToGalleryIntent);
    }
}
