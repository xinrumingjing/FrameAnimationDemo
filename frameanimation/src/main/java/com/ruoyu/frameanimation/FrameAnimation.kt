package com.ruoyu.frameanimation

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Handler
import android.os.HandlerThread
import android.view.SurfaceHolder
import android.view.SurfaceView
import com.ruoyu.frameanimation.frameloader.AssetFrameLoader
import com.ruoyu.frameanimation.frameloader.FrameLoader
import com.ruoyu.frameanimation.frameloader.ResFrameLoader
import com.ruoyu.frameanimation.frameloader.XmlFrameLoader

/**
 * Created by liuruoyu on 2018/9/22.
 */

class FrameAnimation : SurfaceHolder.Callback {

    private val mFrameLoader : FrameLoader
    private val mView: SurfaceView
    private var mStarted : Boolean = false
    private var mCreated : Boolean = false
    private val mTask : Runnable
    private val mIoHandler : Handler

    init {

        var thread = HandlerThread("FrameAnimation")
        thread.start()
        mIoHandler = Handler(thread.looper)
        mTask = object : Runnable {
            override fun run() {
                doDrawFrame()
            }
        }
    }

    constructor(view:SurfaceView, resTypeArray:Int, duration: Long) {
        mView = view
        var typedArray = view.context.resources.obtainTypedArray(resTypeArray)
        var length = typedArray.length()
        var resIds = IntArray(length)
        for (i in 0 until length) {
            resIds[i] = typedArray.getResourceId(i,0)
        }
        typedArray.recycle()
        mFrameLoader = ResFrameLoader(view.context, resIds, LongArray(resIds.size, { i: Int -> duration }))
        view.holder.addCallback(this)
    }

    constructor(view: SurfaceView,resIds:IntArray, duration: Long) {
        mView = view
        mFrameLoader = ResFrameLoader(view.context, resIds, LongArray(resIds.size, { i: Int -> duration }))
        view.holder.addCallback(this)
    }

    constructor(view: SurfaceView,resIds: IntArray, durations: LongArray) {
        mView = view
        mFrameLoader = ResFrameLoader(view.context, resIds, durations)
        view.holder.addCallback(this)
    }

    constructor(view: SurfaceView, assetDir:String, duration: Long) {
        mView = view
        mFrameLoader = AssetFrameLoader(view.context, assetDir, duration)
        view.holder.addCallback(this)
    }

    constructor(view: SurfaceView, xmlDrawableId:Int) {
        mView = view
        mFrameLoader = XmlFrameLoader(view.context, xmlDrawableId)
        view.holder.addCallback(this)
    }

    fun doDrawFrame() {
        var frame = mFrameLoader?.nextFrame()
        var canvas = mView.holder.lockCanvas()
        if (canvas != null ) {
            canvas.drawColor(Color.TRANSPARENT,PorterDuff.Mode.CLEAR)
            canvas.drawBitmap(frame.mBitmap,0F,0F,null)
            mView.holder.unlockCanvasAndPost(canvas)
        }
        mIoHandler.postDelayed(mTask,frame.mDuration)
    }

    fun start() {
        mStarted = true
        realStart()
    }

    fun stop() {
        mStarted = false
        mIoHandler.removeCallbacks(mTask)
    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
        mCreated = true
        realStart()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        mCreated = false
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        mCreated = true
        realStart()
    }

    private fun realStart() {
        if (mStarted && mCreated) {
            mIoHandler.post(mTask)
        }
    }

}
