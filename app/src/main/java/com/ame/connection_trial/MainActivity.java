package com.ame.connection_trial;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //button 例化
        Button tcp_s = findViewById(R.id.button);
        Button tcp_c = findViewById(R.id.button2);
        //button 设置监听

        tcp_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //turn activity
                startActivities(new Intent[]{new Intent(MainActivity.this, SecActivity.class)});

            }
        });

        tcp_c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivities(new Intent[]{new Intent(MainActivity.this, ThrActivity.class)});
            }
        });
    }

}