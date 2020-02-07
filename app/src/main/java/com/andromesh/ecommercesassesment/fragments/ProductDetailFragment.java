package com.andromesh.ecommercesassesment.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andromesh.ecommercesassesment.R;
import com.andromesh.ecommercesassesment.adapters.VariantAdapter;
import com.andromesh.ecommercesassesment.database.entity.ecommerce.Product;
import com.andromesh.ecommercesassesment.database.entity.ecommerce.RankingProduct;
import com.andromesh.ecommercesassesment.utils.Constants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ProductDetailFragment extends Fragment {

    @BindView(R.id.rvProductDetails)
    RecyclerView rvProductDetails;
    @BindView(R.id.tvProductName)
    TextView tvProductName;
    @BindView(R.id.tvViewsCount)
    TextView tvViewsCount;
    @BindView(R.id.tvSharedCount)
    TextView tvSharedCount;
    @BindView(R.id.tvOrderedCount)
    TextView tvOrderedCount;


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

        if (getArguments() != null && getArguments().containsKey(Constants.PRODUCT)) {
            Product product = (Product) (getArguments().get(Constants.PRODUCT));
            VariantAdapter variantAdapter = new VariantAdapter(getActivity(), product.getVariants());
            rvProductDetails.setAdapter(variantAdapter);

            tvProductName.setText(product.getName());
        }

        if (getArguments() != null && getArguments().containsKey(Constants.RANKING_PRODUCT)) {

            RankingProduct rankingProduct = (RankingProduct) (getArguments().get(Constants.RANKING_PRODUCT));

            tvViewsCount.setText(rankingProduct.getViewCount() != null ? rankingProduct.getViewCount() + "" : "-");
            tvSharedCount.setText(rankingProduct.getShares() != null ? rankingProduct.getShares() + "" : "-");
            tvOrderedCount.setText(rankingProduct.getOrderCount() != null ? rankingProduct.getOrderCount() + "" : "-");

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