package com.example.mytourtravel;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.mytourtravel.ui.main.AdminhFragment;

public class adminh extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.adminh_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, AdminhFragment.newInstance())
                    .commitNow();
        }
    }
}