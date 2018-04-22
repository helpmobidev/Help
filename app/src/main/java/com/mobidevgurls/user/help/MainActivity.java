package com.mobidevgurls.user.help;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private ArrayList<HelperCategory> categoriesList = new ArrayList<HelperCategory>();
    private RecyclerView recyclerView;
    private MainActivityAdapter mainActivityAdapter;
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    String uid;

    List<String> keys = new ArrayList<String>();

    NotificationCompat.Builder notif;
    private static final int uniqueID = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        databaseReference = FirebaseDatabase.getInstance().getReference("MainCategories");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                recyclerView = findViewById(R.id.category_rv);
                mainActivityAdapter = new MainActivityAdapter(categoriesList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(mainActivityAdapter);


                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    String key = snapshot.getKey();
                    HelperCategory category = snapshot.getValue(HelperCategory.class);
                    categoriesList.add(category);
                    mainActivityAdapter.notifyDataSetChanged();
                    Log.d("maincate", key);

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        mAuth = FirebaseAuth.getInstance();

        if(mAuth.getCurrentUser() == null){ //no user logged in
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }


        notif = new NotificationCompat.Builder(this);
        notif.setAutoCancel(true);

        sendNotification();

    }

    public void lookForDeclined() {

    }

    public void sendNotification() {

        final FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();

        databaseReference2 = FirebaseDatabase.getInstance().getReference("helpRequest");

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot dataSnapshotNotif : dataSnapshot.getChildren()) {

                    HelpRequest helpRequest = dataSnapshotNotif.getValue(HelpRequest.class);
                    String key = helpRequest.getRequestID();
                    String helpee = helpRequest.getHelpee();
                    String status = helpRequest.getStatus();
                    String notifStatus = helpRequest.getNotifStatus();
                    String date = helpRequest.getDate();
                    String start = helpRequest.getStart();
                    String end = helpRequest.getEnd();
                    String desc = helpRequest.getDesc();
                    String helper = helpRequest.getHelper();

                    Log.d("HelperResult", helpee + " " + status);
                    if(helpee.equals(uid) && status.equalsIgnoreCase("Accepted") && notifStatus.equalsIgnoreCase("Not Read")){

                        String category = helpRequest.getCategory();

                        notif.setSmallIcon(R.drawable.notif);
                        notif.setTicker("Help!");
                        notif.setWhen(System.currentTimeMillis());
                        notif.setContentTitle("Help! Project");
                        notif.setContentText("Your request for " + category + " has been accepted.");

                        Intent intent = new Intent(MainActivity.this, ProjectsActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                        notif.setContentIntent(pendingIntent);

                        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                        notificationManager.notify(uniqueID, notif.build());

                        dataSnapshotNotif.getRef().setValue(null);

                        DatabaseReference updateDR = FirebaseDatabase.getInstance().getReference("helpRequest").child(key);

                        String newNotifStatus = "Read";

                        HelpRequest updatedHelp = new HelpRequest(key, category, date, start, end, desc, helper, status, helpee, newNotifStatus);
                        updateDR.setValue(updatedHelp);


                        Log.d("ACCEPTED:", "ACCEPTED");

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
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
          int id = item.getItemId();

//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
      return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.user_profile_nav) {
            Intent userprofile = new Intent(MainActivity.this, UserProfileActivity.class);
            this.startActivity(userprofile);
        } else if (id == R.id.settings_nav) {
            Intent settings = new Intent(MainActivity.this, MainSettingsActivity.class);
            this.startActivity(settings);
        } else if (id == R.id.projects_nav) {
            Intent projects = new Intent(MainActivity.this, ProjectsActivity.class);
            this.startActivity(projects);
        } else if (id == R.id.pending_nav) {
            Intent pending = new Intent(MainActivity.this, PendingRequestsActivity.class);
            this.startActivity(pending);
        } else if (id == R.id.declined_nav) {
            Intent dec = new Intent(MainActivity.this, DeclinedRequestsActivity.class);
            this.startActivity(dec);
        } else if (id == R.id.tutorial_nav) {
            Intent tut = new Intent(MainActivity.this, TutorialActivity.class);
            this.startActivity(tut);
        } else if (id == R.id.logout_nav) {

            mAuth = FirebaseAuth.getInstance();

            FirebaseAuth.getInstance().signOut();
            Intent logout = new Intent(getBaseContext(), LoginActivity.class);
            startActivity(logout);
            finish();

            /*Intent logout = new Intent(MainActivity.this, LoginActivity.class);
            this.startActivity(logout);*/
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
