package com.example.myappwaqf;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddWaqfActivity extends AppCompatActivity {

    Spinner spinnerType, spinnerLocation;
    EditText etCost;
    Button btnSave;
    WaqfDatabase db;

    String[] types = {
            "💧 بئر عادية",
            "🔧 بئر بمضخة يدوية",
            "☀️ بئر بمضخة شمسية",
            "⛏️ بئر عميقة",
            "🏗️ بئر مع خزان",
            "🌊 بئر تحلية مياه"
    };

    String[] icons = {"💧", "🔧", "☀️", "⛏️", "🏗️", "🌊"};

    String[] locations = {
            "تمنراست", "أدرار", "إليزي", "تندوف",
            "بشار", "ورقلة", "غرداية", "الوادي",
            "خنشلة", "تبسة"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_waqf);

        db = WaqfDatabase.getInstance(this);

        spinnerType     = findViewById(R.id.spinnerType);
        spinnerLocation = findViewById(R.id.spinnerLocation);
        etCost          = findViewById(R.id.etCost);
        btnSave         = findViewById(R.id.btnSave);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, types);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerType.setAdapter(typeAdapter);

        ArrayAdapter<String> locationAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, locations);
        locationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerLocation.setAdapter(locationAdapter);

        btnSave.setOnClickListener(v -> {
            String location = (String) spinnerLocation.getSelectedItem();
            String cost = etCost.getText().toString().trim();
            int selectedIndex = spinnerType.getSelectedItemPosition();

            if (cost.isEmpty()) {
                Toast.makeText(this,
                        getString(R.string.error_amount),
                        Toast.LENGTH_SHORT).show();
                return;
            }

            WaqfEntity waqf = new WaqfEntity(
                    icons[selectedIndex],
                    types[selectedIndex].substring(3),
                    location,
                    cost + " دج",
                    0,
                    0
            );

            db.waqfDao().insert(waqf);
            Toast.makeText(this,
                    getString(R.string.success_save),
                    Toast.LENGTH_SHORT).show();
            finish();
        });
    }
}