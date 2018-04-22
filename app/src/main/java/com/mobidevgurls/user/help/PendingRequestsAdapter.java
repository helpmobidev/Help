package com.mobidevgurls.user.help;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by user on 3/12/2018.
 */

public class PendingRequestsAdapter extends RecyclerView.Adapter<PendingRequestsHolder> {
    public ArrayList<ProjectsData> requestList;

    public PendingRequestsAdapter(ArrayList<ProjectsData> requestList) {
        this.requestList = requestList;
    }

    @Override
    public PendingRequestsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_request_item, parent, false);
        return new PendingRequestsHolder(view);
    }

    @Override
    public void onBindViewHolder(PendingRequestsHolder holder, int position) {
        ProjectsData model = this.requestList.get(position);
        holder.getHelpCategory().setText(model.getCategory());
        holder.getHelpDetails().setText(model.getDate());
    }

    @Override
    public int getItemCount() {
        return this.requestList.size();
    }
}
