package com.andromesh.ecommercesassesment.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.andromesh.ecommercesassesment.R;
import com.andromesh.ecommercesassesment.bindingUtils.interfaceCallback.ItemClickCallback;
import com.andromesh.ecommercesassesment.database.entity.ecommerce.Product;
import com.andromesh.ecommercesassesment.databinding.ProductListRowBinding;
import com.andromesh.ecommercesassesment.view_models.ECommerceViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.MyViewHolder> {

    private final ECommerceViewModel eCommerceViewModel;
    private final List<Product> itemList;
    private final Activity activity;
    private LayoutInflater layoutInflater;
    private ItemClickCallback itemClickCallback;

    public ProductsAdapter(Activity activity, List<Product> itemList, ECommerceViewModel eCommerceViewModel, ItemClickCallback itemClickCallback) {

        this.itemList = itemList;
        this.activity = activity;
        this.eCommerceViewModel = eCommerceViewModel;
        this.itemClickCallback = itemClickCallback;

    }


    @NonNull
    @Override
    public ProductsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ProductListRowBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.product_list_row, parent, false);


        return new ProductsAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductsAdapter.MyViewHolder holder, int position) {
        holder.binding.setProduct(itemList.get(position));
        holder.binding.setCallback(itemClickCallback);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public void setProducts(List<Product> products) {
        itemList.clear();
        itemList.addAll(products);
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ProductListRowBinding binding;

        public MyViewHolder(final ProductListRowBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}