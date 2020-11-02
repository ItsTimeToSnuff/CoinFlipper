package com.flipinc.coin.flipper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity {

    private static final String TAILS = "TAILS!";
    private static final String HEADS = "HEADS!";
    private static final int FLIP_DURATION = 200;
    private static final int TIMER_DELAY = FLIP_DURATION * 2;

    private ImageView frontView;
    private ImageView backView;
    private EasyFlipView easyFlipView;
    private final AtomicBoolean endFlip = new AtomicBoolean(false);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        easyFlipView = findViewById(R.id.cardFlipView);
        easyFlipView.setFlipDuration(FLIP_DURATION);
        easyFlipView.setFlipEnabled(true);
        easyFlipView.setFlipOnceEnabled(false);
        frontView = findViewById(R.id.coin_front_side);
        Glide.with(this).load(R.drawable.front_side).into(frontView);
        backView = findViewById(R.id.coin_back_side);
        Glide.with(this).load(R.drawable.back_side).into(backView);
        frontView.setOnClickListener(view -> startFlip());
        backView.setOnClickListener(view -> startFlip());
        easyFlipView.setOnFlipListener((easyFlipView, newCurrentSide) -> {
            if (endFlip.get()) {
                if (newCurrentSide.equals(EasyFlipView.FlipState.FRONT_SIDE)) {
                    showToast(HEADS);
                } else {
                    showToast(TAILS);
                }
            }
        });
    }

    private void showToast(String msg) {
        Toast toast = Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private void startFlip() {
        final long startFlip = System.currentTimeMillis();
        final Handler handler = new Handler();
        final int[] i = {0};
        Random random = new Random();
        long randomIdx = random.nextInt(7) + 3;
        backView.setClickable(false);
        frontView.setClickable(false);
        endFlip.set(false);
        for (; i[0] < randomIdx; i[0]++) {
            handler.postDelayed(() -> {
                easyFlipView.flipTheView();
                if (System.currentTimeMillis() - startFlip >= i[0] * TIMER_DELAY - (FLIP_DURATION + TIMER_DELAY)) {
                    endFlip.set(true);
                    frontView.setClickable(true);
                    backView.setClickable(true);
                }
            }, TIMER_DELAY * i[0]);
        }
    }
}