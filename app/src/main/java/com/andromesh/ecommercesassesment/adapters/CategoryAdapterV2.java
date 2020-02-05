package com.andromesh.ecommercesassesment.adapters;



import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.andromesh.ecommercesassesment.R;
import com.andromesh.ecommercesassesment.database.entity.ecommerce.Category;
import com.andromesh.ecommercesassesment.databinding.CategoryListRowV3Binding;
import com.andromesh.ecommercesassesment.view_models.ECommerceViewModel;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class CategoryAdapterV2 extends RecyclerView.Adapter<CategoryAdapterV2.MyViewHolder> {

    private final ECommerceViewModel eCommerceViewModel;
    private final List<Category> itemList;
    private final Activity activity;
    private LayoutInflater layoutInflater;


    public CategoryAdapterV2(Activity activity, List<Category> itemList, ECommerceViewModel eCommerceViewModel) {

        this.itemList = itemList;
        this.activity = activity;
        this.eCommerceViewModel = eCommerceViewModel;
    }


    @NonNull
    @Override
    public CategoryAdapterV2.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        CategoryListRowV3Binding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.category_list_row_v3, parent, false);


        return new CategoryAdapterV2.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapterV2.MyViewHolder holder, int position) {
        holder.binding.setProductcategory(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final CategoryListRowV3Binding binding;

        public MyViewHolder(final CategoryListRowV3Binding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}