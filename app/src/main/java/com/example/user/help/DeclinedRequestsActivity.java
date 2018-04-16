package com.example.user.help;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeclinedRequestsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private DeclinedRequestsAdapter declinedRequestsAdapter;
    private FirebaseAuth mAuth;
    String uid;
    DatabaseReference dbRef;
    private ArrayList<DeclinedRequestsModel> projectList = new ArrayList<DeclinedRequestsModel>();

    private void setupRecyclerView() {

        this.recyclerView = this.findViewById(R.id.declined_requests_rv);
        this.declinedRequestsAdapter = new DeclinedRequestsAdapter(projectList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(declinedRequestsAdapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declined_requests);

        mAuth = FirebaseAuth.getInstance();

        if (mAuth.getCurrentUser() == null) { //no user logged in
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        final FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();

        dbRef = FirebaseDatabase.getInstance().getReference("helpRequest");

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {

                    /*HelpRequest helpRequest = dataSnapshot1.getValue(HelpRequest.class);

                    String helper = helpRequest.getHelper();
                    String status = helpRequest.getStatus();

                    Log.d("Helper", helper + " " + status);*/

                    HelpRequest helpRequest = dataSnapshot1.getValue(HelpRequest.class);

                    String helpee = helpRequest.getHelpee();
                    String helper = helpRequest.getHelper();
                    String status = helpRequest.getStatus();

                    if(helpee.equals(uid) && status.equalsIgnoreCase("Declined") ){

                            String category = helpRequest.getCategory();
                            String date = helpRequest.getDate();
                            String time = helpRequest.getStart();
                            String dateandtime = date + " " + time;

                            DeclinedRequestsModel declinedRequestsModel = new DeclinedRequestsModel();
                            declinedRequestsModel.setHelpCategory(category);
                            declinedRequestsModel.setHelpDetails(dateandtime);

                            projectList.add(declinedRequestsModel);

                            Log.d("DECLINED:", "DECLINED NA TO");
                    }

                }
                DeclinedRequestsActivity.this.setupRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }
}
