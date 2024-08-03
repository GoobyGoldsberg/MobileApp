package com.example.psgroupprojectexo;

import android.app.Person;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;

public class GPDetailsActivity extends AppCompatActivity {

    private EditText gpNameField, gpPracticeNameField, gpAddressField, gpPhoneNumberField, userEmailField;
    private Button saveBtn, deleteBtn, backBtn;
    private FloatingActionButton floatingActionButton;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gp_details);

        // Initialize Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("gp_details");
        } else {
            Toast.makeText(this, "No authenticated user found", Toast.LENGTH_SHORT).show();
            return;
        }

        // Initialize UI components
        gpNameField = findViewById(R.id.gp_name_field);
        gpPracticeNameField = findViewById(R.id.gp_practice_name_field);
        gpAddressField = findViewById(R.id.gp_address_field);
        gpPhoneNumberField = findViewById(R.id.gp_phone_number_field);
        saveBtn = findViewById(R.id.saveBtn);
        deleteBtn = findViewById(R.id.deleteBtn);
        floatingActionButton = findViewById(R.id.floatingActionButton);
        backBtn = findViewById(R.id.backBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GPDetailsActivity.this, PersonalDetailsActivity.class);
                startActivity(intent);
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggleSaveDeleteButtons();
            }
        });

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGPDetails();
            }
        });

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteGPDetails();
            }
        });

        // Load GP details if they exist
        loadGPDetails();
    }

    private void toggleSaveDeleteButtons() {
        if (saveBtn.getVisibility() == View.INVISIBLE && deleteBtn.getVisibility() == View.INVISIBLE) {
            saveBtn.setVisibility(View.VISIBLE);
            deleteBtn.setVisibility(View.VISIBLE);
        } else {
            saveBtn.setVisibility(View.INVISIBLE);
            deleteBtn.setVisibility(View.INVISIBLE);
        }
    }

    private void loadGPDetails() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                        Object value = childSnapshot.getValue();
                        if (value instanceof Map) {
                            GPDetails gpDetails = childSnapshot.getValue(GPDetails.class);
                            if (gpDetails != null) {
                                gpNameField.setText(gpDetails.getName());
                                gpPracticeNameField.setText(gpDetails.getPracticeName());
                                gpAddressField.setText(gpDetails.getAddress());
                                gpPhoneNumberField.setText(gpDetails.getPhoneNumber());
                                setFormEditable(false);
                            }
                        }
                    }
                } else {
                    setFormEditable(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GPDetailsActivity.this, "Failed to load GP details.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setFormEditable(boolean isEditable) {
        gpNameField.setEnabled(isEditable);
        gpPracticeNameField.setEnabled(isEditable);
        gpAddressField.setEnabled(isEditable);
        gpPhoneNumberField.setEnabled(isEditable);
    }

    private void saveGPDetails() {
        String gpName = gpNameField.getText().toString().trim();
        String gpPracticeName = gpPracticeNameField.getText().toString().trim();
        String gpAddress = gpAddressField.getText().toString().trim();
        String gpPhoneNumber = gpPhoneNumberField.getText().toString().trim();

        if (!TextUtils.isEmpty(gpName) && !TextUtils.isEmpty(gpPracticeName) && !TextUtils.isEmpty(gpAddress) && !TextUtils.isEmpty(gpPhoneNumber)) {
            String id = databaseReference.push().getKey();
            GPDetails gpDetails = new GPDetails(id, gpName, gpPracticeName, gpAddress, gpPhoneNumber);
            databaseReference.setValue(gpDetails);
            Toast.makeText(this, "GP Details Saved", Toast.LENGTH_SHORT).show();
            setFormEditable(false);
        } else {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteGPDetails() {
        databaseReference.removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                clearFormFields();
                setFormEditable(true);
                Toast.makeText(GPDetailsActivity.this, "GP Details Deleted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(GPDetailsActivity.this, "Failed to delete GP details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFormFields() {
        gpNameField.setText("");
        gpPracticeNameField.setText("");
        gpAddressField.setText("");
        gpPhoneNumberField.setText("");
        userEmailField.setText("");
    }
}
