package com.agadimi.imagehelper.ui.photopicker;

import android.net.Uri;

import java.io.File;

public class PickResult
{
    public static final int RESULT_OK = 1;
    public static final int RESULT_CANCELED = 2;
    public static final int RESULT_FAILED = 3;

    private int action;
    private String filePath;
    private String message;
    private int status;
    private boolean isSuccessFull;

    public PickResult()
    {
    }

    public PickResult(int action, String filePath, String message, int status, boolean isSuccessFull)
    {
        this.action = action;
        this.filePath = filePath;
        this.message = message;
        this.status = status;
        this.isSuccessFull = isSuccessFull;
    }

    public int getAction()
    {
        return action;
    }

    public String getMessage()
    {
        return message;
    }

    public boolean isSuccessFull()
    {
        return isSuccessFull;
    }

    public int getStatus()
    {
        return status;
    }

    public String getFilePath()
    {
        return filePath;
    }

    public File getFile()
    {
        return new File(filePath);
    }

    public Uri getUri()
    {
        return Uri.fromFile(getFile());
    }
}
