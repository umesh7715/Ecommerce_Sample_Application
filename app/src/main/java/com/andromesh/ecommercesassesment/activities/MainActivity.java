package com.andromesh.ecommercesassesment.activities;


import android.os.Bundle;

import com.andromesh.ecommercesassesment.R;
import com.andromesh.ecommercesassesment.fragments.ECommerceFragments;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import dagger.android.AndroidInjection;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureDagger();
        this.showFragment(savedInstanceState);
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    private void showFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {

            ECommerceFragments fragment = new ECommerceFragments();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, fragment, null)
                    .commit();
        }
    }

    private void configureDagger() {
        AndroidInjection.inject(this);
    }
}
