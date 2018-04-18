package com.lounah.gallery.ui.feed.photodetails;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lounah.gallery.R;
import com.lounah.gallery.ui.BaseFragment;
import com.lounah.gallery.ui.MainActivity;
import com.lounah.gallery.ui.feed.FeedViewModel;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhotoDetailsFragment extends BaseFragment {

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @BindView(R.id.vp_photo_preview)
    ViewPager viewPager;

    @BindView(R.id.photo_details_toolbar)
    Toolbar toolbar;

    private static final String INITIAL_POSITION_KEY = "initial_position";
    private static final String CURRENT_POSITION_KEY = "current_position";

    private FeedViewModel mViewModel;
    private PhotoDetailsPagerAdapter adapter;

    private int mCurrentPosition;
    private int mDataSetSize;
    private int mInitialPosition;

    public static PhotoDetailsFragment newInstance(final int itemPosition) {
        PhotoDetailsFragment fragment = new PhotoDetailsFragment();
        Bundle args = new Bundle();
        args.putInt(INITIAL_POSITION_KEY, itemPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(FeedViewModel.class);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mInitialPosition = savedInstanceState == null ?
                getArguments().getInt(INITIAL_POSITION_KEY) :
                savedInstanceState.getInt(CURRENT_POSITION_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_details, container, false);
        ButterKnife.bind(this, view);
        initUI();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setToolbarTitle(mInitialPosition);
        setCurrentItem(mInitialPosition);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mCurrentPosition = position;
                setToolbarTitle(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewModel.getUserFeed().observe(this, feed -> {
            if (feed.data != null) {
                mDataSetSize = feed.data.size();
                adapter.updateDataSet(feed.data);
                setUpToolbar();
                setCurrentItem(mInitialPosition);
            }
        });

    }

    private void initUI() {
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        adapter = new PhotoDetailsPagerAdapter();
        viewPager.setAdapter(adapter);
    }

    private void setToolbarTitle(final int position) {
        toolbar.setTitle((position+1) + " " + getResources().getString(R.string.of) +
                " " + mDataSetSize);
    }

    private void setCurrentItem(final int position) {
        mCurrentPosition = position;
        viewPager.setCurrentItem(position);
    }

    /*
        Это ужасно, я знаю
     */
    private void setUpToolbar() {
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setToolbarTitle(mCurrentPosition);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(CURRENT_POSITION_KEY, mCurrentPosition);
        super.onSaveInstanceState(outState);
    }
}

