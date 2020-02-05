package com.andromesh.ecommercesassesment.di.module;


import com.andromesh.ecommercesassesment.fragments.ECommerceFragments;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentModule {


    @ContributesAndroidInjector
    abstract ECommerceFragments contributeECommerceFragment();
}
