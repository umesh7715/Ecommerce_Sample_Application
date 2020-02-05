package com.andromesh.ecommercesassesment.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.andromesh.ecommercesassesment.R;
import com.andromesh.ecommercesassesment.database.entity.ecommerce.Variant;
import com.andromesh.ecommercesassesment.databinding.VariantListRowItemBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class VariantAdapter extends RecyclerView.Adapter<VariantAdapter.MyViewHolder> {

    private final List<Variant> itemList;
    private final Activity activity;
    private LayoutInflater layoutInflater;


    public VariantAdapter(Activity activity, List<Variant> itemList) {

        this.itemList = itemList;
        this.activity = activity;
    }


    @NonNull
    @Override
    public VariantAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        VariantListRowItemBinding binding =
                DataBindingUtil.inflate(layoutInflater, R.layout.variant_list_row_item, parent, false);


        return new VariantAdapter.MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull VariantAdapter.MyViewHolder holder, int position) {
        holder.binding.setVariant(itemList.get(position));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final VariantListRowItemBinding binding;

        public MyViewHolder(final VariantListRowItemBinding itemBinding) {
            super(itemBinding.getRoot());
            this.binding = itemBinding;
        }
    }
}
