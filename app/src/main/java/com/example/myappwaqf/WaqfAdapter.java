package com.example.myappwaqf;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class WaqfAdapter extends RecyclerView.Adapter<WaqfAdapter.WaqfViewHolder> {

    private List<WaqfEntity> waqfList;
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(WaqfEntity waqf);
    }

    public WaqfAdapter(List<WaqfEntity> waqfList, OnItemClickListener listener) {
        this.waqfList = waqfList;
        this.listener = listener;
    }

    public void updateList(List<WaqfEntity> newList) {
        this.waqfList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WaqfViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_waqf, parent, false);
        return new WaqfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WaqfViewHolder holder, int position) {
        WaqfEntity waqf = waqfList.get(position);

        holder.tvIcon.setText(waqf.getIcon());
        holder.tvType.setText(waqf.getType());
        holder.tvLocation.setText("📍 " + waqf.getLocation());
        holder.tvCost.setText(waqf.getCost());

        // حساب نسبة التمويل
        int target = waqf.getTargetAmount();
        int collected = waqf.getCollectedAmount();
        int percent = (target > 0) ? (collected * 100 / target) : 0;

        holder.progressBar.setProgress(percent);
        holder.tvPercent.setText(percent + "% مموّل");
        holder.tvDonors.setText("👤 " + waqf.getDonorsCount() + " متبرع");

        holder.itemView.setOnClickListener(v -> listener.onItemClick(waqf));
    }

    @Override
    public int getItemCount() {
        return waqfList != null ? waqfList.size() : 0;
    }

    static class WaqfViewHolder extends RecyclerView.ViewHolder {
        TextView tvIcon, tvType, tvLocation, tvCost, tvPercent, tvDonors;
        ProgressBar progressBar;

        WaqfViewHolder(@NonNull View itemView) {
            super(itemView);
            tvIcon = itemView.findViewById(R.id.tvIcon);
            tvType = itemView.findViewById(R.id.tvType);
            tvLocation = itemView.findViewById(R.id.tvLocation);
            tvCost = itemView.findViewById(R.id.tvCost);
            tvPercent = itemView.findViewById(R.id.tvPercent);
            tvDonors = itemView.findViewById(R.id.tvDonors);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}