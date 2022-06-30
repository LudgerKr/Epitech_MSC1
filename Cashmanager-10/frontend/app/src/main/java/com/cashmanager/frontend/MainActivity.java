package com.cashmanager.frontend;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;

import com.cashmanager.frontend.activite.ui.login.LoginActivity;
import com.cashmanager.frontend.asynchrone.RegisterAsynchTask;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler pHandler = new Handler();

    /**
     * Barre de progression
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final ActionBar actionBar = getActionBar();

        new Thread(() -> {
            while (progressStatus < 100) {
                progressStatus++;
                android.os.SystemClock.sleep(20);
                pHandler.post(() -> progressBar.setProgress(progressStatus));
            }

            if (progressStatus == 100) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        }).start();
    }
}