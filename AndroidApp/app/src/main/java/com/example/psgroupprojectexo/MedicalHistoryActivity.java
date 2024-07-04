package com.example.psgroupprojectexo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MedicalHistoryActivity extends AppCompatActivity {

    private final List<MedicalHistoryItem> medicalHistoryItems = new ArrayList<>();
    private MedicalHistoryAdapter adapter;
    private Button saveButton, deleteButton;
    private View floatingActionButton;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_history);

        saveButton = findViewById(R.id.saveBtn);
        deleteButton = findViewById(R.id.deleteBtn);
        floatingActionButton = findViewById(R.id.floatingActionButton);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid()).child("medical_history");
        } else {
            return;
        }

        initializeData();

        RecyclerView recyclerView = findViewById(R.id.medical_history_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MedicalHistoryAdapter(medicalHistoryItems);
        recyclerView.setAdapter(adapter);

        floatingActionButton.setOnClickListener(v -> {
            saveButton.setVisibility(View.VISIBLE);
            deleteButton.setVisibility(View.VISIBLE);
        });

        saveButton.setOnClickListener(v -> saveData());
        deleteButton.setOnClickListener(v -> deleteData());
    }

    private void initializeData() {
        medicalHistoryItems.add(new MedicalHistoryItem());
    }

    private void saveData() {
        List<MedicalHistoryItem> currentItems = adapter.getMedicalHistoryItems();
        for (MedicalHistoryItem item : currentItems) {
            databaseReference.setValue(item);
        }
        adapter.setClickable(false);
        adapter.notifyDataSetChanged();
        saveButton.setVisibility(View.INVISIBLE);
        deleteButton.setVisibility(View.INVISIBLE);
    }

    private void deleteData() {
        databaseReference.removeValue();
        for (MedicalHistoryItem item : medicalHistoryItems) {
            item.setSmoking(false);
            item.setObesity(false);
            item.setFamilyHistory(false);
            item.setHighCholesterol(false);
            item.setDiabetes(false);
            item.setHighBloodPressure(false);
            item.setPhysicalInactivity(false);
            item.setPoorDiet(false);
        }
        adapter.setClickable(true);
        adapter.notifyDataSetChanged();
        saveButton.setVisibility(View.GONE);
        deleteButton.setVisibility(View.GONE);
    }
}
