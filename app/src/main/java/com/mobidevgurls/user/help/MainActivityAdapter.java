package com.mobidevgurls.user.help;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by user on 3/12/2018.
 */

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityHolder> {
    public ArrayList<HelperCategory> categoryList;

    public MainActivityAdapter(ArrayList<HelperCategory> categoryList) {
        this.categoryList = categoryList;
    }

    @Override
    public MainActivityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new MainActivityHolder(view);
    }

    @Override
    public void onBindViewHolder(MainActivityHolder holder, int position) {
        HelperCategory model = this.categoryList.get(position);
        holder.getCategory().setText(model.getCategory());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
