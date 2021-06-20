package com.example.mychatapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class  SetupActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 101;
    CircleImageView profileImageView;
    TextView inputUsername, inputCity, inputProfession, inputCountry;
    Button btnSave;
    Uri imageUrl;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    DatabaseReference mRef;
    StorageReference storageRef;
    ProgressDialog mProgressBar;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        profileImageView = findViewById(R.id.profileImageView);
        inputUsername = findViewById(R.id.inputUsername);
        inputCity = findViewById(R.id.inputCity);
        inputProfession = findViewById(R.id.inputProfession);
        inputCountry = findViewById(R.id.inputCountry);
        btnSave = findViewById(R.id.btnSave);

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mRef = FirebaseDatabase.getInstance().getReference().child("Users");
        storageRef = FirebaseStorage.getInstance().getReference().child("ProfileImages");

        toolbar = findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Profile");

        mProgressBar = new ProgressDialog(this);

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("it works 4");
                SaveData();
            }
        });
    }

    private void SaveData() {
        System.out.println("it works 1");
        final String username = inputUsername.getText().toString();
        final String city = inputCity.getText().toString();
        final String country = inputCountry.getText().toString();
        final String profession = inputProfession.getText().toString();

        if(username.isEmpty() || username.length() < 3)
        {
            showError(inputUsername, "Please enter a valid username.");
        }else if(city.isEmpty() || city.length() < 3){
            showError(inputCity, "Please enter a valid city name.");
        }else if(country.isEmpty() || country.length() < 3){
            showError(inputCountry, "Please enter a valid country name.");
        }else if(profession.isEmpty() || profession.length() < 2){
            showError(inputCountry, "Please enter a valid profession title.");
        }else if(imageUrl == null){
            Toast.makeText(this, "Please add an image", Toast.LENGTH_SHORT).show();
        }else{
            mProgressBar.setTitle("We are creating your new profile now.");
            mProgressBar.setCanceledOnTouchOutside(false);
            mProgressBar.show();
            storageRef.child(mUser.getUid()).putFile(imageUrl).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
                {
                    System.out.println("it works 2");
                    if(task.isSuccessful())
                    {
                        storageRef.child(mUser.getUid()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                HashMap hashMap = new HashMap();
                                hashMap.put("username", username);
                                hashMap.put("city", city);
                                hashMap.put("country", country);
                                hashMap.put("profession", profession);
                                hashMap.put("profileImage", uri.toString());
                                hashMap.put("status", "offline");

                                mRef.child(mUser.getUid()).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                                    @Override
                                    public void onSuccess(Object o) {
                                        System.out.println("it works 3");
                                        Intent intent = new Intent(SetupActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        mProgressBar.dismiss();
                                        Toast.makeText(SetupActivity.this, "Your new profile is complete.", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        mProgressBar.dismiss();
                                        Toast.makeText(SetupActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        });
                    }
                }
            });
        }
    }

    private void showError(TextView input, String message) {
        input.setError(message);
        input.requestFocus();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null)
        {
            imageUrl = data.getData();
            profileImageView.setImageURI(imageUrl);
        }
    }
}