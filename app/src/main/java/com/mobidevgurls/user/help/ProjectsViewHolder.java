package com.mobidevgurls.user.help;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by user on 3/4/2018.
 */

public class ProjectsViewHolder extends RecyclerView.ViewHolder {
    private TextView category;
    private TextView details;

    public ProjectsViewHolder(View view) {
        super(view);

        this.category = view.findViewById(R.id.category_text);
        this.details = view.findViewById(R.id.date_time_text);

        Button viewDetails = view.findViewById(R.id.view_details_btn);
        viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) itemView.getContext();
                Intent redirect = new Intent(activity, ProjectDetailsHelpeeActivity.class);
                redirect.putExtra("APPROVED CATEGORY", category.getText().toString());
                redirect.putExtra("APPROVED DATE", details.getText().toString());
                activity.startActivity(redirect);
            }
        });
    }

    public TextView getCategory() {
        return this.category;
    }

    public TextView getDetails() {
        return this.details;
    }
}
