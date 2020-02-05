package com.andromesh.ecommercesassesment.di.component;

import android.app.Application;


import com.andromesh.ecommercesassesment.App;
import com.andromesh.ecommercesassesment.di.module.ActivityModule;
import com.andromesh.ecommercesassesment.di.module.AppModule;
import com.andromesh.ecommercesassesment.di.module.FragmentModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;


@Singleton
@Component(modules={ActivityModule.class, FragmentModule.class, AppModule.class, AndroidInjectionModule.class})
public interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);
        AppComponent build();
    }

    void inject(App app);
}
