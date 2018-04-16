package com.example.user.help;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class HelpRequestDetailsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String uid, categoryRequest, dateRequest;
    TextView name_text, location_text, date_text, time_text, description_text, contact_text;
    Button accept_button, decline_button;
    String projectfirstname, projectlastname, projectlocation, projectdate, projectstart, projectend, projectdescription,
    projectid, projecthelpeeid, projecthelperid, projectcontact, projectstatus, projectcategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_request_details);

        Intent intent = getIntent();
        categoryRequest = intent.getStringExtra("CATEGORY REQUEST");
        Log.d("GetRequest", categoryRequest);

        dateRequest = intent.getStringExtra("DATE REQUEST");
        Log.d("GetRequest", dateRequest);

        name_text = (TextView)findViewById(R.id.name_text);
        location_text = (TextView)findViewById(R.id.location_text);
        date_text = (TextView)findViewById(R.id.date_text);
        time_text = (TextView)findViewById(R.id.time_text);
        description_text = (TextView)findViewById(R.id.description_text);
        contact_text = (TextView)findViewById(R.id.contact_text);
        accept_button = (Button)findViewById(R.id.accept_button);
        decline_button = (Button)findViewById(R.id.decline_button);

        databaseReference = FirebaseDatabase.getInstance().getReference("helpRequest");

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) { //no user logged in
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        final FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                DatabaseReference databaseReferenceTwo = FirebaseDatabase.getInstance().getReference("users");


                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final String key = snapshot.getKey();
                    final String category = snapshot.child("category").getValue(String.class);
                    final String date = snapshot.child("date").getValue(String.class);
                    final String start = snapshot.child("start").getValue(String.class);
                    final String end = snapshot.child("end").getValue(String.class);
                    final String desc = snapshot.child("desc").getValue(String.class);
                    final String helpee = snapshot.child("helpee").getValue(String.class);
                    final String helper = snapshot.child("helper").getValue(String.class);
                    final String notifStatus = snapshot.child("notifStatus").getValue(String.class);
                    String compDate = date + " " + start;
                    String compTime = start + " - " + end;


                    Log.d("details", key + " " + category + " " + date + " " + start + " " + helpee);

                    if(categoryRequest.equals(category) && dateRequest.equals(compDate)){
                        date_text.setText(date);
                        time_text.setText(compTime);
                        description_text.setText(desc);

                        projectcategory = category;
                        projectdate = date;
                        projectstart = start;
                        projectend = end;
                        projectdescription = desc;
                        projecthelpeeid = helpee;
                        projecthelperid = helper;
                        projectid = key;

                        Log.d("AcceptedProject", "Proj " + projectid);
                        Log.d("AcceptedProject", "Proj " + projectcategory);
                        Log.d("AcceptedProject", "Proj " + projectdate);
                        Log.d("AcceptedProject", "Proj " + projectstart);
                        Log.d("AcceptedProject", "Proj " + projectend);
                        Log.d("AcceptedProject", "Proj " + projecthelpeeid);
                        Log.d("AcceptedProject", "Proj " + projecthelperid);

                        accept_button.setOnClickListener(new View.OnClickListener() {
                            DatabaseReference databaseReferenceTwo = FirebaseDatabase.getInstance().getReference("helpRequest").child(key);

                            @Override
                            public void onClick(View view) {
                                final String ApprovedStatus = "Accepted";
                                final String notifStatus = "Not Read";
                                HelpRequest acceptedRequest = new HelpRequest(key, category, date, start, end, desc, helper, ApprovedStatus, helpee, notifStatus);
                                databaseReferenceTwo.setValue(acceptedRequest);

                                Toast.makeText(HelpRequestDetailsActivity.this, "Request Accepted! Yay!", Toast.LENGTH_LONG).show();
                                finish();

                                startActivity(new Intent(getApplicationContext(), MainActivity.class));

                            }
                        });


                        decline_button.setOnClickListener(new View.OnClickListener() {
                            DatabaseReference databaseReferenceTwo = FirebaseDatabase.getInstance().getReference("helpRequest").child(key);

                            @Override
                            public void onClick(View view) {
                                final String DeclinedStatus = "Declined";
                                final String notifStatus = "None";
                                HelpRequest declinedRequest = new HelpRequest(key, category, date, start, end, desc, helper, DeclinedStatus, helpee, notifStatus);
                                databaseReferenceTwo.setValue(declinedRequest);

                                Toast.makeText(HelpRequestDetailsActivity.this, "Request Declined! Aww :(", Toast.LENGTH_LONG).show();
                                finish();
                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            }
                        });

                        databaseReferenceTwo.child(helpee).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                String firstname = dataSnapshot.child("firstname").getValue(String.class);
                                String lastname = dataSnapshot.child("lastname").getValue(String.class);
                                String fullname = firstname + " " + lastname;
                                String location = dataSnapshot.child("location").getValue(String.class);
                                String contact = dataSnapshot.child("contact").getValue(String.class);

                                name_text.setText(fullname);
                                location_text.setText(location);
                                contact_text.setText(contact);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
