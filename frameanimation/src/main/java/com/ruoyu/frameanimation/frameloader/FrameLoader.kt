package com.ruoyu.frameanimation.frameloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.ruoyu.frameanimation.Frame
import com.ruoyu.frameanimation.FrameAnimation

/**
 * Created by liuruoyu on 2018/9/22.
 */

open abstract class FrameLoader(context: Context) {
    val mContext: Context
    var mFrameIndex: Int
    var mNextFrame: Frame?
    var mReuseBitmap: Bitmap?
    val mOptions: BitmapFactory.Options
    var mMaxRepeatCount = FrameAnimation.INFINITE
    var mCurrentRepeatCount = 0

    init {
        mContext = context
        mFrameIndex = 0
        mNextFrame = null
        mReuseBitmap = null
        mOptions = BitmapFactory.Options()
        mOptions.inSampleSize = 1
        mOptions.inMutable = true
    }

    fun nextFrame(): Frame {
        var duration = frameDuration(mFrameIndex)
        var bitmap: Bitmap = loadBitmap()
        var frameSize = frameSize()

        if (mNextFrame == null) {
            mNextFrame = Frame(duration, bitmap)
        } else {
            mNextFrame?.mBitmap = bitmap
            mNextFrame?.mDuration = duration
        }
        if (mFrameIndex == frameSize-1) {
            ++mCurrentRepeatCount
        }

        mReuseBitmap = bitmap
        mFrameIndex = ++mFrameIndex % frameSize

        return mNextFrame!!
    }

    fun setRepeatCount(repeatCount:Int) {
        mMaxRepeatCount = repeatCount
    }

    abstract fun frameDuration(index: Int): Long
    abstract fun loadBitmap(): Bitmap
    abstract fun frameSize(): Int

    fun animationEnd(): Boolean {
        return mCurrentRepeatCount >= mMaxRepeatCount
    }

    //需在nextframe之前调用
    fun animationStart(): Boolean {
        return mCurrentRepeatCount==0 && mFrameIndex==1;
    }
}
