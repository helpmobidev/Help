package com.mobidevgurls.user.help;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText regemail_editText, regpassword_editText, firstname_editText, lastname_editText, contact_editText, location_editText;
    Button register_button;
    Spinner locationSpinner;
    private FirebaseAuth mAuth;
    FirebaseDatabase addUser;
    DatabaseReference mDatabase;
    FirebaseUser firebaseUser;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        regemail_editText = (EditText) findViewById(R.id.regemail_editText);
        regpassword_editText = (EditText) findViewById(R.id.regpassword_editText);
        firstname_editText = (EditText) findViewById(R.id.firstname_editText);
        lastname_editText = (EditText) findViewById(R.id.lastname_editText);
        contact_editText = (EditText) findViewById(R.id.contact_editText);
        //location_editText = (EditText) findViewById(R.id.location_editText);
        register_button = (Button) findViewById(R.id.register_button);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar_signup);
        locationSpinner = (Spinner) findViewById(R.id.location);

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

    }

    public void registerUser(){
        final String email = regemail_editText.getText().toString().trim();
        final String password = regpassword_editText.getText().toString().trim();
        final String firstname = firstname_editText.getText().toString().trim();
        final String lastname = lastname_editText.getText().toString().trim();
        final String contact = contact_editText.getText().toString().trim();
        final String location = locationSpinner.getSelectedItem().toString().trim();

        if(email.isEmpty()){
            regemail_editText.setError("Email is required");
            regemail_editText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            regemail_editText.setError("Please enter a valid email");
            regemail_editText.requestFocus();
            return;
        }
        if(password.isEmpty()){
            regpassword_editText.setError("Password is required");
            regpassword_editText.requestFocus();
            return;
        }
        if(password.length() <= 5){
            regpassword_editText.setError("Password needs to have at least 6 characters.");
            regpassword_editText.requestFocus();
            return;
        }
        if(firstname.isEmpty()){
            firstname_editText.setError("First Name is required");
            firstname_editText.requestFocus();
            return;
        }
        if(lastname.isEmpty()){
            lastname_editText.setError("Last Name is required");
            lastname_editText.requestFocus();
            return;
        }
        if(contact.isEmpty()){
            contact_editText.setError("Contact is required");
            contact_editText.requestFocus();
            return;
        }
        if(location.isEmpty()){
            locationSpinner.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressBar.setVisibility(View.GONE);
                        if(task.isSuccessful()){
                            Log.d("User Registration", "createUserWithEmail:success");

                            String email = task.getResult().getUser().getEmail();
                            String userId = task.getResult().getUser().getUid();
                            String password = regpassword_editText.getText().toString().trim();
                            String firstname = firstname_editText.getText().toString().trim();
                            String lastname = lastname_editText.getText().toString().trim();
                            String contact = contact_editText.getText().toString().trim();
                            String location = locationSpinner.getSelectedItem().toString().trim();

                            User user = new User(email, password, firstname, lastname, contact, location);

                            mDatabase.child("users").child(userId).setValue(user);

                            Toast.makeText(getApplicationContext(), "User Registered",
                                    Toast.LENGTH_SHORT).show();

                            finish();
                            startActivity(new Intent(getApplicationContext(), HelperSettingsActivity.class));

                        } else {
                            Log.d("User Registration", "createUserWithEmail:failed");
                            Toast.makeText(getApplicationContext(), "User Registration Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });



    }

    private void createNewUser(FirebaseUser userFromRegistration) {
        String email = userFromRegistration.getEmail();
        String userId = userFromRegistration.getUid();
        String password = regpassword_editText.getText().toString().trim();
        String firstname = firstname_editText.getText().toString().trim();
        String lastname = lastname_editText.getText().toString().trim();
        String contact = contact_editText.getText().toString().trim();
        String location = locationSpinner.getSelectedItem().toString().trim();


        User user = new User(email, password, firstname, lastname, contact, location);

        mDatabase.child("users").child(userId).setValue(user);
    }

    public void registerNewUser (String email, String password) {
        mAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Log.d("User Registration", "createUserWithEmail:success");
                            createNewUser(task.getResult().getUser());
                            Toast.makeText(getApplicationContext(), "User Registered",
                                    Toast.LENGTH_SHORT).show();

                            finish();
                            startActivity(new Intent(getApplicationContext(), HelperSettingsActivity.class));
                        } else {
                            Log.d("User Registration", "createUserWithEmail:failed");
                            Toast.makeText(getApplicationContext(), "User Registration Failed",
                                    Toast.LENGTH_SHORT).show();
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
