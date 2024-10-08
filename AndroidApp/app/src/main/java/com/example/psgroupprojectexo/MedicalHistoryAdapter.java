package com.example.psgroupprojectexo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MedicalHistoryAdapter extends RecyclerView.Adapter<MedicalHistoryAdapter.MedicalHistoryViewHolder> {

    private final List<MedicalHistoryItem> medicalHistoryItems;
    private boolean isClickable = true;

    public MedicalHistoryAdapter(List<MedicalHistoryItem> medicalHistoryItems) {
        this.medicalHistoryItems = medicalHistoryItems;
    }

    public void setClickable(boolean clickable) {
        isClickable = clickable;
    }

    @NonNull
    @Override
    public MedicalHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_medical_history, parent, false);
        return new MedicalHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicalHistoryViewHolder holder, int position) {
        MedicalHistoryItem item = medicalHistoryItems.get(position);
        holder.bind(item, isClickable);
    }

    @Override
    public int getItemCount() {
        return medicalHistoryItems.size();
    }

    public List<MedicalHistoryItem> getMedicalHistoryItems() {
        return medicalHistoryItems;
    }

    static class MedicalHistoryViewHolder extends RecyclerView.ViewHolder {
        CheckBox smoking;
        CheckBox obesity;
        CheckBox familyHistory;
        CheckBox highCholesterol;
        CheckBox diabetes;
        CheckBox highBloodPressure;
        CheckBox physicalInactivity;
        CheckBox poorDiet;

        MedicalHistoryViewHolder(View itemView) {
            super(itemView);
            smoking = itemView.findViewById(R.id.smoking);
            obesity = itemView.findViewById(R.id.obesity);
            familyHistory = itemView.findViewById(R.id.family_history);
            highCholesterol = itemView.findViewById(R.id.high_cholesterol);
            diabetes = itemView.findViewById(R.id.diabetes);
            highBloodPressure = itemView.findViewById(R.id.high_blood_pressure);
            physicalInactivity = itemView.findViewById(R.id.physical_inactivity);
            poorDiet = itemView.findViewById(R.id.poor_diet);
        }

        void bind(MedicalHistoryItem item, boolean isClickable) {
            smoking.setChecked(item.isSmoking());
            obesity.setChecked(item.isObesity());
            familyHistory.setChecked(item.isFamilyHistory());
            highCholesterol.setChecked(item.isHighCholesterol());
            diabetes.setChecked(item.isDiabetes());
            highBloodPressure.setChecked(item.isHighBloodPressure());
            physicalInactivity.setChecked(item.isPhysicalInactivity());
            poorDiet.setChecked(item.isPoorDiet());

            smoking.setEnabled(isClickable);
            obesity.setEnabled(isClickable);
            familyHistory.setEnabled(isClickable);
            highCholesterol.setEnabled(isClickable);
            diabetes.setEnabled(isClickable);
            highBloodPressure.setEnabled(isClickable);
            physicalInactivity.setEnabled(isClickable);
            poorDiet.setEnabled(isClickable);

            smoking.setOnCheckedChangeListener((buttonView, isChecked) -> item.setSmoking(isChecked));
            obesity.setOnCheckedChangeListener((buttonView, isChecked) -> item.setObesity(isChecked));
            familyHistory.setOnCheckedChangeListener((buttonView, isChecked) -> item.setFamilyHistory(isChecked));
            highCholesterol.setOnCheckedChangeListener((buttonView, isChecked) -> item.setHighCholesterol(isChecked));
            diabetes.setOnCheckedChangeListener((buttonView, isChecked) -> item.setDiabetes(isChecked));
            highBloodPressure.setOnCheckedChangeListener((buttonView, isChecked) -> item.setHighBloodPressure(isChecked));
            physicalInactivity.setOnCheckedChangeListener((buttonView, isChecked) -> item.setPhysicalInactivity(isChecked));
            poorDiet.setOnCheckedChangeListener((buttonView, isChecked) -> item.setPoorDiet(isChecked));
        }
    }
}
