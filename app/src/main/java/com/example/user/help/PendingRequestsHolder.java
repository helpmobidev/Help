package com.example.user.help;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by user on 3/12/2018.
 */

public class PendingRequestsHolder extends RecyclerView.ViewHolder {
    private TextView helpCategory;
    private TextView helpDetails;

    public PendingRequestsHolder(View view) {
        super(view);

        this.helpCategory = view.findViewById(R.id.help_category);
        this.helpDetails = view.findViewById(R.id.help_details);

        Button viewDetails = view.findViewById(R.id.view_details_btn);
        viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) itemView.getContext();
                Intent redirect = new Intent(activity, HelpRequestDetailsActivity.class);
                redirect.putExtra("CATEGORY REQUEST", helpCategory.getText().toString());
                redirect.putExtra("DATE REQUEST", helpDetails.getText().toString());
                activity.startActivity(redirect);
            }
        });
    }

    public TextView getHelpCategory() {
        return this.helpCategory;
    }

    public TextView getHelpDetails() {
        return this.helpDetails;
    }
}
