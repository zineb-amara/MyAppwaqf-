package com.example.myappwaqf;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    WaqfAdapter adapter;
    WaqfDatabase db;
    TextView tvTotalAmount, tvTotalDonors, tvTotalProjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_main);

        db = WaqfDatabase.getInstance(this);

        recyclerView    = findViewById(R.id.recyclerView);
        tvTotalAmount   = findViewById(R.id.tvTotalAmount);
        tvTotalDonors   = findViewById(R.id.tvTotalDonors);
        tvTotalProjects = findViewById(R.id.tvTotalProjects);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        LinearLayout navHistory = findViewById(R.id.navHistory);
        navHistory.setOnClickListener(v -> {
            startActivity(new Intent(this, HistoryActivity.class));
        });

        LinearLayout navProfile = findViewById(R.id.navProfile);
        navProfile.setOnClickListener(v -> {
            startActivity(new Intent(this, ProfileActivity.class));
        });

        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        Executors.newSingleThreadExecutor().execute(() -> {

            List<WaqfEntity> allList = db.waqfDao().getAllWaqf();
            if (allList.isEmpty()) {
                insertSampleData();
            }

            // جيب غير المشاريع النشطة (collectedAmount < targetAmount)
            List<WaqfEntity> activeList = db.waqfDao().getActiveWaqf();

            int totalAmount = 0;
            int totalDonors = 0;
            for (WaqfEntity w : activeList) {
                totalAmount += w.getCollectedAmount();
                totalDonors += w.getDonorsCount();
            }

            final List<WaqfEntity> finalList  = activeList;
            final int finalAmount = totalAmount;
            final int finalDonors = totalDonors;

            runOnUiThread(() -> {
                tvTotalAmount.setText(String.format("%,d دج", finalAmount));
                tvTotalDonors.setText(String.valueOf(finalDonors));
                tvTotalProjects.setText(String.valueOf(finalList.size()));

                if (adapter == null) {
                    adapter = new WaqfAdapter(finalList, waqf -> {
                        Intent intent = new Intent(this, DetailActivity.class);
                        intent.putExtra("waqf_id", waqf.getId());
                        startActivity(intent);
                    });
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.updateList(finalList);
                }
            });
        });
    }

    private void insertSampleData() {
        WaqfEntity w1 = new WaqfEntity(
                "💧", "بئر عادية", "تمنراست", "150,000 دج", 150000, 75000);
        w1.setDonorsCount(12);

        WaqfEntity w2 = new WaqfEntity(
                "☀️", "بئر بمضخة شمسية", "أدرار", "350,000 دج", 350000, 120000);
        w2.setDonorsCount(8);

        WaqfEntity w3 = new WaqfEntity(
                "🏗️", "بئر مع خزان", "إليزي", "500,000 دج", 500000, 500000);
        w3.setDonorsCount(35);

        WaqfEntity w4 = new WaqfEntity(
                "🔧", "بئر بمضخة يدوية", "بشار", "200,000 دج", 200000, 50000);
        w4.setDonorsCount(6);

        WaqfEntity w5 = new WaqfEntity(
                "⛏️", "بئر عميقة", "تندوف", "450,000 دج", 450000, 185000);
        w5.setDonorsCount(15);

        db.waqfDao().insert(w1);
        db.waqfDao().insert(w2);
        db.waqfDao().insert(w3);
        db.waqfDao().insert(w4);
        db.waqfDao().insert(w5);
    }
}