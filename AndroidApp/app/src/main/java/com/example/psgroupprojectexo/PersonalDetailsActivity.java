package com.example.psgroupprojectexo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class PersonalDetailsActivity extends AppCompatActivity {

    private CardView gpInfoCard, insuranceInfoCard, medicalInfoCard;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_details_activity);

        gpInfoCard = findViewById(R.id.gpInformationCard);

        gpInfoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalDetailsActivity.this, GPDetailsActivity.class);
                startActivity(intent);
            }
        });

        insuranceInfoCard = findViewById(R.id.insuranceCompanyInfoCard);

        insuranceInfoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalDetailsActivity.this, InsuranceDetailsActivity.class);
                startActivity(intent);
            }
        });

        medicalInfoCard = findViewById(R.id.medicalHistoryCard);

        medicalInfoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalDetailsActivity.this, MedicalHistoryActivity.class);
                startActivity(intent);
            }
        });

    }
}
