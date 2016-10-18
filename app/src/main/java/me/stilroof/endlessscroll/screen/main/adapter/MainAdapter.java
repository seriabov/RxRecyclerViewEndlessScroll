package me.stilroof.endlessscroll.screen.main.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import me.stilroof.endlessscroll.R;
import me.stilroof.endlessscroll.model.Product;

/**
 * @author Sergei Riabov 2016
 */

public class MainAdapter extends RecyclerView.Adapter<MainHolder> {

    private List<Product> mProductList;

    public MainAdapter() {
        mProductList = new ArrayList<>();
    }

    public void setData(List<Product> products) {
        mProductList.clear();
        mProductList.addAll(products);
        notifyDataSetChanged();
    }

    public void addData(List<Product> newProducts) {
        int oldListSize = mProductList.size();
        mProductList.addAll(newProducts);
        notifyItemRangeInserted(oldListSize, newProducts.size());
    }

    @Override
    public MainHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainHolder(
                LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false));
    }

    @Override
    public void onBindViewHolder(MainHolder holder, int position) {
        holder.bind(mProductList.get(position));
    }

    @Override
    public int getItemCount() {
        return mProductList.size();
    }
}
