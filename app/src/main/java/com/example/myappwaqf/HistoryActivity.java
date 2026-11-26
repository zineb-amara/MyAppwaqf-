package com.example.myappwaqf;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import java.util.concurrent.Executors;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView    recyclerView;
    WaqfDatabase    db;
    TextView        tvEmpty, tvTotalDonations;
    DonationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("سجل التبرعات");
        }

        setContentView(R.layout.activity_history);

        db           = WaqfDatabase.getInstance(this);
        recyclerView = findViewById(R.id.recyclerViewHistory);
        tvEmpty      = findViewById(R.id.tvEmpty);
        tvTotalDonations = findViewById(R.id.tvTotalDonations);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadHistory();
    }

    private void loadHistory() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<DonationEntity> donations = db.donationDao().getAllDonations();

            // حساب الإجمالي
            int total = 0;
            for (DonationEntity d : donations) {
                total += d.getAmount();
            }
            final int finalTotal = total;

            runOnUiThread(() -> {
                tvTotalDonations.setText(
                        String.format("إجمالي التبرعات: %,d دج", finalTotal));

                if (donations.isEmpty()) {
                    tvEmpty.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                } else {
                    tvEmpty.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter = new DonationAdapter(donations);
                    recyclerView.setAdapter(adapter);
                }
            });
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}