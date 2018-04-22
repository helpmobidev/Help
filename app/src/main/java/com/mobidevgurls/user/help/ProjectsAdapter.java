package com.mobidevgurls.user.help;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by user on 3/4/2018.
 */

public class ProjectsAdapter extends RecyclerView.Adapter<ProjectsViewHolder> {
    private ArrayList<ProjectsModel> projectsList;

    public ProjectsAdapter(ArrayList<ProjectsModel> projectsList) {
        this.projectsList = projectsList;
    }

    @Override
    public ProjectsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.projects_item, parent, false);
        return new ProjectsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProjectsViewHolder holder, int position) {
        ProjectsModel model = this.projectsList.get(position);
        holder.getCategory().setText(model.getCategory());
        holder.getDetails().setText(model.getDetails());
    }

    @Override
    public int getItemCount() {
        return projectsList.size();
    }
}
