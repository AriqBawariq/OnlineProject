package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class activity_splash_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_IMMERSIVE
                        // Set the content to appear under the system bars so that the
                        // content doesn't resize when the system bars hide and show.
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        // Hide the nav bar and status bar
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp = getSharedPreferences("RENTALSEPEDA", Context.MODE_PRIVATE);
                String userId = sp.getString("USERID","");
                String role = sp.getString("ROLEUSER","");

                if (userId.equalsIgnoreCase("")) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    if(role.equalsIgnoreCase("2")){
                        //JIKA ADMIN INTENT NNG ACTIVITY ADMIN
                        Intent intent1 = new Intent(getApplicationContext(), Admin.class);
                        startActivity(intent1);
                        finish();
                    }
                    else {
                        //JIKA ADMIN INTENT NNG ACTIVITY USER
                        Intent intent1 = new Intent(getApplicationContext(), Dashboard.class);
                        startActivity(intent1);
                        finish();
                    }

                }
            }
        }, 2000);
    }
}