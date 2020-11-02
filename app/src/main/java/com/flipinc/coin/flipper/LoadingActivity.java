package com.flipinc.coin.flipper;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

import java.util.Timer;
import java.util.TimerTask;

public class LoadingActivity extends AppCompatActivity {

    private boolean isActivityRunning = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        ImageView loading = findViewById(R.id.loading);
        Glide.with(this).load(R.drawable.loading).into(loading);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (isActivityRunning) {
                    startActivity(new Intent(LoadingActivity.this, MainActivity.class));
                }
                finish();
            }
        }, 3000);
    }

    @Override
    protected void onPause() {
        isActivityRunning = false;
        super.onPause();
    }

    @Override
    protected void onResume() {
        isActivityRunning = true;
        super.onResume();
    }
}
