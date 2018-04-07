package com.lounah.gallery.di;

import com.lounah.gallery.ui.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = {FragmentBindingModule.class})
    abstract MainActivity mainActivity();

}
