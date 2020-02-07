package com.andromesh.ecommercesassesment.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andromesh.ecommercesassesment.R;
import com.andromesh.ecommercesassesment.adapters.CategoryAdapterV2;
import com.andromesh.ecommercesassesment.adapters.ProductsAdapter;
import com.andromesh.ecommercesassesment.bindingUtils.interfaceCallback.ExecutorCallbackForProduct;
import com.andromesh.ecommercesassesment.database.entity.ecommerce.Category;
import com.andromesh.ecommercesassesment.database.entity.ecommerce.Product;
import com.andromesh.ecommercesassesment.database.entity.ecommerce.RankingProduct;
import com.andromesh.ecommercesassesment.networkBoundResource.Resource;
import com.andromesh.ecommercesassesment.utils.Constants;
import com.andromesh.ecommercesassesment.utils.Constants.SortTypes;
import com.andromesh.ecommercesassesment.utils.RecyclerItemClickListener;
import com.andromesh.ecommercesassesment.view_models.ECommerceViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

import static com.andromesh.ecommercesassesment.networkBoundResource.Resource.Status.LOADING;
import static com.andromesh.ecommercesassesment.networkBoundResource.Resource.Status.SUCCESS;

public class ECommerceFragments extends Fragment {

    @BindView(R.id.rvCategories)
    RecyclerView rvCategories;
    @BindView(R.id.rvProducts)
    RecyclerView rvProducts;
    @BindView(R.id.tvNoProducts)
    TextView tvNoProducts;
    @BindView(R.id.tvCategoryName)
    TextView tvCategoryName;
    @BindView(R.id.pbCategory)
    ProgressBar pbCategory;
    @BindView(R.id.pbProduct)
    ProgressBar pbProduct;
    @BindView(R.id.bottom_sheet)
    LinearLayout bottom_sheet;
    @BindView(R.id.fabSort)
    FloatingActionButton fabSort;
    @BindView(R.id.rgSort)
    RadioGroup rgSort;


    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private Unbinder binder;
    private ECommerceViewModel viewModel;
    private LinearLayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;
    private ProductsAdapter productAdapter;
    private List<Product> products = new ArrayList<>();
    private String sortType = SortTypes.MOST_VIEWED;
    private List<Category> categories;
    private int globalPosition = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ecommerce_list, container, false);
        binder = ButterKnife.bind(this, view);

        rvCategories.setHasFixedSize(true);
        rvProducts.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getContext());
        rvCategories.setLayoutManager(layoutManager);

        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rvProducts.setLayoutManager(gridLayoutManager);


        // Bottom sheet
        BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:

                        if (sortType.equalsIgnoreCase(Constants.SortTypes.MOST_ORDERED)) {
                            ((RadioButton) rgSort.findViewById(R.id.rbMostOrdered)).setChecked(true);
                        } else if (sortType.equalsIgnoreCase(Constants.SortTypes.MOST_SHARED)) {
                            ((RadioButton) rgSort.findViewById(R.id.rbMostShared)).setChecked(true);
                        } else if (sortType.equalsIgnoreCase(Constants.SortTypes.MOST_VIEWED)) {
                            ((RadioButton) rgSort.findViewById(R.id.rbMostViewed)).setChecked(true);
                        }

                        break;
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        fabSort.setOnClickListener(v -> {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        });

        rgSort.setOnCheckedChangeListener((group, checkedId) -> {

            switch (checkedId) {
                case R.id.rbMostViewed:
                    sortType = Constants.SortTypes.MOST_VIEWED;
                    break;
                case R.id.rbMostOrdered:
                    sortType = Constants.SortTypes.MOST_ORDERED;
                    break;
                case R.id.rbMostShared:
                    sortType = Constants.SortTypes.MOST_SHARED;
                    break;
            }

            viewModel.getProducts(categories.get(globalPosition).getId(), sortType).observe(getActivity(), this::updateUIProducts);

            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        });

        ((RadioButton) rgSort.findViewById(R.id.rbMostViewed)).setChecked(true);

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
        this.configureDagger();
        this.configureViewModel();

        if (rgSort != null) {
            if (sortType.equalsIgnoreCase(Constants.SortTypes.MOST_ORDERED)) {
                ((RadioButton) rgSort.findViewById(R.id.rbMostOrdered)).setChecked(true);
            } else if (sortType.equalsIgnoreCase(Constants.SortTypes.MOST_SHARED)) {
                ((RadioButton) rgSort.findViewById(R.id.rbMostShared)).setChecked(true);
            } else if (sortType.equalsIgnoreCase(Constants.SortTypes.MOST_VIEWED)) {
                ((RadioButton) rgSort.findViewById(R.id.rbMostViewed)).setChecked(true);
            }
        }
    }

    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    private void configureViewModel() {

        viewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(ECommerceViewModel.class);
        viewModel.getCategoriesAndProductWithoutDatabaseResource().observe(getActivity(), this::updateUI);


    }

    private void updateUIProducts(Resource<List<Product>> listResource) {

        if (listResource.status == SUCCESS) {
            List<Product> products = listResource.data;

            productAdapter.setProducts(products);

            if (products.size() == 0) {
                tvNoProducts.setVisibility(View.VISIBLE);
                rvProducts.setVisibility(View.GONE);
            } else {
                tvNoProducts.setVisibility(View.GONE);
                rvProducts.setVisibility(View.VISIBLE);
            }
        }
    }


    private void updateUI(Resource<List<Category>> listResource) {

        if (listResource.status == SUCCESS && listResource.data != null && listResource.data.size() > 0) {

            pbCategory.setVisibility(View.GONE);
            pbProduct.setVisibility(View.GONE);

            categories = listResource.data;
            CategoryAdapterV2 categoryAdapter = new CategoryAdapterV2(getActivity(), categories, viewModel);
            rvCategories.setAdapter(categoryAdapter);
            rvCategories.addOnItemTouchListener(
                    new RecyclerItemClickListener(getActivity(), rvCategories, new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            // do whatever
                            globalPosition = position;
                            tvCategoryName.setText(categories.get(position).getName());
                            viewModel.getProducts(categories.get(position).getId(), sortType).observe(getActivity(), this::updateUIProducts);

                        }

                        @Override
                        public void onLongItemClick(View view, int position) {
                            // do whatever
                        }

                        private void updateUIProducts(Resource<List<Product>> listResource) {

                            if (listResource.status == SUCCESS) {
                                List<Product> products = listResource.data;

                                productAdapter.setProducts(products);

                                if (products.size() == 0) {
                                    tvNoProducts.setVisibility(View.VISIBLE);
                                    rvProducts.setVisibility(View.GONE);
                                } else {
                                    tvNoProducts.setVisibility(View.GONE);
                                    rvProducts.setVisibility(View.VISIBLE);
                                }
                            }
                        }


                    })
            );

            setupProductAdapter();
            tvCategoryName.setText(categories.get(globalPosition).getName());
            viewModel.getProducts(categories.get(globalPosition).getId(), sortType).observe(getActivity(), this::updateUIProducts);



           /* new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                        rvCategories.findViewHolderForAdapterPosition(0).itemView.performClick();
                }
            }, 4000);*/

        } else if (listResource.status == LOADING) {
            pbCategory.setVisibility(View.VISIBLE);
            pbProduct.setVisibility(View.VISIBLE);
        } else {
            pbCategory.setVisibility(View.GONE);
            pbProduct.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "Something went wrong..! Please try again", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupProductAdapter() {

        products.clear();
        productAdapter = new ProductsAdapter(getActivity(), products, viewModel, product -> {
            try {

                viewModel.getRankingProduct(product.getId(), new ExecutorCallbackForProduct() {
                    @Override
                    public void fromDiskIO(RankingProduct productRanking) {

                        ProductDetailFragment fragment = new ProductDetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(Constants.PRODUCT, product);
                        bundle.putParcelable(Constants.RANKING_PRODUCT, productRanking);
                        fragment.setArguments(bundle);
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, fragment, null)
                                .addToBackStack("product")
                                .commit();

                    }

                    @Override
                    public void fromNetwork() {

                    }

                    @Override
                    public void fromMain() {

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        rvProducts.setAdapter(productAdapter);

    }


}