package com.ruoyu.frameanimation

/**
 * Created by liuruoyu on 2018/10/1.
 */
interface FrameListener {
    fun onDrawFrame(index:Int) {}
    fun onDrawStart() {}
    fun onDrawEnd() {}
}