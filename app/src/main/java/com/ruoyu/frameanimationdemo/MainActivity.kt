package com.ruoyu.frameanimationdemo

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.ruoyu.frameanimation.FrameAnimation

/**
 * https://stackoverflow.com/questions/8692328/causing-outofmemoryerror-in-frame-by-frame-animation-in-android
 * 通过一帧一帧绘制的方式解决AnimationDrawable使用内存过大的问题
 * */
class MainActivity : AppCompatActivity() {

    var frameAnimation : FrameAnimation? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        assetTest()
//        resTest()
        xmlTest()
    }

    fun assetTest() {
        frameAnimation = FrameAnimation(findViewById(R.id.surface_view),"surprise",100)
        frameAnimation?.start()
    }

    fun resTest() {
        frameAnimation = FrameAnimation(findViewById(R.id.surface_view),R.array.surprise,100)
        frameAnimation?.start()
    }

    fun xmlTest() {
        frameAnimation = FrameAnimation(findViewById(R.id.surface_view),R.drawable.surprise)
        frameAnimation?.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        frameAnimation?.stop()
    }
}
