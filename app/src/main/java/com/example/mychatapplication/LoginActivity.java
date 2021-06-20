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

public class LoginActivity extends AppCompatActivity {
    private TextInputLayout inputEmail, inputPassword;
    private Button btnLogin;
    private TextView ForgotPassword, createNewAccount;
    ProgressDialog mProgressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.inputEmail);
        inputPassword = findViewById(R.id.inputPassword);
        btnLogin = findViewById(R.id.btnLogin);
        ForgotPassword = findViewById(R.id.forgotPassword);
        createNewAccount = findViewById(R.id.createNewAccount);
        mProgressBar = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();

        createNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AttemptLogin();
            }
        });
    }

    private void AttemptLogin() {
        String email = inputEmail.getEditText().getText().toString();
        String password = inputPassword.getEditText().getText().toString();

        if(email.isEmpty() || !email.contains("@")){
            showError(inputEmail, "This is not a valid email address");
        }else if(password.isEmpty() || password.length() < 8){
            showError(inputPassword, "A valid password must contain 8 letters or numbers");
        }else{
            mProgressBar.setTitle("Login");
            mProgressBar.setMessage("Verifying your credentials...");
            mProgressBar.setCanceledOnTouchOutside(false);
            mProgressBar.show();

            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        mProgressBar.dismiss();
                        Toast.makeText(LoginActivity.this, "Successfully Logged In.", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, SetupActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }else
                    {
                        mProgressBar.dismiss();
                        Toast.makeText(LoginActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
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