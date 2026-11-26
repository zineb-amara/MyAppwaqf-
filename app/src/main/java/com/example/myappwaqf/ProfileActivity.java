package com.example.myappwaqf;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.concurrent.Executors;

public class ProfileActivity extends AppCompatActivity {

    TextView tvMyDonationsCount, tvMyTotalAmount, tvProfileEmpty;
    RecyclerView recyclerViewProfile;
    WaqfDatabase db;
    DonationAdapter adapter;
    Button btnGoHome, btnGoHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_profile);

        db = WaqfDatabase.getInstance(this);

        tvMyDonationsCount = findViewById(R.id.tvMyDonationsCount);
        tvMyTotalAmount    = findViewById(R.id.tvMyTotalAmount);
        tvProfileEmpty     = findViewById(R.id.tvProfileEmpty);
        recyclerViewProfile= findViewById(R.id.recyclerViewProfile);
        btnGoHome          = findViewById(R.id.btnGoHome);
        btnGoHistory       = findViewById(R.id.btnGoHistory);

        recyclerViewProfile.setLayoutManager(new LinearLayoutManager(this));

        btnGoHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        btnGoHistory.setOnClickListener(v -> {
            startActivity(new Intent(this, HistoryActivity.class));
        });

        loadData();
    }

    private void loadData() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<DonationEntity> donations = db.donationDao().getAllDonations();

            int total = 0;
            for (DonationEntity d : donations) {
                total += d.getAmount();
            }
            final int finalTotal = total;

            // نجيب آخر 3 تبرعات فقط
            List<DonationEntity> recent = donations.size() > 3
                    ? donations.subList(0, 3)
                    : donations;

            runOnUiThread(() -> {
                tvMyDonationsCount.setText(String.valueOf(donations.size()));
                tvMyTotalAmount.setText(String.format("%,d", finalTotal));

                if (donations.isEmpty()) {
                    tvProfileEmpty.setVisibility(View.VISIBLE);
                    recyclerViewProfile.setVisibility(View.GONE);
                } else {
                    tvProfileEmpty.setVisibility(View.GONE);
                    recyclerViewProfile.setVisibility(View.VISIBLE);
                    adapter = new DonationAdapter(recent);
                    recyclerViewProfile.setAdapter(adapter);
                }
            });
        });
    }
}