package com.example.myappwaqf;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    ProgressBar progressSplash;
    int progressStatus = 0;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        setContentView(R.layout.activity_splash);

        progressSplash = findViewById(R.id.progressSplash);

        // تحريك شريط التحميل
        new Thread(() -> {
            while (progressStatus < 100) {
                progressStatus += 2;
                handler.post(() ->
                        progressSplash.setProgress(progressStatus));
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            // بعد اكتمال التحميل روح للرئيسية
            handler.post(() -> {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            });
        }).start();
    }
}