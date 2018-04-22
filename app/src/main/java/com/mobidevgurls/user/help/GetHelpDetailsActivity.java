package com.mobidevgurls.user.help;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GetHelpDetailsActivity extends AppCompatActivity {

    private EditText date;
    private EditText start;
    private EditText end;
    private EditText desc;
    private Button submit;
    private FirebaseAuth mAuth;
    DatabaseReference getHelpDB;
    DatabaseReference databaseReference;
    String uid, helperName, email, helperCategory;
    String helperID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_help_details);

        Intent intent = getIntent();
        helperName = intent.getStringExtra("HELPER NAME");
        Log.d("GetHelp", helperName);

        email = intent.getStringExtra("EMAIL");
        Log.d("GetHelp", email);

        helperCategory = intent.getStringExtra("HELPER CATEGORY");
        Log.d("GetHelp", helperCategory);


        date = (EditText) findViewById(R.id.date_input);
        start = (EditText) findViewById(R.id.start_time_input);
        end = (EditText) findViewById(R.id.end_time_input);
        desc = (EditText) findViewById(R.id.description_input);
        submit = (Button) findViewById(R.id.gethelp_button);


        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) { //no user logged in
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
        getHelpDB = FirebaseDatabase.getInstance().getReference("helpRequest");
        databaseReference = FirebaseDatabase.getInstance().getReference("users");

        final FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    String helperEmail = snapshot.child("email").getValue(String.class);

                        if(email.equals(helperEmail)){
                            helperID = key;
                        }


                }

                Log.d("helperID", " email " + helperID);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String dateInput = date.getText().toString().trim();
                String startInput = start.getText().toString().trim();
                String endInput = end.getText().toString().trim();
                String descInput = desc.getText().toString().trim();
                String helper = helperID;
                String helpee = uid;
                String dummyCateg = helperCategory;
                String status = "Pending"; //DEFAULT
                String notifStatus = "None";

                if(!TextUtils.isEmpty(dateInput) && !TextUtils.isEmpty(startInput) && !TextUtils.isEmpty(endInput)) {
                    String id = getHelpDB.push().getKey();
                    HelpRequest request = new HelpRequest(id, dummyCateg, dateInput, startInput, endInput, descInput, helper, status, helpee, notifStatus);
                    getHelpDB.child(id).setValue(request);

                    Toast.makeText(GetHelpDetailsActivity.this, "Request Added", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(GetHelpDetailsActivity.this, "All fields except for description are required", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
