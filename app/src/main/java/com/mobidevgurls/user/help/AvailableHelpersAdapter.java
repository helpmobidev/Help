package com.mobidevgurls.user.help;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by user on 3/4/2018.
 */

public class AvailableHelpersAdapter extends RecyclerView.Adapter<AvailableHelpersHolder> {
    public ArrayList<AvailableHelpersModel> helperList;

    public AvailableHelpersAdapter(ArrayList<AvailableHelpersModel> helperList) {
        this.helperList = helperList;
    }

    @Override
    public AvailableHelpersHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.available_helpers_item, parent, false);
        return new AvailableHelpersHolder(view);
    }

    @Override
    public void onBindViewHolder(AvailableHelpersHolder holder, int position) {
        AvailableHelpersModel model = this.helperList.get(position);
        holder.getHelperName().setText(model.getHelperName());
        holder.getEmail().setText(model.getEmail());
        holder.getCategory().setText(model.getCategory());
    }

    @Override
    public int getItemCount() {
        return helperList.size();
    }
}
