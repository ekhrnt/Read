package com.ardan.readartikel.control;

import android.util.Log;

import com.ardan.readartikel.BuildConfig;

/**
 * Created by ardan on 15/03/2016.
 */
public class Utils
{
    public static void TRACE(String tag, String msg)
    {
        if (BuildConfig.DEBUG)
        {
            Log.d(tag, msg);
        }
    }
}