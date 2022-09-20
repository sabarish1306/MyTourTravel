package com.example.mytourtravel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.mytourtravel.ApiClient.Commons;

public class userdash extends AppCompatActivity {
    Button tserch,iserch,lout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userdash);
        tserch=(Button)findViewById(R.id.tsbtn);
        iserch=(Button)findViewById(R.id.isbtn);
        lout=(Button)findViewById(R.id.lbtn);



        tserch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(userdash.this, u_main.class);

                startActivity(i);

            }
        });
        iserch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(userdash.this, imgsrch.class);

                startActivity(i);

            }
        });

        lout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(userdash.this, MainActivity.class);

                startActivity(i);

            }
        });
    }

    @Override
    public void onDestroy()
    {
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onDestroy();
    }
}