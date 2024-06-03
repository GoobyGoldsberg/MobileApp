package com.example.psgroupprojectexo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;



public class Login extends AppCompatActivity {
    TextInputEditText editTextEmail, editTextPassword;

    TextView forgotPassword;
    Button loginBtn;
    private CheckBox rememberMeCheckBox;

    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FacebookAuthManager facebookAuthManager;
    private GoogleAuthManager googleAuthManager;
    private EmailPasswordLoginManager emailAndPasswordAuthManager;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final String PREFS_NAME = "prefs";
    private static final String KEY_REMEMBER_ME = "remember_me";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";

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
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.emailField);
        editTextPassword = findViewById(R.id.passwordField);
        loginBtn = findViewById(R.id.loginButton);
        rememberMeCheckBox = findViewById(R.id.rememberMeBox);

        if (editTextEmail == null || editTextPassword == null || loginBtn == null) {
            Toast.makeText(this, "One or more views are not properly initialized", Toast.LENGTH_SHORT).show();
            return;
        }

        SwitchCompat pageSwitch = findViewById(R.id.pageSwitch);

        if (pageSwitch == null) {
            Toast.makeText(this, "SwitchCompat not found", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        forgotPassword = findViewById(R.id.forgot_password);


        ProgressDialog progressDialog = new ProgressDialog(Login.this);
        progressDialog.setTitle("Signing in account");
        progressDialog.setMessage("We are signing you in.");

        facebookAuthManager = new FacebookAuthManager();
        facebookAuthManager.initialize(this, mAuth, database);

        googleAuthManager = new GoogleAuthManager(this, mAuth, database);

        emailAndPasswordAuthManager = new EmailPasswordLoginManager(this, mAuth);

        pageSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                Intent intent = new Intent(Login.this, Register.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                pageSwitch.setChecked(false);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();
                emailAndPasswordAuthManager.signInWithEmailAndPassword(email, password);

                if (rememberMeCheckBox.isChecked()) {
                    editor.putBoolean(KEY_REMEMBER_ME, true);
                    editor.putString(KEY_EMAIL, email);
                    editor.putString(KEY_PASSWORD, password);
                    editor.apply();
                } else {
                    editor.putBoolean(KEY_REMEMBER_ME, false);
                    editor.putString(KEY_EMAIL, "");
                    editor.putString(KEY_PASSWORD, "");
                    editor.apply();
                }
            }
        });

        ImageView gmailImage = findViewById(R.id.gmailImage);
        ImageView facebookImage = findViewById(R.id.facebookImage);
        ImageView twitterImage = findViewById(R.id.twitterImage);

        if (gmailImage != null) {
            gmailImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    googleAuthManager.initGoogleLogin();
                }
            });
        }

        if (facebookImage != null) {
            facebookImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    facebookAuthManager.initFacebookLogin();
                }
            });
        }

        if (twitterImage != null) {
            twitterImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Login.this, TwitterAuthManager.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                    startActivity(intent);
                }
            });
        }

        if (sharedPreferences.getBoolean(KEY_REMEMBER_ME, false)) {
            editTextEmail.setText(sharedPreferences.getString(KEY_EMAIL, ""));
            editTextPassword.setText(sharedPreferences.getString(KEY_PASSWORD, ""));
            rememberMeCheckBox.setChecked(true);
        }

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotPasswordDialog();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        googleAuthManager.handleActivityResult(requestCode, resultCode, data);
        facebookAuthManager.getCallbackManager().onActivityResult(requestCode, resultCode, data);
    }

    private void showForgotPasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your email to reset password");

        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        builder.setView(input);

        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String email = input.getText().toString().trim();
                if (!email.isEmpty()) {
                    resetPassword(email);
                } else {
                    Toast.makeText(Login.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void resetPassword(String email) {
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Login.this, "Password reset email sent", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Login.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
