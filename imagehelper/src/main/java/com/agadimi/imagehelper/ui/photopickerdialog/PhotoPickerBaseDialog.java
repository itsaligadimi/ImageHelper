package com.agadimi.imagehelper.ui.photopickerdialog;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.Toast;

import androidx.annotation.NonNull;


public class PhotoPickerBaseDialog extends Dialog
{
    private Activity activity;

    public PhotoPickerBaseDialog(@NonNull Activity context)
    {
        super(context, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        this.activity = context;

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public PhotoPickerBaseDialog(Activity context, int style)
    {
        super(context, style);
        this.activity = context;

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public Activity getActivity()
    {
        return activity;
    }

    public void setCameraViewId(int cameraViewId)
    {
        findViewById(cameraViewId).setOnClickListener(v -> {
            Toast.makeText(activity, "pick photo with camera", Toast.LENGTH_SHORT).show();
            dismiss();
        });
    }

    public void setGalleryViewId(int galleryViewId)
    {
        findViewById(galleryViewId).setOnClickListener(v -> {
            Toast.makeText(activity, "pick photo from gallery", Toast.LENGTH_SHORT).show();
            dismiss();
        });
    }
}
