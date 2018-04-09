package com.lounah.gallery.ui.feed;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lounah.gallery.R;
import com.lounah.gallery.data.entity.Photo;
import com.lounah.gallery.ui.BaseFragment;
import com.lounah.gallery.ui.MainActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class FeedFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rv_feed)
    RecyclerView rvFeed;

    @BindView(R.id.srl)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private FeedViewModel viewModel;
    private FeedAdapter adapter;
    private OptionsDialog optionsDialog;
    private static final String TAG = "FEED_FRAGMENT";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(FeedViewModel.class);
        viewModel.refreshFeed();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void initializeAdapter() {
        adapter = new FeedAdapter(new PhotoOnClickListener() {
            @Override
            public void onClick(@NonNull Photo photo) {
                openPhotoDetails(photo);
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
        onHideLoadingView();
        onHideSwipeLoadingView();
        showToast(R.string.error_fetching_data);
        adapter.updateDataSet(photos);
    }

    private void processSuccessState(@NonNull final List<Photo> feed) {
        onHideLoadingView();
        onHideSwipeLoadingView();
        Timber.i("SUCCESS");
        Toast.makeText(getContext(), "SUCC " + feed.size(), Toast.LENGTH_SHORT).show();
        adapter.updateDataSet(feed);
    }

    private void processLoadingState(@Nullable final List<Photo> feed) {
        onShowLoadingView();
        adapter.updateDataSet(feed);
    }

    private void openPhotoDetails(@NonNull final Photo photo) {

    }

    private void showOptionsDialog(@NonNull final Photo photo) {
        optionsDialog = OptionsDialog.newInstance(photo.getFileDownloadLink());
        optionsDialog.show(getFragmentManager(), TAG);
    }

    private void onShowLoadingView() {

    }

    private void onHideLoadingView() {

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
        viewModel.refreshFeed();
    }
}
