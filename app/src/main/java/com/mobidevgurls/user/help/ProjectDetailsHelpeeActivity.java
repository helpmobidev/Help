package com.mobidevgurls.user.help;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProjectDetailsHelpeeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String uid, approvedcategory, approveddate;
    TextView helpee_help_request_detail, textView13, helper_detail, place_detail, date_detail, time_detail, contact_detail;
    String projectfirstname, projectlastname, projectlocation, projectdate, projectstart, projectend, projectdescription,
            projectid, projecthelpeeid, projecthelperid, projectcontact, projectstatus, projectcategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpee_help_details);

        Intent intent = getIntent();
        approvedcategory = intent.getStringExtra("APPROVED CATEGORY");
        Log.d("GetProject", approvedcategory);

        approveddate = intent.getStringExtra("APPROVED DATE");
        Log.d("GetProject", approveddate);


        helpee_help_request_detail = (TextView)findViewById(R.id.helpee_help_request_detail);
        textView13 = (TextView)findViewById(R.id.textView13);
        helper_detail = (TextView)findViewById(R.id.helper_detail);
        place_detail = (TextView)findViewById(R.id.place_detail);
        date_detail = (TextView)findViewById(R.id.date_detail);
        time_detail = (TextView)findViewById(R.id.time_detail);
        contact_detail = (TextView)findViewById(R.id.contact_detail);


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
                String compDate = date + " " + start;
                String compTime = start + " - " + end;


                Log.d("details", key + " " + category + " " + date + " " + start + " " + helpee);

                if(approvedcategory.equals(category) && approveddate.equals(compDate)){
                    date_detail.setText(date);
                    time_detail.setText(compTime);
                    helpee_help_request_detail.setText(category);

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


                    databaseReferenceTwo.child(helpee).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String firstname = dataSnapshot.child("firstname").getValue(String.class);
                            String lastname = dataSnapshot.child("lastname").getValue(String.class);
                            String fullname = firstname + " " + lastname;
                            String location = dataSnapshot.child("location").getValue(String.class);
                            String contact = dataSnapshot.child("contact").getValue(String.class);

                            textView13.setText(fullname);
                            place_detail.setText(location);
                            contact_detail.setText(contact);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    databaseReferenceTwo.child(helper).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String firstname = dataSnapshot.child("firstname").getValue(String.class);
                            String lastname = dataSnapshot.child("lastname").getValue(String.class);
                            String fullname = firstname + " " + lastname;
                            helper_detail.setText(fullname);
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
}
