package com.lounah.gallery.ui.feed;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.lounah.gallery.R;
import com.lounah.gallery.data.entity.Photo;
import com.lounah.gallery.ui.BaseFragment;
import com.lounah.gallery.ui.navigation.NavigationController;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener,
        OptionsDialogFragment.OnClickListener,
        DownloadPhotoDialogFragment.DownloadPhotoCallback {

    @BindView(R.id.rv_feed)
    RecyclerView rvFeed;

    @BindView(R.id.srl)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    NavigationController mNavigationController;

    private static final String TAG = "FEED_FRAGMENT";

    private FeedViewModel viewModel;
    private FeedAdapter adapter;
    private DownloadPhotoDialogFragment downloadFragment;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(FeedViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            viewModel.getUserFeed().observe(this, feed -> {
            switch (feed.status) {
                case ERROR:
                    processErrorState(feed.data);
                    break;
                case LOADING:
                    processLoadingState(feed.data);
                    break;
                case SUCCESS:
                    if (feed.data != null)
                        processSuccessState(feed.data);
                    break;
            }
        });

        viewModel.getSuccessDeletionResult().observe(this, isSuccessful -> {
            if (isSuccessful) viewModel.refreshFeed(false); else showToast(R.string.error_delete);
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setOnRefreshListener(this);
        initializeAdapter();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.refreshFeed(false);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && viewModel != null)
            viewModel.refreshFeed(false);
    }

    private void initializeAdapter() {
        adapter = new FeedAdapter(new PhotoOnClickListener() {
            @Override
            public void onClick(final int position) {
                openPhotoDetails(position);
            }

            @Override
            public void onLongClick(@NonNull Photo photo) {
                showOptionsDialog(photo);
            }
        });
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 2);
        rvFeed.setLayoutManager(layoutManager);
        rvFeed.setAdapter(adapter);
    }

    private void processErrorState(@Nullable final List<Photo> photos) {
        onHideSwipeLoadingView();
        showToast(R.string.error_fetching_data);
        adapter.updateDataSet(photos);
    }

    private void processSuccessState(@NonNull final List<Photo> feed) {
        onHideSwipeLoadingView();
        adapter.updateDataSet(feed);
    }

    private void processLoadingState(@Nullable final List<Photo> feed) {
        adapter.updateDataSet(feed);
    }

    private void openPhotoDetails(final int position) {
        mNavigationController.navigateToFeedPhotoDetails(position);
    }

    private void showOptionsDialog(@NonNull final Photo photo) {
        OptionsDialogFragment optionsDialog = OptionsDialogFragment.newInstance(photo);
        optionsDialog.show(FeedFragment.this.getChildFragmentManager(), TAG);
    }

    private void onHideSwipeLoadingView() {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }

    private void showToast(final int msgId) {
        Toast.makeText(getContext(),
                msgId,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRefresh() {
        viewModel.refreshFeed(true);
    }

    @Override
    public void onSavePhotoClicked(@NonNull Photo photo) {
        downloadFragment = DownloadPhotoDialogFragment
                .newInstance(photo);
        downloadFragment.show(FeedFragment.this.getChildFragmentManager(), TAG);

    }

    @Override
    public void onDeletePhotoClicked(@NonNull Photo photo) {
        viewModel.deletePhoto(photo);
    }

    @Override
    public void onDownloadCompletedSuccessfully() {
        downloadFragment.dismiss();
        Toast.makeText(getActivity(), R.string.successfully_downloaded, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDownloadCompletedWithError(@NonNull final String error) {
        downloadFragment.dismiss();
        //Toast.makeText(getActivity(), R.string.error_downloading, Toast.LENGTH_SHORT).show();
        Toast.makeText(getActivity(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPhotoAlreadyExists() {
        downloadFragment.dismiss();
        Toast.makeText(getActivity(), R.string.photo_already_exists, Toast.LENGTH_SHORT).show();
    }


}
