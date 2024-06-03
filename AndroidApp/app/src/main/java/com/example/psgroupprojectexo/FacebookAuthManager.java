package com.example.psgroupprojectexo;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Collections;

public class FacebookAuthManager extends AppCompatActivity {

    private Activity activity;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private final CallbackManager callbackManager;

    // Public default constructor
    public FacebookAuthManager() {
        this.callbackManager = CallbackManager.Factory.create();
    }

    // Setter method for Activity and Firebase instances
    public void initialize(Activity activity, FirebaseAuth mAuth, FirebaseDatabase database) {
        this.activity = activity;
        this.mAuth = mAuth;
        this.database = database;
    }

    public CallbackManager getCallbackManager() {
        return callbackManager;
    }

    public void initFacebookLogin() {
        if (activity == null || mAuth == null || database == null) {
            throw new IllegalStateException("FacebookAuthManager is not initialized properly.");
        }

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired()) {
            handleFacebookAccessToken(accessToken);
        } else {
            LoginManager.getInstance().logInWithReadPermissions(activity, Collections.singletonList("public_profile"));
            LoginManager.getInstance().registerCallback(callbackManager,
                    new FacebookCallback<LoginResult>() {
                        @Override
                        public void onSuccess(LoginResult loginResult) {
                            handleFacebookAccessToken(loginResult.getAccessToken());
                        }

                        @Override
                        public void onCancel() {
                            Toast.makeText(activity, "Login Cancelled", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(@NonNull FacebookException e) {
                            Toast.makeText(activity, "Login Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            saveUserData(user);
                            Intent intent = new Intent(activity, MainActivity.class);
                            activity.startActivity(intent);
                            activity.finish();
                        } else {
                            Toast.makeText(activity, "Authentication Failed.", Toast.LENGTH_SHORT).show();
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
