package com.mobidevgurls.user.help;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;



public class MainSettingsActivity extends AppCompatActivity {

    TextView settings_link;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_settings);

        settings_link = (TextView) findViewById(R.id.settings_link);
        settings_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userprofile = new Intent(MainSettingsActivity.this, UserSettingsActivity.class);
                startActivity(userprofile);
            }
        });
    }
}
