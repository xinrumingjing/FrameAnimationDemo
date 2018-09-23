package com.ruoyu.frameanimation.frameloader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.ruoyu.frameanimation.Frame
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import java.io.IOException


/**
 * Created by liuruoyu on 2018/9/22.
 */

class XmlFrameLoader(context: Context, xmlDrawableId:Int) : FrameLoader(context) {

    val mXmlDrawableId:Int
    private var mFrameDrawables: ArrayList<FrameDrawable> = ArrayList()

    private class FrameDrawable(drawableId:Int,duration:Long) {
        val mDrawableId : Int
        val mDuration : Long

        init {
            mDrawableId = drawableId
            mDuration = duration
        }
    }

    init {
        mXmlDrawableId = xmlDrawableId
        loadFrameDrawables()
    }

    override fun nextFrame(): Frame {

        var frameDrawable = mFrameDrawables[mFrameIndex]
        var bitmap:Bitmap

        if (mReuseBitmap != null) {
            mOptions.inBitmap = mReuseBitmap
        }
        bitmap = BitmapFactory.decodeResource(mContext.resources,frameDrawable.mDrawableId,mOptions)

        if (mNextFrame == null) {
            mNextFrame = Frame(frameDrawable.mDuration, bitmap)
        } else {
            mNextFrame?.mBitmap = bitmap
            mNextFrame?.mDuration = frameDrawable.mDuration
        }
        mReuseBitmap = bitmap
        ++mFrameIndex
        mFrameIndex = mFrameIndex%mFrameDrawables.size

        return mNextFrame!!
    }

    private fun loadFrameDrawables() {

        val parser = mContext.resources.getXml(mXmlDrawableId)

        try {
            var eventType = parser.getEventType()
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {

                } else if (eventType == XmlPullParser.START_TAG) {

                    if (parser.getName() == "item") {
                        var resId : Int = 0
                        var duration = 100L


                        for (i in 0 until parser.getAttributeCount()) {
                            if (parser.getAttributeName(i) == "drawable") {
                                resId = Integer.parseInt(parser.getAttributeValue(i).substring(1))
                            } else if (parser.getAttributeName(i) == "duration") {
                                duration = parser.getAttributeIntValue(i, 100).toLong()
                            }
                        }

                        val frameDrawable = FrameDrawable(resId, duration)
                        mFrameDrawables.add(frameDrawable)
                    }

                } else if (eventType == XmlPullParser.END_TAG) {

                } else if (eventType == XmlPullParser.TEXT) {

                }

                eventType = parser.next()
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: XmlPullParserException) {
            e.printStackTrace()
        }
    }
}
