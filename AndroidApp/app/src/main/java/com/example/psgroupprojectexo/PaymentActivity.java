package com.example.psgroupprojectexo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

import com.stripe.android.paymentsheet.*;
import com.stripe.android.PaymentConfiguration;
import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.github.kittinunf.fuel.core.Handler;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity {

    private static final String TAG = "PaymentActivity";
    private PaymentSheet paymentSheet;
    private String paymentClientSecret;
    private PaymentSheet.CustomerConfiguration customerConfig;
    private Button payBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        paymentSheet = new PaymentSheet(this, this::onPaymentSheetResult);

        String url = "https://us-central1-ps-groupproject-exo.cloudfunctions.net/helloWorld";

        Fuel.INSTANCE.post(url, null).responseString(new Handler<String>() {
            @Override
            public void success(String s) {
                try {
                    final JSONObject result = new JSONObject(s);
                    customerConfig = new PaymentSheet.CustomerConfiguration(
                            result.getString("customer"),
                            result.getString("ephemeralKey")
                    );
                    paymentClientSecret = result.getString("paymentIntent");
                    PaymentConfiguration.init(getApplicationContext(), result.getString("publishableKey"));

                    Log.d(TAG, "Customer ID: " + customerConfig.getId());
                    Log.d(TAG, "Ephemeral Key: " + customerConfig.getEphemeralKeySecret());
                    Log.d(TAG, "Payment Intent: " + paymentClientSecret);
                    Log.d(TAG, "Publishable Key: " + result.getString("publishableKey"));

                    runOnUiThread(() -> presentPaymentSheet());

                } catch (JSONException e) {
                    Toast.makeText(PaymentActivity.this, "Error in JSON parsing", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "JSON Parsing Error", e);
                }
            }

            @Override
            public void failure(@NonNull FuelError fuelError) {
                Toast.makeText(PaymentActivity.this, "Failed to fetch payment details", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "Fuel Error", fuelError);
            }
        });

    }

    private void presentPaymentSheet() {
        if (paymentClientSecret != null && customerConfig != null) {
            final PaymentSheet.Configuration configuration = new PaymentSheet.Configuration.Builder("HealthyApp")
                    .customer(customerConfig)
                    .build();
            paymentSheet.presentWithPaymentIntent(paymentClientSecret, configuration);
        } else {
            Toast.makeText(PaymentActivity.this, "Payment configuration is not ready", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Payment configuration is not ready");
        }
    }

    private void onPaymentSheetResult(final PaymentSheetResult paymentSheetResult) {
        if (paymentSheetResult instanceof PaymentSheetResult.Canceled) {
            Toast.makeText(PaymentActivity.this, "Payment Cancelled", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Payment Cancelled");
        } else if (paymentSheetResult instanceof PaymentSheetResult.Failed) {
            Toast.makeText(PaymentActivity.this, "Payment Failed", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Payment Failed", ((PaymentSheetResult.Failed) paymentSheetResult).getError());
        } else if (paymentSheetResult instanceof PaymentSheetResult.Completed) {
            Toast.makeText(PaymentActivity.this, "Payment Successful", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "Payment Successful");

            Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
            intent.putExtra("paymentSuccess", true);
            Log.d(TAG, "Extra boolean has been put");
            startActivity(intent);
            finish();
        }
    }
}
