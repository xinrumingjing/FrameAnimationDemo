package com.ruoyu.frameanimation.frameloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.ruoyu.frameanimation.Frame

/**
 * Created by liuruoyu on 2018/9/22.
 */

class ResFrameLoader(context: Context, resIds: IntArray, durations: LongArray) : FrameLoader(context) {
    val mResIds: IntArray
    val mDurations: LongArray

    init {
        mResIds = resIds
        mDurations = durations
        if (mResIds.size != mDurations.size) {
            throw IllegalArgumentException("resource size must equal durations size")
        }
    }

    override fun frameDuration(index: Int): Long {
        return mDurations[index]
    }

    override fun loadBitmap(): Bitmap {
        if (mReuseBitmap != null) {
            mOptions.inBitmap = mReuseBitmap
        }
        return BitmapFactory.decodeResource(mContext.resources, mResIds[mFrameIndex], mOptions)
    }

    override fun frameSize(): Int {
        return mResIds.size
    }
}
