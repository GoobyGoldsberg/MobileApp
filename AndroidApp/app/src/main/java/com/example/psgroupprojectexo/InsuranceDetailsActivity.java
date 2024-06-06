package com.example.psgroupprojectexo;

import android.os.Bundle;
import android.text.InputType;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class InsuranceDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private InsuranceDetailsAdapter adapter;
    private List<InsuranceDetail> insuranceDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance_details);

        recyclerView = findViewById(R.id.insurance_form_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

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
        insuranceDetails.add(new InsuranceDetail("Agent/Broker Name", "Enter agent/broker name", InputType.TYPE_CLASS_TEXT));
        insuranceDetails.add(new InsuranceDetail("Agent/Broker Contact Information", "Enter agent/broker contact information", InputType.TYPE_CLASS_PHONE));
        insuranceDetails.add(new InsuranceDetail("Claims Contact Information", "Enter claims contact information", InputType.TYPE_CLASS_PHONE));

        adapter = new InsuranceDetailsAdapter(insuranceDetails);
        recyclerView.setAdapter(adapter);
    }
}
