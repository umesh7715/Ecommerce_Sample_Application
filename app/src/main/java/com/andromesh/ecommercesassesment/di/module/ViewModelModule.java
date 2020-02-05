package com.andromesh.ecommercesassesment.di.module;


import com.andromesh.ecommercesassesment.di.key.ViewModelKey;
import com.andromesh.ecommercesassesment.view_models.ECommerceViewModel;
import com.andromesh.ecommercesassesment.view_models.FactoryViewModel;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {
    

    @Binds
    @IntoMap
    @ViewModelKey(ECommerceViewModel.class)
    abstract ViewModel bindECommerceViewModel(ECommerceViewModel repoViewModel);


    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(FactoryViewModel factory);
}
