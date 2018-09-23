package com.ruoyu.frameanimation

import android.graphics.Bitmap

/**
 * Created by liuruoyu on 2018/9/22.
 */

class Frame {
    var mDuration : Long
    var mBitmap : Bitmap

    constructor(duration: Long,bitmap: Bitmap) {
        mDuration = duration
        mBitmap = bitmap
    }
}
