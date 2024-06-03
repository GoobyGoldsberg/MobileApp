package com.example.psgroupprojectexo;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity  {

    TextInputEditText editTextEmail, editTextPassword, editTextConfirmPassword;
    Button registerBtn;

    private FirebaseAuth mAuth;

    private FirebaseDatabase database;
    private FacebookAuthManager facebookAuthManager;
    private GoogleAuthManager googleAuthManager;

    private EmailPasswordRegisterManager emailPasswordRegisterManager;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextEmail = findViewById(R.id.emailField);
        editTextPassword = findViewById(R.id.passwordField);
        editTextConfirmPassword = findViewById(R.id.confirmPasswordField);
        registerBtn = findViewById(R.id.registerButton);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        SwitchCompat pageSwitch = findViewById(R.id.pageSwitch);

        ProgressDialog progressDialog = new ProgressDialog(Register.this);
        progressDialog.setTitle("Signing in account");
        progressDialog.setMessage("We are signing you in.");



        facebookAuthManager = new FacebookAuthManager();
        facebookAuthManager.initialize(this, mAuth, database);

        googleAuthManager = new GoogleAuthManager(this, mAuth, database);

        emailPasswordRegisterManager = new EmailPasswordRegisterManager(this, mAuth);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();
                emailPasswordRegisterManager.registerWithEmailAndPassword(email, password, confirmPassword);
            }
        });


        pageSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                Intent intent = new Intent(Register.this, Login.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        ImageView gmailImage = findViewById(R.id.gmailImage);
        ImageView facebookImage = findViewById(R.id.facebookImage);
        ImageView twitterImage = findViewById(R.id.twitterImage);

        gmailImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleAuthManager.initGoogleLogin();
            }
        });

        facebookImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facebookAuthManager.initFacebookLogin();
            }
        });

        twitterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Register.this, TwitterAuthManager.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googleAuthManager.handleActivityResult(requestCode, resultCode, data);
        facebookAuthManager.getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }

}
