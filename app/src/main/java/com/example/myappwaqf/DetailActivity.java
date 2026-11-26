package com.example.myappwaqf;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import java.util.concurrent.Executors;

public class DetailActivity extends AppCompatActivity {

    TextView  tvDetailIcon, tvDetailType, tvDetailLocation;
    TextView  tvDetailPercent, tvDetailDonors;
    TextView  tvCollected, tvTarget;
    ProgressBar progressDetail;
    EditText  etName, etPhone, etDonationAmount;
    Spinner   spinnerPayment;
    Button    btnDonate;
    WaqfDatabase db;
    WaqfEntity   waqf;
    int waqfId;

    String[] paymentMethods = {
            "💳 CIB",
            "📱 Baridimob",
            "🏦 تحويل بنكي",
            "💵 نقداً"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("تفاصيل المشروع");
        }

        db     = WaqfDatabase.getInstance(this);
        waqfId = getIntent().getIntExtra("waqf_id", -1);

        tvDetailIcon    = findViewById(R.id.tvDetailIcon);
        tvDetailType    = findViewById(R.id.tvDetailType);
        tvDetailLocation= findViewById(R.id.tvDetailLocation);
        tvDetailPercent = findViewById(R.id.tvDetailPercent);
        tvDetailDonors  = findViewById(R.id.tvDetailDonors);
        tvCollected     = findViewById(R.id.tvCollected);
        tvTarget        = findViewById(R.id.tvTarget);
        progressDetail  = findViewById(R.id.progressDetail);
        etName          = findViewById(R.id.etName);
        etPhone         = findViewById(R.id.etPhone);
        etDonationAmount= findViewById(R.id.etDonationAmount);
        spinnerPayment  = findViewById(R.id.spinnerPayment);
        btnDonate       = findViewById(R.id.btnDonate);

        ArrayAdapter<String> paymentAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, paymentMethods);
        paymentAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinnerPayment.setAdapter(paymentAdapter);

        loadWaqf();

        btnDonate.setOnClickListener(v -> donate());
    }

    private void loadWaqf() {
        Executors.newSingleThreadExecutor().execute(() -> {
            waqf = db.waqfDao().getWaqfById(waqfId);
            runOnUiThread(() -> {
                if (waqf != null) updateUI();
            });
        });
    }

    private void updateUI() {
        tvDetailIcon.setText(waqf.getIcon());
        tvDetailType.setText(waqf.getType());
        tvDetailLocation.setText("📍 " + waqf.getLocation());

        int target    = waqf.getTargetAmount();
        int collected = waqf.getCollectedAmount();
        int percent   = (target > 0) ? (collected * 100 / target) : 0;

        progressDetail.setProgress(percent);
        tvDetailPercent.setText(percent + "% مموّل");
        tvDetailDonors.setText("👤 " + waqf.getDonorsCount() + " متبرع");
        tvCollected.setText(String.format("%,d دج", collected));
        tvTarget.setText(String.format("%,d دج", target));
    }

    private void donate() {
        String name      = etName.getText().toString().trim();
        String phone     = etPhone.getText().toString().trim();
        String amountStr = etDonationAmount.getText().toString().trim();
        String payment   = (String) spinnerPayment.getSelectedItem();

        if (name.isEmpty()) {
            Toast.makeText(this, "⚠️ أدخل اسمك", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone.isEmpty() || phone.length() < 10) {
            Toast.makeText(this, "⚠️ أدخل رقم هاتف صحيح", Toast.LENGTH_SHORT).show();
            return;
        }
        if (amountStr.isEmpty()) {
            Toast.makeText(this, "⚠️ أدخل مبلغ التبرع", Toast.LENGTH_SHORT).show();
            return;
        }

        int amount = Integer.parseInt(amountStr);
        if (amount <= 0) {
            Toast.makeText(this, "⚠️ المبلغ يجب أن يكون أكبر من 0",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        String receipt = "WAQF-" + System.currentTimeMillis();

        Executors.newSingleThreadExecutor().execute(() -> {

            // 1. تحديث المشروع في waqf_table
            db.waqfDao().addDonation(waqfId, amount);

            // 2. حفظ التبرع في donation_table
            DonationEntity donation = new DonationEntity(
                    waqfId, name, phone, amount, payment, receipt);
            db.donationDao().insert(donation);

            runOnUiThread(() -> {
                Intent intent = new Intent(this, ThankYouActivity.class);
                intent.putExtra("name",    name);
                intent.putExtra("phone",   phone);
                intent.putExtra("project", waqf.getType() + " - " + waqf.getLocation());
                intent.putExtra("amount",  amountStr);
                intent.putExtra("payment", payment);
                intent.putExtra("receipt", receipt);
                startActivity(intent);
                finish();
            });
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}