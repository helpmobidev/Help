package com.mobidevgurls.user.help;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by USER on 4/6/2018.
 */

public class DeclinedRequestsHolder extends RecyclerView.ViewHolder {
    private TextView helpCategory;
    private TextView helpDetails;

    public DeclinedRequestsHolder(View view) {
        super(view);

        this.helpCategory = view.findViewById(R.id.help_category);
        this.helpDetails = view.findViewById(R.id.help_details);
    }

    public TextView getHelpCategory() {
        return this.helpCategory;
    }

    public TextView getHelpDetails() {
        return this.helpDetails;
    }
}
