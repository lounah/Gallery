package com.lounah.gallery.di;


import android.app.Application;

import com.lounah.gallery.GalleryApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {
        MainActivityModule.class,
        AppModule.class,
        AndroidSupportInjectionModule.class,
        AndroidInjectionModule.class
})
public interface AppComponent extends AndroidInjector<GalleryApplication> {
    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }
}
