package com.example.psgroupprojectexo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReviewApp extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText commentEditText;
    private Button submitFeedbackButton;

    private FirebaseAuth auth;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_app);

        auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");

        ratingBar = findViewById(R.id.ratingBar);
        commentEditText = findViewById(R.id.commentEditText);
        submitFeedbackButton = findViewById(R.id.submitFeedbackButton);

        submitFeedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitFeedback();
            }
        });
    }

    private void submitFeedback() {
        float rating = ratingBar.getRating();
        String comment = commentEditText.getText().toString();

        if (auth.getCurrentUser() != null) {
            String userId = auth.getCurrentUser().getUid();
            DatabaseReference userRef = databaseReference.child(userId).child("feedback");

            Feedback feedback = new Feedback(rating, comment);
            userRef.setValue(feedback)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(ReviewApp.this, "Thank you for your feedback", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ReviewApp.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(ReviewApp.this, "Failed to submit feedback", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }

        ratingBar.setRating(0);
        commentEditText.setText("");
    }

    public static class Feedback {
        public float rating;
        public String comment;

        public Feedback() {
        }

        public Feedback(float rating, String comment) {
            this.rating = rating;
            this.comment = comment;
        }
    }
}
