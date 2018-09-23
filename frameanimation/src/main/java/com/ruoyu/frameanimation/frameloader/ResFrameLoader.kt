package com.ruoyu.frameanimation.frameloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.ruoyu.frameanimation.Frame

/**
 * Created by liuruoyu on 2018/9/22.
 */

class ResFrameLoader(context: Context,resIds: IntArray, durations: LongArray) : FrameLoader(context) {
    val mResIds : IntArray
    val mDurations : LongArray

    init {
        mResIds = resIds
        mDurations = durations
    }

    override fun nextFrame(): Frame {
        var duration = mDurations[mFrameIndex]
        var bitmap:Bitmap

        if (mReuseBitmap != null) {
            mOptions.inBitmap = mReuseBitmap
        }
        bitmap = BitmapFactory.decodeResource(mContext.resources,mResIds[mFrameIndex],mOptions)
        if (mNextFrame == null) {
            mNextFrame = Frame(duration, bitmap)
        } else {
            mNextFrame?.mBitmap = bitmap
            mNextFrame?.mDuration = duration
        }

        mReuseBitmap = bitmap
        mFrameIndex = ++mFrameIndex % mResIds.size

        return mNextFrame!!
    }
}
