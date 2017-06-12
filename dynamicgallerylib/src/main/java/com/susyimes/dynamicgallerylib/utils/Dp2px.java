package com.susyimes.dynamicgallerylib.utils;

import android.content.Context;

/**
 * Created by susyimes on 2016/5/25.
 */
public class Dp2px {
    public static int UseDp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
