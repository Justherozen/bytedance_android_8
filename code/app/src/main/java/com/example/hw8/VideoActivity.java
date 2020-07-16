package com.example.hw8;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import android.view.View;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.hw8.utils.Utils;

import static androidx.core.app.ActivityCompat.startActivityForResult;

public class VideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private static final int REQUEST_VIDEO_CAPTURE = 1;

    private static final int REQUEST_EXTERNAL_CAMERA = 101;
    String[] permissions = new String[] {
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        videoView = findViewById(R.id.img);
    }
    public void checkpermission_2(View view){
        if (Utils.isPermissionsReady(this, permissions)) {
            openVideoRecordApp();
        } else {
        //todo 权限检查
        Utils.reuqestPermissions(this, permissions, REQUEST_EXTERNAL_CAMERA);
    }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            Uri videoUri = intent.getData();
            videoView.setVideoURI(videoUri);
            videoView.start();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_CAMERA: {
                if (Utils.isPermissionsReady(this, permissions)) {
                    openVideoRecordApp();
                }
                break;
            }
        }
    }

    private void openVideoRecordApp() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if(takeVideoIntent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }
}
