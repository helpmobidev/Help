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

public class AvailableHelpersHolder extends RecyclerView.ViewHolder {
    private TextView helperName;
    private TextView email;
    private TextView category;

    public AvailableHelpersHolder(View view) {
        super(view);

        this.helperName = view.findViewById(R.id.sub_category);
        this.email = view.findViewById(R.id.help_details);
        this.category = view.findViewById(R.id.category);

        Button askHelp = view.findViewById(R.id.ask_button);
        askHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity activity = (Activity) itemView.getContext();
                Intent redirect = new Intent(activity, GetHelpDetailsActivity.class);
                redirect.putExtra("HELPER NAME", helperName.getText().toString());
                redirect.putExtra("EMAIL", email.getText().toString());
                redirect.putExtra("HELPER CATEGORY", category.getText().toString());
                activity.startActivity(redirect);
            }
        });
    }

    public TextView getHelperName() {
        return this.helperName;
    }

    public TextView getEmail() {
        return this.email;
    }

    public TextView getCategory() {
        return this.category;
    }
}
