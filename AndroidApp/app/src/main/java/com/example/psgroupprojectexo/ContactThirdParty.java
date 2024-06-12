package com.example.psgroupprojectexo;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ContactThirdParty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_third_party);

        TextView gpPhoneNumberTextView = findViewById(R.id.gp_phone_number);
        TextView insurancePhoneNumberTextView = findViewById(R.id.insurance_phone_number);

        Button callGpButton = findViewById(R.id.call_gp_button);
        Button callInsuranceButton = findViewById(R.id.call_insurance_button);

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
}
