package com.agadimi.imagehelper.ui.photopicker;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.agadimi.imagehelper.R;
import com.agadimi.imagehelper.utils.FileUtils;
import com.agadimi.imagehelper.utils.Logger;

import java.io.File;
import java.io.IOException;

public class ActivityPickHandler extends AppCompatActivity
{
    private int action;
    private String cameraFilePath;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_handler);
        Logger.d("pick handler launched");

        try
        {
            action = getIntent().getExtras().getInt("action");
        }
        catch (Exception e)
        {
            finish();
        }

        if (action == PhotoPicker.PICK_FROM_CAMERA)
        {
            launchCamera();
        }
        else if (action == PhotoPicker.PICK_FROM_GALLERY)
        {
            launchGallery();
        }
    }

    private void launchCamera()
    {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null)
        {
            // Create the File where the photo should go
            File photoFile = null;
            try
            {
                photoFile = FileUtils.createImageFile(this);
                cameraFilePath = photoFile.getAbsolutePath();
            }
            catch (IOException ex)
            {
                // Error occurred while creating the File
                returnFailureResult();
            }
            // Continue only if the File was successfully created
            if (photoFile != null)
            {
                Uri photoURI = FileProvider.getUriForFile(this,
                        getApplication().getPackageName() + ".fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 2);
            }
        }
        else
        {
            returnFailureResult();
        }
    }

    private void launchGallery()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED)
        {
            // Permission is not granted
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 3);

            return;
        }


        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, 1);
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent();
        intent.putExtra("message", "Canceled");
        setResult(PickResult.RESULT_CANCELED, intent);
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && null != data)
        {
            //gallery
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            returnSuccessResult(picturePath);
            return;
        }
        else if (requestCode == 2 && resultCode == RESULT_OK)
        {
            //camera
            returnSuccessResult(cameraFilePath);
            return;
        }
        else if (resultCode == RESULT_CANCELED)
        {
            onBackPressed();
            return;
        }


        returnFailureResult();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 3)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                launchGallery();
            }
            return;
        }

        onBackPressed();
    }

    private void returnSuccessResult(String filePath)
    {
        Intent intent = new Intent();
        intent.putExtra("data", filePath);
        intent.putExtra("message", "Successful");
        setResult(PickResult.RESULT_OK, intent);
        finish();
    }

    private void returnFailureResult()
    {
        Intent intent = new Intent();
        intent.putExtra("message", "Failed");
        setResult(PickResult.RESULT_FAILED, intent);
        finish();
    }
}
