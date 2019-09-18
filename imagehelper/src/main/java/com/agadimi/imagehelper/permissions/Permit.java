package com.agadimi.imagehelper.permissions;

import android.content.Context;
import android.widget.Toast;

public class Permit
{
    public void showToast(Context context)
    {
        Toast.makeText(context, "Toast from dependency", Toast.LENGTH_SHORT).show();
    }
}
