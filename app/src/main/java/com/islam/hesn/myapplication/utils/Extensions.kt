package com.islam.hesn.myapplication.utils

import android.content.Context
import android.util.DisplayMetrics


fun Context.convertDpToPixel(dp: Float): Float {
    return dp * (resources
        .displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
}