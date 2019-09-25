package com.agadimi.imagehelper.ui.photopicker;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.agadimi.imagehelper.R;

public class LightPicker extends PhotoPicker
{
    public LightPicker(@NonNull Activity context)
    {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_photo_picker);

        setCameraViewId(R.id.camera_btn);
        setGalleryViewId(R.id.gallery_btn);
    }

    public static void launch(Activity activity)
    {
        new LightPicker(activity).show();
    }
}
