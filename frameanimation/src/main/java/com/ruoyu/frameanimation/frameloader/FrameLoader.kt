package com.ruoyu.frameanimation.frameloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.ruoyu.frameanimation.Frame

/**
 * Created by liuruoyu on 2018/9/22.
 */

open abstract class FrameLoader(context: Context) {
    val mContext: Context
    var mFrameIndex: Int
    var mNextFrame: Frame?
    var mReuseBitmap:Bitmap?
    val mOptions : BitmapFactory.Options

    init {
        mContext = context
        mFrameIndex = 0
        mNextFrame = null
        mReuseBitmap = null
        mOptions = BitmapFactory.Options()
        mOptions.inSampleSize = 1
        mOptions.inMutable = true
    }

    abstract fun nextFrame(): Frame
}
