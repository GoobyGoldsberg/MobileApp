package com.example.psgroupprojectexo;

import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsuranceDetailsActivity extends AppCompatActivity {

    private LinearLayout formContainer;
    private List<InsuranceDetail> insuranceDetails;
    private Button saveButton, deleteButton;
    private View floatingActionButton;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_details);

        formContainer = findViewById(R.id.formContainer);
        saveButton = findViewById(R.id.saveBtn);
        deleteButton = findViewById(R.id.deleteBtn);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("insurance_details");
        } else {
            Toast.makeText(this, "No authenticated user found", Toast.LENGTH_SHORT).show();
            return;
        }

        initializeInsuranceDetails();
        populateForm();

        floatingActionButton.setOnClickListener(v -> {
            saveButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
        });

        saveButton.setOnClickListener(v -> saveDetails());
        deleteButton.setOnClickListener(v -> deleteDetails());
    }

    private void initializeInsuranceDetails() {
        insuranceDetails = new ArrayList<>();
        insuranceDetails.add(new InsuranceDetail("Insurance Company Name", "Enter insurance company name", InputType.TYPE_CLASS_TEXT));
        insuranceDetails.add(new InsuranceDetail("Policy Number", "Enter policy number", InputType.TYPE_CLASS_TEXT));
        insuranceDetails.add(new InsuranceDetail("Type of Insurance", "Enter type of insurance", InputType.TYPE_CLASS_TEXT));
        insuranceDetails.add(new InsuranceDetail("Coverage Details", "Enter coverage details", InputType.TYPE_CLASS_TEXT));
        insuranceDetails.add(new InsuranceDetail("Coverage Amount", "Enter coverage amount", InputType.TYPE_CLASS_NUMBER));
        insuranceDetails.add(new InsuranceDetail("Start Date", "Enter start date", InputType.TYPE_CLASS_DATETIME));
        insuranceDetails.add(new InsuranceDetail("End Date", "Enter end date", InputType.TYPE_CLASS_DATETIME));
        insuranceDetails.add(new InsuranceDetail("Premium Amount", "Enter premium amount", InputType.TYPE_CLASS_NUMBER));
        insuranceDetails.add(new InsuranceDetail("Payment Frequency", "Enter payment frequency", InputType.TYPE_CLASS_TEXT));
        insuranceDetails.add(new InsuranceDetail("Policy Holder Name", "Enter policy holder name", InputType.TYPE_CLASS_TEXT));
        insuranceDetails.add(new InsuranceDetail("Agent or Broker Name", "Enter agent or broker name", InputType.TYPE_CLASS_TEXT));
        insuranceDetails.add(new InsuranceDetail("Agent or Broker Contact Information", "Enter agent or broker contact information", InputType.TYPE_CLASS_PHONE));
        insuranceDetails.add(new InsuranceDetail("Claims Contact Information", "Enter claims contact information", InputType.TYPE_CLASS_PHONE));
    }

    private void populateForm() {
        for (InsuranceDetail detail : insuranceDetails) {
            View itemView = getLayoutInflater().inflate(R.layout.insurance_detail_item, formContainer, false);

            TextView label = itemView.findViewById(R.id.insurance_detail_label);
            EditText input = itemView.findViewById(R.id.insurance_detail_input);

            label.setText(detail.getLabel());
            input.setHint(detail.getHint());
            input.setInputType(detail.getInputType());
            formContainer.addView(itemView);
        }
    }

    private void saveDetails() {
        // Logic to save details to the database
        Map<String, String> detailsMap = new HashMap<>();
        for (int i = 0; i < formContainer.getChildCount(); i++) {
            View itemView = formContainer.getChildAt(i);
            EditText input = itemView.findViewById(R.id.insurance_detail_input);
            String key = insuranceDetails.get(i).getLabel();
            detailsMap.put(key, input.getText().toString());
            insuranceDetails.get(i).setValue(input.getText().toString());
        }

        databaseReference.setValue(detailsMap)
                .addOnSuccessListener(aVoid -> Toast.makeText(InsuranceDetailsActivity.this, "Details saved successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(InsuranceDetailsActivity.this, "Failed to save details: " + e.getMessage(), Toast.LENGTH_SHORT).show());

        // Make the form fields uneditable
        for (int i = 0; i < formContainer.getChildCount(); i++) {
            View itemView = formContainer.getChildAt(i);
            EditText input = itemView.findViewById(R.id.insurance_detail_input);
            input.setEnabled(false);
        }

        // Hide save and delete buttons
        saveButton.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);
    }

    private void deleteDetails() {
        // Logic to delete details from the database
        databaseReference.removeValue()
                .addOnSuccessListener(aVoid -> Toast.makeText(InsuranceDetailsActivity.this, "Details deleted successfully", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e -> Toast.makeText(InsuranceDetailsActivity.this, "Failed to delete details: " + e.getMessage(), Toast.LENGTH_SHORT).show());

        // Clear the input fields
        for (InsuranceDetail detail : insuranceDetails) {
            detail.setValue("");
        }

        for (int i = 0; i < formContainer.getChildCount(); i++) {
            View itemView = formContainer.getChildAt(i);
            EditText input = itemView.findViewById(R.id.insurance_detail_input);
            input.setText("");
            input.setEnabled(true); // Make fields editable again
        }

        // Hide save and delete buttons
        saveButton.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);
    }
}
