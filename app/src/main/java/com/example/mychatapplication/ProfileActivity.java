package com.example.mychatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    CircleImageView profileImageView;
    EditText inputProfileUsername, inputProfileCity, inputProfileCountry, inputProfessionProfile;
    Button btnProfileUpdate;

    DatabaseReference mUserRef;
    FirebaseAuth mAuth;
    FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        profileImageView = findViewById(R.id.profileImageView);
        inputProfessionProfile = findViewById(R.id.inputProfessionProfile);
        inputProfileCity = findViewById(R.id.inputProfileCity);
        inputProfileUsername = findViewById(R.id.inputProfileUsername);
        inputProfileCountry = findViewById(R.id.inputProfileCountry);
        btnProfileUpdate = findViewById(R.id.btnProfileUpdate);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Users");

        mUserRef.child(mUser.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String profileImageUrl = snapshot.child("profileImage").getValue().toString();
                    String city = snapshot.child("city").getValue().toString();
                    String country = snapshot.child("country").getValue().toString();
                    String profession = snapshot.child("profession").getValue().toString();
                    String username = snapshot.child("username").getValue().toString();

                    Picasso.get().load(profileImageUrl).into(profileImageView);
                    inputProfileCity.setText(city);
                    inputProfileCountry.setText(country);
                    inputProfessionProfile.setText(profession);
                    inputProfileUsername.setText(username);
                }
                else
                {
                    Toast.makeText(ProfileActivity.this, "Data does not exist", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfileActivity.this, ""+error.getMessage().toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}