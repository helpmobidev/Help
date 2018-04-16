package com.example.user.help;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class UserProfileActivity extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
    String uid;

    EditText editText;
    TextView textView;
    TextView textView2;
    TextView textView3;

    ListView listview;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        mAuth = FirebaseAuth.getInstance();

        editText = (EditText)findViewById(R.id.editText);
        textView = (TextView)findViewById(R.id.textView);
        textView2 = (TextView)findViewById(R.id.textView2);
        textView3 = (TextView)findViewById(R.id.textView3);

        listview=(ListView)findViewById(R.id.listview);
        final ArrayAdapter<String> adapter=new ArrayAdapter<String>(this, R.layout.helper_category, R.id.help_category, list);
        listview.setAdapter(adapter);

        final FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference2 = FirebaseDatabase.getInstance().getReference();
        editText.setFocusable(false);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String firstname = dataSnapshot.child("users").child(uid).child("firstname").getValue(String.class);
                String lastname = dataSnapshot.child("users").child(uid).child("lastname").getValue(String.class);
                String email = dataSnapshot.child("users").child(uid).child("email").getValue(String.class);
                String location = dataSnapshot.child("users").child(uid).child("location").getValue(String.class);

                editText.setText("Location: " + location);
                textView.setText(firstname + " " + lastname);
                textView2.setText(email);
                textView3.setText("Location: " + location);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        databaseReference2.child("helpercategories").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // .child(uid) refers to the ID of the current logged in user, i want to retrieve all his helper categories,
                // after the UID, there's still a dynamic key, tapos arraylist :(, so idk how

                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    for(DataSnapshot childSnapshot:snapshot.getChildren())
                    {
                        String key = childSnapshot.getKey();
                        String value = childSnapshot.getValue().toString();

                        list.add(value);
                        adapter.notifyDataSetChanged();
                        Log.d("helpcate", key + " " + value);
                    }

                }

                /*List<String> myString = new ArrayList<String>();
                //GenericTypeIndicator<List<String>> typeIndicator = new GenericTypeIndicator<List<String>>() {};
                int integer = 0;

                List<String> helperCategories = dataSnapshot.child(uid).child(dataSnapshot.getKey()).getValue((GenericTypeIndicator<List<String>>) myString);
                for(int i = 0; i < helperCategories.size(); i++){
                    Log.d("helpcat", helperCategories.get(i));
                }

                Log.d("numbertot", "total is: " + integer);*/
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
