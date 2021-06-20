package com.example.mychatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {
    private TextInputLayout inputEmail, inputPassword,inputConfirmPassword;
    Button btnRegistration;
    TextView alreadyHaveAnAccount;
    FirebaseAuth mAuth;
    ProgressDialog mProgressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        inputConfirmPassword = findViewById(R.id.inputConfirmPassword);
        btnRegistration = findViewById(R.id.btnRegistration);
        alreadyHaveAnAccount = findViewById(R.id.alreadyHaveAnAccount);
        mAuth = FirebaseAuth.getInstance();
        mProgressBar = new ProgressDialog(this);

        btnRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttemptRegistration();
            }
        });
        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void AttemptRegistration() {
        String email = inputEmail.getEditText().getText().toString();
        String password = inputPassword.getEditText().getText().toString();
        String confirmPassword = inputConfirmPassword.getEditText().getText().toString();

        if(email.isEmpty() || !email.contains("@")){
            showError(inputEmail, "This is not a valid email address");
        }else if(password.isEmpty() || password.length() < 8){
            showError(inputPassword, "A valid password must contain 8 letters or numbers");
        }else if(confirmPassword.isEmpty() || !confirmPassword.equals(password)){
            showError(inputConfirmPassword, "Passwords do not match");
        }else{
            mProgressBar.setTitle("Registration");
            mProgressBar.setMessage("Please Wait, while we register your new account.");
            mProgressBar.setCanceledOnTouchOutside(false);
            mProgressBar.show();

            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        mProgressBar.dismiss();
                        Toast.makeText(RegistrationActivity.this, "Congratulations, you have been registered successfully", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegistrationActivity.this, SetupActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        mProgressBar.dismiss();
                        Toast.makeText(RegistrationActivity.this, "Your registration has failed recheck your information. ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void showError(TextInputLayout field, String text) {
        field.setError(text);
        field.requestFocus();
    }
}