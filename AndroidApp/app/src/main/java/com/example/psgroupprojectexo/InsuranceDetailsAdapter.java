package com.example.psgroupprojectexo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InsuranceDetailsAdapter extends RecyclerView.Adapter<InsuranceDetailsAdapter.InsuranceViewHolder> {

    private final List<InsuranceDetail> insuranceDetails;

    public InsuranceDetailsAdapter(List<InsuranceDetail> insuranceDetails) {
        this.insuranceDetails = insuranceDetails;
    }

    @NonNull
    @Override
    public InsuranceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.insurance_detail_item, parent, false);
        return new InsuranceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InsuranceViewHolder holder, int position) {
        InsuranceDetail detail = insuranceDetails.get(position);
        holder.label.setText(detail.getLabel());
        holder.input.setHint(detail.getHint());
        holder.input.setInputType(detail.getInputType());
    }

    @Override
    public int getItemCount() {
        return insuranceDetails.size();
    }

    public static class InsuranceViewHolder extends RecyclerView.ViewHolder {
        public TextView label;
        public EditText input;

        public InsuranceViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.insurance_detail_label);
            input = itemView.findViewById(R.id.insurance_detail_input);
        }
    }
}
