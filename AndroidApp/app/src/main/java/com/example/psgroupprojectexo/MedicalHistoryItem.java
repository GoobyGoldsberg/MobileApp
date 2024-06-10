package com.example.psgroupprojectexo;

public class MedicalHistoryItem {
    private boolean smoking;
    private boolean obesity;
    private boolean familyHistory;
    private boolean highCholesterol;
    private boolean diabetes;
    private boolean highBloodPressure;
    private boolean physicalInactivity;
    private boolean poorDiet;

    // Constructor, getters, and setters

    public MedicalHistoryItem() {}

    public boolean isSmoking() {
        return smoking;
    }

    public void setSmoking(boolean smoking) {
        this.smoking = smoking;
    }

    public boolean isObesity() {
        return obesity;
    }

    public void setObesity(boolean obesity) {
        this.obesity = obesity;
    }

    public boolean isFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(boolean familyHistory) {
        this.familyHistory = familyHistory;
    }

    public boolean isHighCholesterol() {
        return highCholesterol;
    }

    public void setHighCholesterol(boolean highCholesterol) {
        this.highCholesterol = highCholesterol;
    }

    public boolean isDiabetes() {
        return diabetes;
    }

    public void setDiabetes(boolean diabetes) {
        this.diabetes = diabetes;
    }

    public boolean isHighBloodPressure() {
        return highBloodPressure;
    }

    public void setHighBloodPressure(boolean highBloodPressure) {
        this.highBloodPressure = highBloodPressure;
    }

    public boolean isPhysicalInactivity() {
        return physicalInactivity;
    }

    public void setPhysicalInactivity(boolean physicalInactivity) {
        this.physicalInactivity = physicalInactivity;
    }

    public boolean isPoorDiet() {
        return poorDiet;
    }

    public void setPoorDiet(boolean poorDiet) {
        this.poorDiet = poorDiet;
    }
}
