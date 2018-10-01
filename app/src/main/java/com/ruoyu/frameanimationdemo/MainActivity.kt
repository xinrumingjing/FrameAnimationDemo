package com.ruoyu.frameanimationdemo

import android.graphics.drawable.AnimationDrawable
import android.nfc.Tag
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.ruoyu.frameanimation.FrameAnimation
import com.ruoyu.frameanimation.FrameListener

/**
 * https://stackoverflow.com/questions/8692328/causing-outofmemoryerror-in-frame-by-frame-animation-in-android
 * 通过一帧一帧绘制的方式解决AnimationDrawable使用内存过大的问题
 * */
class MainActivity : AppCompatActivity() {

    var frameAnimation : FrameAnimation? = null
    val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        assetTest()
//        resTest()
//        xmlTest()

    }

    fun assetTest() {
        frameAnimation = FrameAnimation(findViewById(R.id.surface_view),"surprise",100)
        frameAnimation?.setRepeatCount(1)
        frameAnimation?.mFrameListener = object : FrameListener {
            override fun onDrawStart() {
                super.onDrawStart()
                Log.d(TAG,"onDrawStart")
            }

            override fun onDrawFrame(index: Int) {
                super.onDrawFrame(index)
                Log.d(TAG,"onDrawFrame:" + index)
            }

            override fun onDrawEnd() {
                super.onDrawEnd()
                Log.d(TAG,"onDrawEnd")
            }
        }
        frameAnimation?.start()
    }

    fun resTest() {
        frameAnimation = FrameAnimation(findViewById(R.id.surface_view),R.array.surprise,100)
        frameAnimation?.setRepeatCount(1)
        frameAnimation?.start()
    }

    fun xmlTest() {
        frameAnimation = FrameAnimation(findViewById(R.id.surface_view),R.drawable.surprise)
        frameAnimation?.setRepeatCount(1)
        frameAnimation?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        frameAnimation?.stop()
    }
}
