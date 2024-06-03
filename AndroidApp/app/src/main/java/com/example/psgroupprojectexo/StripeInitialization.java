package com.example.psgroupprojectexo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.stripe.android.PaymentConfiguration;

public class StripeInitialization extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PaymentConfiguration.init(
                getApplicationContext(),
                getString(R.string.stripe_publishable_key) // replace with your actual key
        );
    }
}