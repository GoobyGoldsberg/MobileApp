package com.example.psgroupprojectexo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class PersonalDetailsActivity extends AppCompatActivity {

    private CardView insuranceInfoCard;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_details_activity);

        insuranceInfoCard = findViewById(R.id.insuranceCompanyInfoCard);

        insuranceInfoCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalDetailsActivity.this, InsuranceDetailsActivity.class);
                startActivity(intent);
            }
        });

    }
}
