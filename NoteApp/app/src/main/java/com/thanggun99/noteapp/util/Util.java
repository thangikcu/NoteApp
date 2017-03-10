package com.thanggun99.noteapp.util;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;

import com.thanggun99.noteapp.App;

public class Util {
    public static String getStringByid(int id) {
        return App.getContext().getResources().getString(id);
    }

    public static String convertColorToHex(Drawable drawable) {

        return String.format("#%06x",  (((ColorDrawable)drawable).getColor() & 0x00FFFFFF));
    }
}
