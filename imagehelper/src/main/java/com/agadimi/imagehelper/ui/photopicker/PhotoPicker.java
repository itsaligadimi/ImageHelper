package com.agadimi.imagehelper.ui.photopicker;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.view.Window;

import androidx.annotation.NonNull;


public class PhotoPicker extends Dialog
{
    public static final int PICK_FROM_GALLERY = 3001;
    public static final int PICK_FROM_CAMERA = 3002;

    private Activity activity;
    private PickResultListener pickResultListener;

    public PhotoPicker(@NonNull Activity context)
    {
        super(context, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar);
        this.activity = context;

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
    }

    public PhotoPicker(Activity context, int style)
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
            launchCamera();
            dismiss();
        });
    }

    public void setGalleryViewId(int galleryViewId)
    {
        findViewById(galleryViewId).setOnClickListener(v -> {
            launchGallery();
            dismiss();
        });
    }

    public void launchCamera()
    {
        Intent intent = new Intent(activity, ActivityPickHandler.class);
        intent.putExtra("action", PICK_FROM_CAMERA);
        activity.startActivityForResult(intent, PICK_FROM_CAMERA);
    }

    public void launchGallery()
    {
        Intent intent = new Intent(activity, ActivityPickHandler.class);
        intent.putExtra("action", PICK_FROM_GALLERY);
        activity.startActivityForResult(intent, PICK_FROM_GALLERY);
    }

    public void setPickResultListener(PickResultListener pickResultListener)
    {
        this.pickResultListener = pickResultListener;
    }

    public void handleResult(int requestCode, int resultCode, Intent data)
    {
        if (pickResultListener != null &&
                (requestCode == PICK_FROM_CAMERA || requestCode == PICK_FROM_GALLERY))
        {
            String filePath = "";
            String message = "";
            try
            {
                message = data.getExtras().getString("message", "");
                filePath = data.getExtras().getString("data", "");
            }
            catch (Exception e)
            {
            }

            pickResultListener.onResultReady(
                    new PickResult(
                            requestCode,
                            filePath,
                            message,
                            resultCode,
                            resultCode == PickResult.RESULT_OK
                    ));
        }
    }
}
