package com.andromesh.ecommercesassesment.fragments;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andromesh.ecommercesassesment.R;
import com.andromesh.ecommercesassesment.adapters.VariantAdapter;
import com.andromesh.ecommercesassesment.database.entity.ecommerce.Product;
import com.andromesh.ecommercesassesment.utils.Constants;

import javax.inject.Inject;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProductDetailFragment extends Fragment {

    @BindView(R.id.rvProductDetails)
    RecyclerView rvProductDetails;
    @BindView(R.id.tvProductName)
    TextView tvProductName;


    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private Unbinder binder;
    private LinearLayoutManager layoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_details, container, false);
        binder = ButterKnife.bind(this, view);

        rvProductDetails.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        rvProductDetails.setLayoutManager(layoutManager);

        if (getArguments() != null && getArguments().containsKey(Constants.VARIANTS)) {
            Product product = (Product) (getArguments().get(Constants.VARIANTS));
            VariantAdapter variantAdapter = new VariantAdapter(getActivity(), product.getVariants());
            rvProductDetails.setAdapter(variantAdapter);

            tvProductName.setText(product.getName());
        }


        //initializePostList();

        return view;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binder.unbind();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}