package com.ruoyu.frameanimation.frameloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.ruoyu.frameanimation.Frame
import java.io.File

/**
 * Created by liuruoyu on 2018/9/22.
 */
class AssetFrameLoader(context: Context, assetDir:String, duration:Long) : FrameLoader(context) {
    val mDuration : Long
    val mFilePaths : Array<String>
    init {
        mDuration = duration
        mFilePaths = mContext.assets.list(assetDir)
        for (i in 0 until  mFilePaths.size) {
            var name : String = mFilePaths[i]
            mFilePaths[i] = assetDir + File.separator + name
        }
    }

    override fun nextFrame(): Frame {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

        var bitmap:Bitmap

        if (mReuseBitmap != null) {
            mOptions.inBitmap = mReuseBitmap
        }
        bitmap = BitmapFactory.decodeStream(mContext.assets.open(mFilePaths[mFrameIndex]),null,mOptions)

        if (mNextFrame == null) {
            mNextFrame = Frame(mDuration, bitmap)
        } else {
            mNextFrame?.mDuration = mDuration
            mNextFrame?.mBitmap = bitmap
        }

        mFrameIndex = (++mFrameIndex) % mFilePaths.size

        return mNextFrame!!
    }
}