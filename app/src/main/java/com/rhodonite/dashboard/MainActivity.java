package com.rhodonite.dashboard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final DashBoard d = (DashBoard) findViewById(R.id.dash);
        findViewById(R.id.rand).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int max = 180;
                int min = 1;
                Random random = new Random();
                int p = random.nextInt(max) % (max - min + 1) + min;
                d.cgangePer(p / 180f);
            }
        });

        findViewById(R.id.retu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d.cgangePer(0);
            }
        });

    }
}