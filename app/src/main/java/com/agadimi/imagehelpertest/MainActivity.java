package com.agadimi.imagehelpertest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.agadimi.imagehelper.ui.photopicker.LightPicker;
import com.agadimi.imagehelper.ui.photopicker.PickResult;
import com.agadimi.imagehelper.ui.photopicker.PickResultListener;


public class MainActivity extends AppCompatActivity implements
        PickResultListener
{
    private ImageView imageView;
    private LightPicker lightPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lightPicker = new LightPicker(this);
        lightPicker.setPickResultListener(this);

        imageView = findViewById(R.id.image_view);
        setupListeners();
    }

    private void setupListeners()
    {
        findViewById(R.id.pick_photo_btn).setOnClickListener(v -> lightPicker.show());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        lightPicker.handleResult(requestCode, resultCode, data);
    }

    @Override
    public void onResultReady(PickResult pickResult)
    {
        Toast.makeText(this, pickResult.getMessage(), Toast.LENGTH_SHORT).show();
        if (pickResult.isSuccessFull())
        {
            imageView.setImageURI(pickResult.getUri());
        }
    }
}
