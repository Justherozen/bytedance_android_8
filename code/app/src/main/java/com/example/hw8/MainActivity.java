package com.example.hw8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private String[] permissions = new String[] {

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }
    public void startcamera(View view){
        Intent intent=new Intent(MainActivity.this, PictureActivity.class);
        startActivity(intent);
    }
    public void startvideo(View view){
        Intent intent=new Intent(MainActivity.this, VideoActivity.class);
        startActivity(intent);
    }

}
