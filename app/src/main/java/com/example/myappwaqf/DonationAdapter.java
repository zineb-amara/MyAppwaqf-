package com.example.myappwaqf;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DonationAdapter extends RecyclerView.Adapter<DonationAdapter.DonationViewHolder> {

    private List<DonationEntity> donationList;

    public DonationAdapter(List<DonationEntity> donationList) {
        this.donationList = donationList;
    }

    public void updateList(List<DonationEntity> newList) {
        this.donationList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DonationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_donation, parent, false);
        return new DonationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DonationViewHolder holder, int position) {
        DonationEntity d = donationList.get(position);

        holder.tvDonorName.setText("👤 " + d.getDonorName());
        holder.tvAmount.setText(String.format("%,d دج", d.getAmount()));
        holder.tvPayment.setText(d.getPaymentMethod());
        holder.tvReceipt.setText("🧾 " + d.getReceiptNumber());

        // تنسيق التاريخ
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault());
        holder.tvDate.setText(sdf.format(new Date(d.getDate())));
    }

    @Override
    public int getItemCount() {
        return donationList != null ? donationList.size() : 0;
    }

    static class DonationViewHolder extends RecyclerView.ViewHolder {
        TextView tvDonorName, tvAmount, tvPayment, tvReceipt, tvDate;

        DonationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDonorName = itemView.findViewById(R.id.tvDonorName);
            tvAmount    = itemView.findViewById(R.id.tvAmount);
            tvPayment   = itemView.findViewById(R.id.tvPayment);
            tvReceipt   = itemView.findViewById(R.id.tvReceipt);
            tvDate      = itemView.findViewById(R.id.tvDate);
        }
    }
}