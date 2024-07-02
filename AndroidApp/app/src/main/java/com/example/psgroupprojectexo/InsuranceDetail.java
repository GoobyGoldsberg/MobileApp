package com.example.psgroupprojectexo;

public class InsuranceDetail {
    private String label;
    private String hint;
    private int inputType;
    private String value;

    // Default constructor required for calls to DataSnapshot.getValue(InsuranceDetail.class)
    public InsuranceDetail() {
    }

    public InsuranceDetail(String label, String hint, int inputType) {
        this.label = label;
        this.hint = hint;
        this.inputType = inputType;
        this.value = ""; // Initialize value to an empty string
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
