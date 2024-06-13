package com.example.psgroupprojectexo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ReviewApp extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText commentEditText;
    private Button submitFeedbackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_app);

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

        // TODO Implement logic for saving the feedback, make the intent go back to mainActivity, and disable the cardview. Change the Toast to "thank you for feedback".
        Toast.makeText(this, "Rating: " + rating + "\nComment: " + comment, Toast.LENGTH_LONG).show();

        ratingBar.setRating(0);
        commentEditText.setText("");
    }
}
