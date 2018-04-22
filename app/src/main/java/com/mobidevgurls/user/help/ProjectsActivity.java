package com.mobidevgurls.user.help;

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

public class ProjectsActivity extends AppCompatActivity {
    private ArrayList<ProjectsModel> projectList = new ArrayList<ProjectsModel>();
    private RecyclerView recyclerView;
    private ProjectsAdapter projectsAdapter;
    private FirebaseAuth mAuth;
    String uid;
    DatabaseReference dbRef;

    private void setupRecyclerView() {
        this.recyclerView = this.findViewById(R.id.projects_rv);
        this.projectsAdapter = new ProjectsAdapter(projectList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        this.recyclerView.setLayoutManager(layoutManager);
        this.recyclerView.setItemAnimator(new DefaultItemAnimator());
        this.recyclerView.setAdapter(this.projectsAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);

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
                for (DataSnapshot dataSnapshotproj : dataSnapshot.getChildren()) {

                    HelpRequest helpRequest = dataSnapshotproj.getValue(HelpRequest.class);

                    String helpee = helpRequest.getHelpee();
                    String helper = helpRequest.getHelper();
                    String status = helpRequest.getStatus();

                    if(helper.equals(uid) || helpee.equals(uid) || (helper.equals(uid) && helpee.equals(uid))){
                        if(status.equalsIgnoreCase("Accepted")){
                            String category = helpRequest.getCategory();
                            String date = helpRequest.getDate();
                            String time = helpRequest.getStart();
                            String dateandtime = date + " " + time;

                            ProjectsModel projectsModel = new ProjectsModel();
                            projectsModel.setCategory(category);
                            projectsModel.setDetails(dateandtime);

                            projectList.add(projectsModel);

                            Log.d("ACCEPT PROJECTS:", "ACCEPT NA TO");
                        }
                    }
                }
                ProjectsActivity.this.setupRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }



}
