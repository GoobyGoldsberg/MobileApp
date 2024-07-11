package com.example.psgroupprojectexo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "SubscriptionPrefs";
    private static final String KEY_SUBSCRIPTION_STATUS = "subscriptionStatus";
    private static final String KEY_SUBSCRIPTION_EXPIRY = "subscriptionExpiry";
    private static final String TAG = "MainActivity";

    Button logoutBtn;
    CardView personalInfoCard, subscriptionCard, contactHealthcareCard, reviewTheAppCard, customerSupportCard;
    TextView subscriptionStatusTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        logoutBtn = findViewById(R.id.signOut);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                FirebaseAuth.getInstance().signOut();
                finish();
            }
        });

        personalInfoCard = findViewById(R.id.personalDetailsCardView);
        personalInfoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PersonalDetailsActivity.class);
                startActivity(intent);
            }
        });

        subscriptionCard = findViewById(R.id.subscriptionCardView);
        subscriptionStatusTextView = findViewById(R.id.subscriptionStatusTextView);

        subscriptionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
                startActivity(intent);
            }
        });

        contactHealthcareCard = findViewById(R.id.healthcareContactCardView);
        contactHealthcareCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ContactThirdParty.class);
                startActivity(intent);
            }
        });

        reviewTheAppCard = findViewById(R.id.reviewTheAppCardView);
        reviewTheAppCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ReviewApp.class);
                startActivity(intent);
            }
        });

        customerSupportCard = findViewById(R.id.customerSupportCardView);
        customerSupportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CustomerSupport.class);
                startActivity(intent);
            }
        });

        // Handle the intent that started the activity
        handleIntent(getIntent());
    }

    private void checkSubscriptionStatus() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean isSubscribed = prefs.getBoolean(KEY_SUBSCRIPTION_STATUS, false);
        long expiryTime = prefs.getLong(KEY_SUBSCRIPTION_EXPIRY, 0);

        if (isSubscribed && System.currentTimeMillis() < expiryTime) {
            setSubscriptionCardViewState(false);
            subscriptionStatusTextView.setVisibility(View.VISIBLE);
        } else {
            setSubscriptionCardViewState(true);
            subscriptionStatusTextView.setVisibility(View.GONE);
        }
    }

    private void setSubscriptionCardViewState(boolean isActive) {
        subscriptionCard.setClickable(isActive);
        subscriptionCard.setFocusable(isActive);
        subscriptionCard.setAlpha(isActive ? 1.0f : 0.3f);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (intent != null && intent.getBooleanExtra("paymentSuccess", false)) {
            Log.d(TAG, "onNewIntent or onCreate: Payment success flag detected");
            onPaymentSuccess();
        } else {
            Log.d(TAG, "onNewIntent or onCreate: Payment success flag not detected");
        }
    }

    public void onPaymentSuccess() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(KEY_SUBSCRIPTION_STATUS, true);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        editor.putLong(KEY_SUBSCRIPTION_EXPIRY, calendar.getTimeInMillis());
        editor.apply();

        checkSubscriptionStatus();
    }
}
