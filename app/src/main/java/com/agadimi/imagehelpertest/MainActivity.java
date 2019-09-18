package com.agadimi.imagehelpertest;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.agadimi.imagehelper.ui.photopickerdialog.PhotoPicker;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PhotoPicker.launch(this);
    }
}
