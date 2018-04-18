package com.lounah.gallery.data.ui;

import android.arch.lifecycle.MutableLiveData;
import android.support.test.runner.AndroidJUnit4;

import com.lounah.gallery.R;
import com.lounah.gallery.data.entity.Photo;
import com.lounah.gallery.data.entity.Resource;
import com.lounah.gallery.ui.feed.FeedViewModel;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.mockito.AdditionalMatchers.not;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(AndroidJUnit4.class)
public class FeedFragmentTest {

    private FeedViewModel viewModel;
    private MutableLiveData<Resource<List<Photo>>> feed = new MutableLiveData<>();

    @Before
    public void init() throws Throwable {
        viewModel = mock(FeedViewModel.class);
        when(viewModel.getUserFeed()).thenReturn(feed);
    }

    @Test
    public void loading() {
        viewModel.refreshFeed(true);
        onView(withId(R.id.srl)).check(matches(isDisplayed()));
    }

    @Test
    public void error() throws InterruptedException {
        feed.postValue(Resource.error(null));
        onView(withId(R.id.srl)).check(matches(not(isDisplayed())));
    }

}
