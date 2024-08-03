package com.example.psgroupprojectexo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContactThirdParty extends AppCompatActivity {

    private TextView gpPhoneNumberTextView, insurancePhoneNumberTextView;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_third_party);

        gpPhoneNumberTextView = findViewById(R.id.gp_phone_number);
        insurancePhoneNumberTextView = findViewById(R.id.insurance_phone_number);

        Button callGpButton = findViewById(R.id.call_gp_button);
        Button callInsuranceButton = findViewById(R.id.call_insurance_button);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
            loadPhoneNumbers();
        } else {
            Toast.makeText(this, "No authenticated user found", Toast.LENGTH_SHORT).show();
        }

        callGpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gpPhoneNumber = gpPhoneNumberTextView.getText().toString();
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + gpPhoneNumber));
                startActivity(dialIntent);
            }
        });

        callInsuranceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String insurancePhoneNumber = insurancePhoneNumberTextView.getText().toString();
                Intent dialIntent = new Intent(Intent.ACTION_DIAL);
                dialIntent.setData(Uri.parse("tel:" + insurancePhoneNumber));
                startActivity(dialIntent);
            }
        });
    }

    private void loadPhoneNumbers() {
        databaseReference.child("gp_details").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    GPDetails gpDetails = snapshot.getValue(GPDetails.class);
                    if (gpDetails != null) {
                        gpPhoneNumberTextView.setText(gpDetails.getPhoneNumber());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ContactThirdParty.this, "Failed to load GP phone number.", Toast.LENGTH_SHORT).show();
            }
        });

        databaseReference.child("insurance_details").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    String insurancePhoneNumber = snapshot.child("Claims Contact Information").getValue(String.class);
                    if (insurancePhoneNumber != null) {
                        insurancePhoneNumberTextView.setText(insurancePhoneNumber);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ContactThirdParty.this, "Failed to load insurance phone number.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
