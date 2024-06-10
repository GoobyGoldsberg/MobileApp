package com.example.psgroupprojectexo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MedicalHistoryActivity extends AppCompatActivity {

    private final List<MedicalHistoryItem> medicalHistoryItems = new ArrayList<>();
    private MedicalHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);

        initializeData();

        RecyclerView recyclerView = findViewById(R.id.medical_history_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MedicalHistoryAdapter(medicalHistoryItems);
        recyclerView.setAdapter(adapter);

    }

    private void initializeData() {
        medicalHistoryItems.add(new MedicalHistoryItem());
    }

    private void saveData() {
        for (MedicalHistoryItem item : medicalHistoryItems) {
            // Process and save data to the database
        }
    }
}
