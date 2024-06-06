package com.example.psgroupprojectexo;

public class InsuranceDetail {
    private final String label;
    private final String hint;
    private final int inputType;

    public InsuranceDetail(String label, String hint, int inputType) {
        this.label = label;
        this.hint = hint;
        this.inputType = inputType;
    }

    public String getLabel() {
        return label;
    }

    public String getHint() {
        return hint;
    }

    public int getInputType() {
        return inputType;
    }
}
