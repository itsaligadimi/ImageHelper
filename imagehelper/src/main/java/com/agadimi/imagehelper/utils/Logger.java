package com.agadimi.imagehelper.utils;

import android.util.Log;

import java.util.Objects;

public class Logger
{
    public static void d(String s)
    {
        Log.d("IH", s);
    }

    public static void d(String s, Objects... args)
    {
        Log.d("IH", String.format(s, args));
    }
}
