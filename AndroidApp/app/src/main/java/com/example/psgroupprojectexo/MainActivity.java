package com.example.psgroupprojectexo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Flow;

public class MainActivity extends AppCompatActivity {

    Button logoutBtn;

    CardView personalInfoCard, subscriptionCard;

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

        subscriptionCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PaymentActivity.class);
                startActivity(intent);

            }
        });
    }
}