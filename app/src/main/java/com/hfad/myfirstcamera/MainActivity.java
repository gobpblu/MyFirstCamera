package com.hfad.myfirstcamera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    ImageView photoImageView;
    Bitmap imageBitmap;
    Button cameraButton;
    private final int REQUEST_CODE_CAMERA = 100;
    private final int REQUEST_IMAGE_CAPTURE = 1;
    private String cameraPermission;
    String currentPhotoPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        photoImageView = (ImageView) findViewById(R.id.camera_photo);
        cameraButton = (Button) findViewById(R.id.camera_button);
        cameraPermission = Manifest.permission.CAMERA;


        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showCamera();
            }
        }
    }

    private void checkPermission() {
        String permission = Manifest.permission.CAMERA;
        int resultCode = ContextCompat.checkSelfPermission(this, permission);
        if (resultCode == PackageManager.PERMISSION_GRANTED) {
            showCamera();
        } else {
            requestCameraPermission();
        }
    }

    private void showCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        /*if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating file
            }
            if (photoFile != null) {
                System.out.println("Я тут");
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.hfad.myfirstcamera",
                        photoFile);
               takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

            }
        }*/

    }

    private void requestCameraPermission() {
        String[] permissions = {cameraPermission};
        ActivityCompat.requestPermissions(this, permissions, REQUEST_CODE_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       /*if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
           try {
               imageBitmap = (Bitmap) MediaStore.Images.Media.getBitmap(
                       this.getContentResolver(),
                       Uri.parse(currentPhotoPath));
               photoImageView.setImageBitmap(imageBitmap);
           } catch (IOException e) {
               e.printStackTrace();
           }
        }*/
        Bitmap photo = (Bitmap) data.getExtras().get("data");
        photoImageView.setImageBitmap(photo);
    }

    /*private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  *//* prefix *//*
                ".jpg",         *//* suffix *//*
                storageDir      *//* directory *//*
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }*/
}