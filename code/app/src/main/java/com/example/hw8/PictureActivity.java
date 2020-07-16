package com.example.hw8;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.hw8.utils.Utils;

import java.io.File;

public class PictureActivity extends AppCompatActivity {

    private ImageView imageView;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_EXTERNAL_STORAGE = 101;
    private File imageFile;

    String[] permissions = new String[] {
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        imageView = findViewById(R.id.img);

    }
    public void checkpermission(View view){
        if (Utils.isPermissionsReady(PictureActivity.this,permissions)) {
            takePicture();
        } else {
            Utils.reuqestPermissions(PictureActivity.this,permissions,REQUEST_EXTERNAL_STORAGE);
        }
    }


    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageFile  = Utils.getOutputMediaFile(Utils.MEDIA_TYPE_IMAGE);
        if (imageFile != null){
            Uri fileUri;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ) {
                 fileUri = FileProvider.getUriForFile(this,"com.example.hw8", imageFile);
            }
            else {
                 fileUri = Uri.fromFile(imageFile);
            }
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
            startActivityForResult(takePictureIntent,REQUEST_IMAGE_CAPTURE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            setPic();
        }
    }

    private void setPic() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imageFile.getAbsolutePath(),options);
        int photoW = options.outWidth;
        int photoH = options.outHeight;
        int scaleFactor = Math.min(photoW/imageView.getWidth(),photoH/imageView.getHeight());
        options.inJustDecodeBounds = false;
        options.inSampleSize  = scaleFactor;
        options.inPurgeable = true;
        Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath(),options);
        bitmap = Utils.rotateImage(bitmap, imageFile.getAbsolutePath());
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_EXTERNAL_STORAGE: {
                if (Utils.isPermissionsReady(PictureActivity.this,permissions)){
                    takePicture();
                }
                break;
            }
        }
    }
}
