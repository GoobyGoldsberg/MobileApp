package com.example.psgroupprojectexo;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class GoogleAuthManager extends AppCompatActivity {

    private Activity activity;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private GoogleSignInClient mGoogleSignInClient;
    private final int RC_SIGN_IN = 213;

    public GoogleAuthManager() {
        // Default constructor required for instantiation
    }

    public GoogleAuthManager(Activity activity, FirebaseAuth mAuth, FirebaseDatabase database) {
        this.activity = activity;
        this.mAuth = mAuth;
        this.database = database;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    public void initGoogleLogin() {
        Intent intent = mGoogleSignInClient.getSignInIntent();
        activity.startActivityForResult(intent, RC_SIGN_IN);
    }

    public void handleActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                if (account != null) {
                    googleFirebaseAuth(account.getIdToken());
                }
            } catch (ApiException e) {
                Toast.makeText(activity, "Google Sign-In failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void googleFirebaseAuth(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
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
