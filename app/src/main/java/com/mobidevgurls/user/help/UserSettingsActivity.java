package com.mobidevgurls.user.help;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class UserSettingsActivity extends AppCompatActivity {

    private static final int CHOOSE_IMAGE = 101;
    ImageView imageView;
    Button save_button;

    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String uid;
    String uemail;

    Uri uriProfileImage;
    String profileImageURL;

    EditText firstname_editText;
    EditText lastname_editText;
    EditText password_editText;
    EditText contact_editText;
    EditText location_editText;
    Spinner locationSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        mAuth = FirebaseAuth.getInstance();

        firstname_editText = (EditText)findViewById(R.id.firstname_editText);
        lastname_editText = (EditText)findViewById(R.id.lastname_editText);
        password_editText = (EditText)findViewById(R.id.password_editText);
        contact_editText = (EditText)findViewById(R.id.contact_editText);
        location_editText = (EditText)findViewById(R.id.location_editText);
        locationSpinner = (Spinner) findViewById(R.id.location);

        imageView = (ImageView) findViewById(R.id.imageView);
        save_button = (Button) findViewById(R.id.save_button);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChooser();
            }
        });

        //loadUserInformation();

        /* save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInformation();
            }
        }); */

        final FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();
        uemail = user.getEmail();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String firstname = dataSnapshot.child("users").child(uid).child("firstname").getValue(String.class);
                String lastname = dataSnapshot.child("users").child(uid).child("lastname").getValue(String.class);
                String email = dataSnapshot.child("users").child(uid).child("email").getValue(String.class);
                String password = dataSnapshot.child("users").child(uid).child("password").getValue(String.class);
                String location = locationSpinner.getSelectedItem().toString().trim();
                String location2 = dataSnapshot.child("users").child(uid).child("location").getValue(String.class);
                String contact = dataSnapshot.child("users").child(uid).child("contact").getValue(String.class);

                firstname_editText.setText(firstname);
                lastname_editText.setText(lastname);
                password_editText.setText(password);
                location_editText.setText(location2);
                contact_editText.setText(contact);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference databaseReferenceUser = FirebaseDatabase.getInstance().getReference("users").child(uid);

                final String password = password_editText.getText().toString().trim();
                final String firstname = firstname_editText.getText().toString().trim();
                final String lastname = lastname_editText.getText().toString().trim();
                final String contact = contact_editText.getText().toString().trim();
                final String location = locationSpinner.getSelectedItem().toString().trim();


                if(password.isEmpty()){
                    password_editText.setError("Password is required");
                    password_editText.requestFocus();
                    return;
                }
                if(password.length() <= 5){
                    password_editText.setError("Password needs to have at least 6 characters.");
                    password_editText.requestFocus();
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

                User updatedUser = new User(uemail, password, firstname, lastname, contact, location);
                databaseReferenceUser.setValue(updatedUser);

                Toast.makeText(UserSettingsActivity.this, "User Updated Successfully.", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();
        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }else {
            final FirebaseUser user = mAuth.getCurrentUser();
            uid = user.getUid();
            databaseReference = FirebaseDatabase.getInstance().getReference();

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String firstname = dataSnapshot.child("users").child(uid).child("firstname").getValue(String.class);
                    String lastname = dataSnapshot.child("users").child(uid).child("lastname").getValue(String.class);
                    String email = dataSnapshot.child("users").child(uid).child("email").getValue(String.class);
                    String password = dataSnapshot.child("users").child(uid).child("password").getValue(String.class);
                    String location = dataSnapshot.child("users").child(uid).child("location").getValue(String.class);
                    String contact = dataSnapshot.child("users").child(uid).child("contact").getValue(String.class);

                    firstname_editText.setText(firstname);
                    lastname_editText.setText(lastname);
                    password_editText.setText(password);
                    contact_editText.setText(contact);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    private void loadUserInformation() {
        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();

        String photoUrl = user.getPhotoUrl().toString();
        String displayName = user.getDisplayName();
    }

    private void saveUserInformation() {
        FirebaseUser user = mAuth.getCurrentUser();

        if(user!=null && profileImageURL != null){
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setPhotoUri(Uri.parse(profileImageURL))
                    .build();

            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                Toast.makeText(UserSettingsActivity.this, "Image Updated!", Toast.LENGTH_SHORT);
                            }
                        }
                    });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData()!=null){
            uriProfileImage = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                imageView.setImageBitmap(bitmap);

                uploadImageToFirebaseStorage();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void uploadImageToFirebaseStorage() {
        StorageReference profileImageRef = FirebaseStorage.getInstance().getReference("profilepics/" + System.currentTimeMillis() + ".jpg");

        if (uriProfileImage != null) {
            profileImageRef.putFile(uriProfileImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    profileImageURL = taskSnapshot.getDownloadUrl().toString();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UserSettingsActivity.this, e.getMessage(), Toast.LENGTH_SHORT);
                }
            });
        }
    }

    private void showImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE);
    }
}
