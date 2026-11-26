package com.example.myappwaqf;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ThankYouActivity extends AppCompatActivity {

    TextView tvThankName, tvThankPhone, tvThankProject;
    TextView tvThankAmount, tvThankPayment, tvThankReceipt;
    Button btnBackHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thank_you);

        tvThankName    = findViewById(R.id.tvThankName);
        tvThankPhone   = findViewById(R.id.tvThankPhone);
        tvThankProject = findViewById(R.id.tvThankProject);
        tvThankAmount  = findViewById(R.id.tvThankAmount);
        tvThankPayment = findViewById(R.id.tvThankPayment);
        tvThankReceipt = findViewById(R.id.tvThankReceipt);
        btnBackHome    = findViewById(R.id.btnBackHome);

        // استقبال البيانات
        String name    = getIntent().getStringExtra("name");
        String phone   = getIntent().getStringExtra("phone");
        String project = getIntent().getStringExtra("project");
        String amount  = getIntent().getStringExtra("amount");
        String payment = getIntent().getStringExtra("payment");
        String receipt = getIntent().getStringExtra("receipt");

        tvThankName.setText(name);
        tvThankPhone.setText(phone);
        tvThankProject.setText(project);
        tvThankAmount.setText(amount + " دج");
        tvThankPayment.setText(payment);
        tvThankReceipt.setText(receipt);

        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }
}