package com.example.psgroupprojectexo;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class EmailPasswordRegisterManager extends AppCompatActivity {

    private Activity activity;
    private FirebaseAuth mAuth;

    private FirebaseDatabase database;



    public EmailPasswordRegisterManager (Activity activity, FirebaseAuth mAuth) {
        this.activity = activity;
        this.mAuth = mAuth;
        this.database = FirebaseDatabase.getInstance();

    }

    public void registerWithEmailAndPassword(String email, String password, String confirmPassword) {
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(activity, "Email cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(activity, "Password cannot be empty", Toast.LENGTH_LONG).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(activity, "Passwords must match", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            saveUserData(user);
                            Toast.makeText(activity, "Login successful.", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(activity, MainActivity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(activity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void saveUserData(FirebaseUser user) {
        if (user != null) {
            Users users = new Users();
            users.setUserId(user.getUid());
            users.setName(user.getDisplayName());
            if (user.getPhotoUrl() != null) {
                users.setProfile(user.getPhotoUrl().toString());
            }
            database.getReference().child("Users").child(user.getUid()).setValue(users);
        }
    }
}
