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

public class PendingRequestsActivity extends AppCompatActivity {
    private RecyclerView recyclerView1;
    private RecyclerView recyclerView2;
    private PendingRequestsAdapter pendingRequestsAdapter1;
    private PendingRequestsAdapter pendingRequestsAdapter2;
    private FirebaseAuth mAuth;
    String uid;
    DatabaseReference dbRef;
    private ArrayList<ProjectsData> fbProjectList = new ArrayList<ProjectsData>();

    private void setupRecyclerView() {

        this.recyclerView2 = this.findViewById(R.id.help_requests_rv);
        this.pendingRequestsAdapter2 = new PendingRequestsAdapter(fbProjectList);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(this);
        this.recyclerView2.setLayoutManager(layoutManager2);
        this.recyclerView2.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView2.setAdapter(pendingRequestsAdapter2);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_requests);

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

                    HelpRequest helpRequest = dataSnapshot1.getValue(HelpRequest.class);

                    String helper = helpRequest.getHelper();
                    String status = helpRequest.getStatus();

                    Log.d("Helper", helper + " " + status);

                    if(helper.equals(uid) && status.equalsIgnoreCase("Pending")){

                        String category = helpRequest.getCategory();
                        String date = helpRequest.getDate();
                        String time = helpRequest.getStart();
                        String dateandtime = date + " " + time;

                        ProjectsData projectsData = new ProjectsData();
                        projectsData.setCategory(category);
                        projectsData.setDate(dateandtime);

                        fbProjectList.add(projectsData);

                        Log.d("FIREBASE:", "ADDED");

                    }
                }
                PendingRequestsActivity.this.setupRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }


}
