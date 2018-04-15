package com.lounah.gallery.ui.trash;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.lounah.gallery.R;
import com.lounah.gallery.data.entity.Trash;
import com.lounah.gallery.ui.BaseFragment;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TrashFragment extends BaseFragment implements
        SwipeRefreshLayout.OnRefreshListener, TrashOptionsDialogFragment.OnClickListener {

    @BindView(R.id.rv_trash)
    RecyclerView rvTrash;

    @BindView(R.id.srl)
    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.tv_empty)
    TextView emptyView;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private static final String TAG = "TRASH_FRAGMENT";

    private TrashViewModel viewModel;
    private TrashAdapter adapter;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(TrashViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel.getClearActionResult().observe(this, isSuccessful -> {
            if (isSuccessful) {
                viewModel.refreshTrash();
                showToast(R.string.successfully_deleted);
            } else showToast(R.string.error_fetching_data);
        });

        viewModel.getDeleteActionResult().observe(this, isSuccessful -> {
            if (isSuccessful) {
                viewModel.refreshTrash();
                showToast(R.string.successfully_deleted);
            } else showToast(R.string.error_delete);
        });

        viewModel.getMoveToGalleryActionResult().observe(this, isSuccessful -> {
            if (isSuccessful) {
                viewModel.refreshTrash();
                showToast(R.string.successfully_moved);
            } else showToast(R.string.error_moving_to_gallery);
        });

        viewModel.getDeletedPhotos().observe(this, photos -> {
            if (!photos.isEmpty()) {
                enableDataView();
                adapter.updateDataSet(photos);
                onHideSwipeLoadingView();
            } else {
                showEmptyView();
                onHideSwipeLoadingView();
            }
        });

    }

    private void enableDataView() {
        emptyView.setVisibility(View.GONE);
        rvTrash.setVisibility(View.VISIBLE);
    }

    private void showEmptyView() {
        emptyView.setVisibility(View.VISIBLE);
        rvTrash.setVisibility(View.GONE);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trash, container, false);
        ButterKnife.bind(this, view);
        swipeRefreshLayout.setOnRefreshListener(this);
        initializeAdapter();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel.refreshTrash();

    }

    private void initializeAdapter() {
        adapter = new TrashAdapter(this::showOptionsDialog);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvTrash.setLayoutManager(layoutManager);
        rvTrash.setAdapter(adapter);
    }

    private void showToast(final int msgId) {
        Toast.makeText(getContext(),
                msgId,
                Toast.LENGTH_SHORT).show();
    }

    private void onHideSwipeLoadingView() {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);
    }
    private void showOptionsDialog(@NonNull final Trash photo) {
        TrashOptionsDialogFragment optionsDialog = TrashOptionsDialogFragment.newInstance(photo);
        optionsDialog.show(TrashFragment.this.getChildFragmentManager(), TAG);
    }

    @Override
    public void onRefresh() {
        viewModel.refreshTrash();
    }

    @Override
    public void onMoveToGalleryClicked(@NonNull Trash photo) {
        viewModel.movePhotoToGallery(photo);
    }

    @Override
    public void onDeletePermanentlyClicked(@NonNull Trash photo) {
        viewModel.deletePhotoPermanently(photo);
    }
}
