package com.example.psgroupprojectexo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class InsuranceDetailsAdapter extends RecyclerView.Adapter<InsuranceDetailsAdapter.ViewHolder> {

    private List<InsuranceDetail> insuranceDetails;

    public InsuranceDetailsAdapter(List<InsuranceDetail> insuranceDetails) {
        this.insuranceDetails = insuranceDetails;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.insurance_detail_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        InsuranceDetail insuranceDetail = insuranceDetails.get(position);
        holder.label.setText(insuranceDetail.getLabel());
        holder.input.setHint(insuranceDetail.getHint());
        holder.input.setInputType(insuranceDetail.getInputType());

        if (!holder.input.isEnabled()) {
            holder.input.setText(insuranceDetail.getValue());
        } else {
            holder.input.setText("");  // Clear the text if it's editable
        }

        // Update the value when the text changes
        holder.input.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                insuranceDetail.setValue(s.toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return insuranceDetails.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView label;
        EditText input;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            label = itemView.findViewById(R.id.insurance_detail_label);
            input = itemView.findViewById(R.id.insurance_detail_input);
        }
    }
}
