package com.example.user.help;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AvailableHelpersActivity extends AppCompatActivity {
    private ArrayList<AvailableHelpersModel> helperList = new ArrayList<AvailableHelpersModel>();
    private RecyclerView recyclerView;
    private AvailableHelpersAdapter helpersAdapter;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String uid;

    public void onDataRetrieved() {
        Log.i("OnDataRetrieved", "Data has been retrieved");

        recyclerView = findViewById(R.id.helpers_rv);
        helpersAdapter = new AvailableHelpersAdapter(helperList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(helpersAdapter);
    }

    public final static int HELPER_ACTIVITY_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_helpers);
        Intent intent = getIntent();
        final String category = intent.getStringExtra("CATEGORY");
        Log.d("catCheck", category);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) { //no user logged in
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        final FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("helpercategories");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                DatabaseReference databaseReferenceTwo = FirebaseDatabase.getInstance().getReference("users");

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final String userKey = snapshot.getKey();

                    if (!uid.equals(userKey)) {

                        for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                            for (DataSnapshot childtwoSnapshot : childSnapshot.getChildren()) {

                                String value = childtwoSnapshot.getValue().toString();

                                if (category.equals(value)) {
                                    Log.d("availableHelper", category + " " + value);

                                    databaseReferenceTwo.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                            final String loggedinUserLocation = dataSnapshot.child(uid).child("location").getValue(String.class);
                                            Log.d("himemo", loggedinUserLocation + " ");

                                            String name = dataSnapshot.child(userKey).child("firstname").getValue(String.class);
                                            String last = dataSnapshot.child(userKey).child("lastname").getValue(String.class);
                                            String email = dataSnapshot.child(userKey).child("email").getValue(String.class);
                                            String location = dataSnapshot.child(userKey).child("location").getValue(String.class);
                                            Log.d("nameHelper", name + " " + last + " " + email);

                                            String fullname = name + " " + last;

                                            if(loggedinUserLocation.equalsIgnoreCase(location)){
                                                AvailableHelpersModel availableHelpersModel = new AvailableHelpersModel(fullname, email, category);
                                                helperList.add(availableHelpersModel);

                                                AvailableHelpersActivity.this.onDataRetrieved();
                                            }

                                        }

                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {

                                        }
                                    });
                                }

                            }

                            String key = childSnapshot.getKey();
                            Log.d("equalCheck", key);

                        }


                    }
                }



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        //this.createDummyData();
        //this.setupRecyclerView();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    /*private void createDummyData() {
        this.helperList.add(new AvailableHelpersModel("Leonardo di Caprio", "De La Salle University"));
        this.helperList.add(new AvailableHelpersModel("Sam Clafflin", "Taft Avenue"));
        this.helperList.add(new AvailableHelpersModel("Patrick Dempsey", "Vito Cruz"));
        this.helperList.add(new AvailableHelpersModel("Zac Efron", "Jollibee Quirino"));
        this.helperList.add(new AvailableHelpersModel("Dave Franco", "College of St. Benilde"));
    }*/

    private void setupRecyclerView() {
        this.recyclerView = this.findViewById(R.id.helpers_rv);
        this.helpersAdapter = new AvailableHelpersAdapter(helperList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(this.helpersAdapter);
    }
}
