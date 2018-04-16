package com.example.user.help;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HelperSettingsActivity extends AppCompatActivity {

    CheckBox house_cleaning_checkBox, lawn_mowing_checkBox, house_repairs_checkBox, pet_training_checkBox,
    pet_sitting_checkBox, trainer_checkBox, life_coach_checkBox, assistant_checkBox, instruments_checkBox,
    crafts_checkBox, academics_checkBox, coordinator_checkBox, photographer_checkBox, makeup_checkBox;

    Button save_helper_settings_button;

    List<String> helperCategories = new ArrayList<String>();
    FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helper_settings);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();

        save_helper_settings_button = (Button)findViewById(R.id.save_helper_settings_button);






        house_cleaning_checkBox = (CheckBox)findViewById(R.id.house_cleaning_checkBox);
        lawn_mowing_checkBox = (CheckBox)findViewById(R.id.lawn_mowing_checkBox);
        house_repairs_checkBox = (CheckBox)findViewById(R.id.house_repairs_checkBox);
        pet_training_checkBox = (CheckBox)findViewById(R.id.pet_training_checkBox);
        pet_sitting_checkBox = (CheckBox)findViewById(R.id.pet_sitting_checkBox);
        trainer_checkBox = (CheckBox)findViewById(R.id.trainer_checkBox);
        life_coach_checkBox = (CheckBox)findViewById(R.id.life_coach_checkBox);
        assistant_checkBox = (CheckBox)findViewById(R.id.assistant_checkBox);
        instruments_checkBox = (CheckBox)findViewById(R.id.instruments_checkBox);
        crafts_checkBox = (CheckBox)findViewById(R.id.crafts_checkBox);
        academics_checkBox = (CheckBox)findViewById(R.id.academics_checkBox);
        coordinator_checkBox = (CheckBox)findViewById(R.id.coordinator_checkBox);
        photographer_checkBox = (CheckBox)findViewById(R.id.photographer_checkBox);
        makeup_checkBox = (CheckBox)findViewById(R.id.makeup_checkBox);

        house_cleaning_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true){
                    String category = house_cleaning_checkBox.getText().toString().trim();
                    helperCategories.add(category);
                }else {
                    String category = house_cleaning_checkBox.getText().toString().trim();
                    helperCategories.remove(category);
                }
            }
        });

        lawn_mowing_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true){
                    String category1 = lawn_mowing_checkBox.getText().toString().trim();
                    helperCategories.add(category1);
                }
                else {
                    String category1 = lawn_mowing_checkBox.getText().toString().trim();
                    helperCategories.remove(category1);
                }
            }
        });


        house_repairs_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true){
                    String category2 = house_repairs_checkBox.getText().toString().trim();
                    helperCategories.add(category2);
                }else {
                    String category2 = house_repairs_checkBox.getText().toString().trim();
                    helperCategories.remove(category2);
                }
            }
        });

        pet_training_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true){
                    String category3 = pet_training_checkBox.getText().toString().trim();
                    helperCategories.add(category3);
                }else {
                    String category3 = pet_training_checkBox.getText().toString().trim();
                    helperCategories.remove(category3);
                }
            }
        });

        pet_sitting_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true){
                    String category4 = pet_sitting_checkBox.getText().toString().trim();
                    helperCategories.add(category4);
                }else {
                    String category4 = pet_sitting_checkBox.getText().toString().trim();
                    helperCategories.remove(category4);
                }
            }
        });


        life_coach_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true){
                    String category5 = life_coach_checkBox.getText().toString().trim();
                    helperCategories.add(category5);
                }else {
                    String category5 = life_coach_checkBox.getText().toString().trim();
                    helperCategories.remove(category5);
                }
            }
        });

        assistant_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true){
                    String category6 = assistant_checkBox.getText().toString().trim();
                    helperCategories.add(category6);
                }else {
                    String category6 = assistant_checkBox.getText().toString().trim();
                    helperCategories.remove(category6);
                }
            }
        });

        trainer_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true){
                    String category7 = trainer_checkBox.getText().toString().trim();
                    helperCategories.add(category7);
                }else {
                    String category7 = trainer_checkBox.getText().toString().trim();
                    helperCategories.remove(category7);
                }
            }
        });

        photographer_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true){
                    String category8 = photographer_checkBox.getText().toString().trim();
                    helperCategories.add(category8);
                }else {
                    String category8 = photographer_checkBox.getText().toString().trim();
                    helperCategories.remove(category8);
                }
            }
        });

        coordinator_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true){
                    String category9 = coordinator_checkBox.getText().toString().trim();
                    helperCategories.add(category9);
                }else {
                    String category9 = coordinator_checkBox.getText().toString().trim();
                    helperCategories.remove(category9);
                }
            }
        });

        makeup_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true){
                    String category10 = makeup_checkBox.getText().toString().trim();
                    helperCategories.add(category10);
                }else {
                    String category10 = makeup_checkBox.getText().toString().trim();
                    helperCategories.remove(category10);
                }
            }
        });

        crafts_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true){
                    String category11 = crafts_checkBox.getText().toString().trim();
                    helperCategories.add(category11);
                }else {
                    String category11 = crafts_checkBox.getText().toString().trim();
                    helperCategories.remove(category11);
                }
            }
        });

        academics_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true){
                    String category12 = academics_checkBox.getText().toString().trim();
                    helperCategories.add(category12);
                }else {
                    String category12 = academics_checkBox.getText().toString().trim();
                    helperCategories.remove(category12);
                }
            }
        });

        instruments_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked == true){
                    String category13 = instruments_checkBox.getText().toString().trim();
                    helperCategories.add(category13);
                }else {
                    String category13 = instruments_checkBox.getText().toString().trim();
                    helperCategories.remove(category13);
                }
            }
        });






        //databaseReference = FirebaseDatabase.getInstance().getReference("helpercategories").child(uid);

        save_helper_settings_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               saveHelper();

            }

        });

    }

    private void saveHelper() {


        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();
        uid = user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("helpercategories").child(uid);

        if(helperCategories.size() > 0) {
            String id = databaseReference.push().getKey();
            databaseReference.child(id).setValue(helperCategories);
            for (int i = 0; i < helperCategories.size(); i++) {
                Log.d("Category: ", helperCategories.get(i));
            }
            Toast toast = Toast.makeText(this, "Helper Settings Saved Successfully.", Toast.LENGTH_LONG);
            View view = toast.getView();
            //To change the Background of Toast
            view.setBackgroundColor(Color.TRANSPARENT);
            toast.show();

            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));

        }
        else if(helperCategories.size() == 0){
            Toast toast = Toast.makeText(this, "Please try again.", Toast.LENGTH_LONG);
            View view = toast.getView();
            //To change the Background of Toast
            view.setBackgroundColor(Color.TRANSPARENT);
            toast.show();
        }
    }

    @Override
    public void onStart(){
        super.onStart();
        if(mAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    @Override
    public void onBackPressed() {

    }
}
