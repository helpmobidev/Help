package com.mobidevgurls.user.help;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by USER on 4/6/2018.
 */

public class DeclinedRequestsAdapter extends RecyclerView.Adapter<DeclinedRequestsHolder> {
    public ArrayList<DeclinedRequestsModel> requestList;

    public DeclinedRequestsAdapter(ArrayList<DeclinedRequestsModel> requestList) {
        this.requestList = requestList;
    }

    @Override
    public DeclinedRequestsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.declined_request_item, parent, false);
        return new DeclinedRequestsHolder(view);
    }

    @Override
    public void onBindViewHolder(DeclinedRequestsHolder holder, int position) {
        DeclinedRequestsModel model = this.requestList.get(position);
        holder.getHelpCategory().setText(model.getHelpCategory());
        holder.getHelpDetails().setText(model.getHelpDetails());
    }

    @Override
    public int getItemCount() {
        return this.requestList.size();
    }
}
