package com.mobidevgurls.user.help;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by user on 3/12/2018.
 */

public class MainActivityHolder extends RecyclerView.ViewHolder {
    private TextView category, mainCategory;
    private static final String TAG = "HELP LOG:";

    public MainActivityHolder(View view) {
        super(view);

        category = (TextView) view.findViewById(R.id.sub_category);
        //this.mainCategory = view.findViewById(R.id.help_details);

        Button getHelper = view.findViewById(R.id.get_helpers_btn);
        getHelper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "GET HELP BUTTON PRESSED");
                Activity activity = (Activity) itemView.getContext();
                Intent helperIntent = new Intent(activity, AvailableHelpersActivity.class);
                helperIntent.putExtra("CATEGORY", category.getText().toString());
                activity.startActivity(helperIntent);
            }
        });
    }

    public TextView getCategory() {
        return this.category;
    }

   /* public TextView getMainCategory() {
        return this.mainCategory;
    } */
}
